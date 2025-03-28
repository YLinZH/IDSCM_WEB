package servlets;

import models.Video;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.SQLException;
import services.VideoService;

@WebServlet("/ServletREST")
public class ServletREST extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if(idStr == null || idStr.trim().isEmpty()){
            response.sendRedirect("listVideos.jsp");
            return;
        }
        try {
            VideoService videoService = new VideoService();
            int id = Integer.parseInt(idStr);
            
            // Recupera el objeto vídeo a través de la capa de acceso a datos
            Video video = videoService.getVideoById(id);
            if(video.getId() < 0) {
                response.sendRedirect("listVideos.jsp");
                return;
            }
            // Pasa el objeto vídeo a la JSP de reproducción
            request.setAttribute("video", video);
            request.getRequestDispatcher("reproduccion.jsp").forward(request, response);
        } catch(NumberFormatException ex){
            response.sendRedirect("listadoVid.jsp");
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("error", "Error en la base de datos. Por favor, intente nuevamente más tarde.");
        }
    }
}
