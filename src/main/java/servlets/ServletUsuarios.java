/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import services.UserService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import models.User;

/**
 *
 * @author zhihan
 */

@WebServlet(name = "ServletUsuarios", urlPatterns = {"/login", "/register", "/logout"})
public class ServletUsuarios extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getRequestURI();
        HttpSession session = request.getSession(false);
        
         // Mantener sessión activo
        if (action != null && action.endsWith("/login")){

            if (session != null && session.getAttribute("user") != null) {

                response.sendRedirect("listadoVid.jsp");
                return;
            }

            // Si no está logueado, muestra el formulario de login
            response.sendRedirect("login.jsp");
        }

    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getRequestURI();  // Obtener la url completa

        if (action != null) {
            if (action.endsWith("/login")) {
                loginUser(request, response);
            } else if (action.endsWith("/register")) {
                registerUser(request, response);
            } else if(action.endsWith("/logout")){
                logoutUser(request, response);
            }
            else{
                // Acción no reconocida
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        } else {
            // Si no se pasa la acción, puedes manejarlo como un error o redirigir a un formulario
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción requerida");
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Verificar las credenciales del usuario
            UserService userService = new UserService();
            User user = userService.loginUser(username, password);

            if (user != null) {
                // Si el usuario es encontrado, se establece la sesión
                HttpSession session = request.getSession();
                session.setAttribute("user", user.getUsername());
                response.sendRedirect("listadoVid.jsp");
            } else {
                // Si el usuario no es encontrado, mostrar error
                request.setAttribute("error", "Usuario o contraseña incorrectos.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendRedirect("login.jsp?error=DatabaseError");
        }
    }


    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String mail = request.getParameter("mail");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        try {
            // Verificar si el usuario ya existe
            UserService userService = new UserService();
            String result = userService.registerUser(name, surname, mail, username, password, confirmPassword);
            if ("success".equals(result)) {
                response.sendRedirect("registroUsu.jsp?success=true");
            } else {
                request.setAttribute("error", result);
                request.getRequestDispatcher("registroUsu.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void logoutUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalidar la sesión actual
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();  // Invalidar la sesión
        }

        // Redirigir al login después de cerrar sesión
        response.sendRedirect("login.jsp");  // Redirigir al login
    }
}



