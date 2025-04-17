package servlets;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import models.Video;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.SQLException;
import services.VideoService;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ServletREST")
public class ServletREST extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if(idStr == null || idStr.trim().isEmpty()){
            response.sendRedirect("listadoVid.jsp");
            return;
        }
        try {
            String serviceUrl = "http://localhost:8080/REST/resources/jakartaee91/actualizarReproducciones";

            VideoService videoService = new VideoService();
            int id = Integer.parseInt(idStr);
            int incremento = 1;
            // Recupera el objeto vídeo a través de la capa de acceso a datos
            Video video = videoService.getVideoById(id);
            if(video.getId() < 0) {
                response.sendRedirect("listadoVid.jsp");
                return;
            }
            
             // Construir el JSON a enviar. Se crea una cadena JSON de forma manual.
            String jsonPayload = "{\"videoId\": " + id + ", \"increment\": " + incremento + "}";

            // Crear el cliente REST para enviar la petición POST
            Client client = ClientBuilder.newClient();
            Response restResponse = client.target(serviceUrl)
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(jsonPayload, MediaType.APPLICATION_JSON));

            // Leer la respuesta del servicio REST (por ejemplo, un mensaje en formato JSON)
            String result = restResponse.readEntity(String.class);
            restResponse.close();
            client.close();
            
            // Parsear el JSON de respuesta utilizando JSON-P
           JsonReader jsonReader = Json.createReader(new StringReader(result));
           JsonObject jsonResponse = jsonReader.readObject();
           jsonReader.close();

           // Extraer el valor del campo "estado"
           String estado = jsonResponse.getString("estado", "error");
           
            // Verificar el estado si ha actualizado correctamente el numero de reproducciones
            if ("actualizado".equalsIgnoreCase(estado)) {
                // El vídeo se ha actualizado correctamente
                // Pasa el objeto vídeo a la JSP de reproducción
                video.setViews(video.getViews() + incremento);
                request.setAttribute("video", video);
                request.getRequestDispatcher("reproduccion.jsp").forward(request, response);
            } else {
                // Se ha producido algún error
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"mensaje\":\"Error en la actualización\"}");
            }

        } catch(NumberFormatException ex){
            response.sendRedirect("listadoVid.jsp");
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Error en la base de datos. Por favor, intente nuevamente más tarde.");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tipoBusqueda = request.getParameter("tipoBusqueda");
        String criterio = request.getParameter("criterio");

        String baseUrl = "http://localhost:8080/REST/resources/jakartaee91";
        String url = "";

        switch (tipoBusqueda) {
            case "titulo":  
                url = baseUrl + "/buscar/titulo?titulo=" + criterio;
                break;
            case "autor":
                url = baseUrl + "/buscar/autor?autor=" + criterio;
                break;
            case "fecha": 
                url = baseUrl + "/buscar/fecha?fecha=" + criterio;
                break;
            default: url = null;
                break;
        }
        
        List<Video> listaVideos = new ArrayList<>();
        if (url != null) {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(url);
            String jsonResponse = target.request(MediaType.APPLICATION_JSON).get(String.class);
            client.close();
            
            // Parsear el JSON usando JSON-P
           JsonReader reader = Json.createReader(new StringReader(jsonResponse));
           JsonArray jsonArray = reader.readArray();

           for (JsonValue val : jsonArray) {
               JsonObject obj = val.asJsonObject();
               Video video = new Video(
                        obj.getInt("id"),
                        obj.getString("title"),
                        obj.getString("author"),
                        obj.getString("creationDate"),
                        obj.getString("duration"),
                        obj.getInt("views"),
                        obj.getString("description"),
                        obj.getString("format"),
                        obj.getString("path"),
                        obj.getInt("userId")
                );
                listaVideos.add(video);
        }
        }

        // Guardar resultados y reenviar a la misma página
        request.setAttribute("tipoBusqueda", tipoBusqueda);
        request.setAttribute("criterio", criterio);
        request.setAttribute("videos", listaVideos);
        request.getRequestDispatcher("busqueda.jsp").forward(request, response);
    }
}
