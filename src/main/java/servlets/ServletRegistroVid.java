package servlets;


import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import services.UserService;
import services.VideoService;

/**
 *
 * @author zhihan
 */
@WebServlet(name = "servletRegistroVid", urlPatterns = {"/registroVideo"})
public class ServletRegistroVid extends HttpServlet {
    
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
    
     // Método para manejar la solicitud de registro de un video
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
     
        // Obtener los datos del formulario
        String title = request.getParameter("titulo");
        String author = request.getParameter("autor");

        String duration = request.getParameter("duracion");
        int views = Integer.parseInt(request.getParameter("reproducciones"));
        String description = request.getParameter("descripcion");
        String format = request.getParameter("formato");
        String videoPath = request.getParameter("path");
        
        // Obtener fecha actual como String
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String creationDate = now.format(formatter);

        // Obtener el username de la sesión
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user");
        
        // Verificar si el username existe en la sesión
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            
            UserService userService = new UserService();
            int userId = userService.getUserIdByUsername(username);
            VideoService videoService = new VideoService();
            String result = videoService.registerVideo(title, author, creationDate, duration, views, description, format, videoPath, userId);
            if ("success".equals(result)) {
                response.sendRedirect("registroVid.jsp?success=true");
            } else {
                request.setAttribute("error", result);
                request.getRequestDispatcher("registroVid.jsp").forward(request, response);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    

}
