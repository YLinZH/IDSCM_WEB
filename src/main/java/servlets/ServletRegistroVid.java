package servlets;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import services.UserService;
import services.VideoService;

/**
 *
 * @author zhihan
 */
// Especificamos las configuraciones de subida de archivos
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10, // 10MB antes de guardar en disco
        maxFileSize = 1024 * 1024 * 100, // Tamaño máximo de archivo: 100MB
        maxRequestSize = 1024 * 1024 * 150 // Tamaño máximo de la solicitud: 150MB
)
@WebServlet(name = "servletRegistroVid", urlPatterns = {"/registroVideo"})
public class ServletRegistroVid extends HttpServlet {
    
    private static final String VIDEO_DIRECTORY = "C:/videos_subidos/";
    
    // prevenir que los usuarios puedan acceder a la pagina sin loguear
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getRequestURI();
        HttpSession session = request.getSession(false);
        
         // Mantener sessión activo
        if (action != null && action.endsWith("/registroVideo")){

            if (session == null && session.getAttribute("user") != null) {

                response.sendRedirect("login.jsp");
                return;
            }
        }
        response.sendRedirect("registroVid.jsp");
    }
   
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el username de la sesión
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user");

        // Verificar si el usuario está autenticado
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Obtener los datos del formulario
        String title = request.getParameter("titulo");
        String author = request.getParameter("autor");
        String duration = request.getParameter("duracion");
        int views = 0;
        String description = request.getParameter("descripcion");
        String format = request.getParameter("formato");
        
        // Obtener fecha actual
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String creationDate = now.format(formatter);
        
        // Obtener el archivo de video
        Part filePart = request.getPart("video");

        // Obtener solo el nombre del archivo sin la ruta completa
        String fileName = new File(filePart.getSubmittedFileName()).getName();

        // Definir la ruta completa donde se guardará el archivo
        String videoPath = VIDEO_DIRECTORY + fileName;

        // Crear la carpeta si no existe
        File fileSaveDir = new File(VIDEO_DIRECTORY);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        // Guardar el archivo en la ruta establecido
        try (InputStream fileContent = filePart.getInputStream();
            FileOutputStream fos = new FileOutputStream(videoPath)) {

            byte[] buffer = new byte[1024]; // Buffer de 1 KB
            int bytesRead;
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            // Obtener el ID del usuario
            UserService userService = new UserService();
            int userId = userService.getUserIdByUsername(username);

            // Registrar el video en la base de datos
            VideoService videoService = new VideoService();
            String result = videoService.registerVideo(title, author, creationDate, duration, views, description, format, videoPath, userId);

            if ("success".equals(result)) {
                response.sendRedirect("registroVid.jsp?success=true");
            } else {
                request.setAttribute("error", result);
                request.getRequestDispatcher("registroVid.jsp").forward(request, response);
            }
        } catch (ServletException e) {
        e.printStackTrace();
        request.setAttribute("error", "Error en la solicitud. Formato incorrecto.");
        request.getRequestDispatcher("registroVid.jsp").forward(request, response);

        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al guardar el video.");
            request.getRequestDispatcher("registroVid.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en la base de datos.");
            request.getRequestDispatcher("registroVid.jsp").forward(request, response);

        } catch (Exception e) {  // Captura cualquier otro error inesperado
            e.printStackTrace();
            request.setAttribute("error", "Error desconocido.");
            request.getRequestDispatcher("registroVid.jsp").forward(request, response);
        }
    }
    

}
