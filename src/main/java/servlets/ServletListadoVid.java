/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Video;
import services.UserService;
import services.VideoService;

/**
 *
 * @author zhihan
 */
@WebServlet(name = "ServletListadoVid", urlPatterns = {"/listadoVideos"})
public class ServletListadoVid extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Video> listaVideos = new ArrayList<>();
        
        // Obtener el username de la sesión
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user");
        
        // Verificar si el username existe en la sesión
        if (username == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try{
            UserService userService = new UserService();
            VideoService videoService = new VideoService();
            int userId = userService.getUserIdByUsername(username);
            listaVideos = videoService.getListaVideos(userId);

            // Pasar la lista de videos al JSP
            request.setAttribute("videos", listaVideos);
            RequestDispatcher dispatcher = request.getRequestDispatcher("listadoVid.jsp");
            dispatcher.forward(request, response);
        }catch (SQLException e) {
            // Manejo de errores específicos para SQLException
            e.printStackTrace();
            request.setAttribute("error", "Error en la base de datos. Por favor, intente nuevamente más tarde.");
            request.getRequestDispatcher("registroVid.jsp").forward(request, response);
        } catch (NullPointerException e) {
            // Manejo de errores específicos para NullPointerException
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al procesar los datos. Es posible que la sesión haya expirado.");
            request.getRequestDispatcher("registroVid.jsp").forward(request, response);
        } catch (Exception e) {
            // Captura cualquier otra excepción no manejada
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error inesperado. Por favor, contacte al administrador.");
            request.getRequestDispatcher("registroVid.jsp").forward(request, response);
        }

    }


}
