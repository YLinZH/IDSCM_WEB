/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import dao.UserDAO;
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author zhiha
 */

@WebServlet(name = "servletUsuarios", urlPatterns = {"/login", "/register", "/logout"})
public class servletUsuarios extends HttpServlet {
    
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
        
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(username, password);

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
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String mail = request.getParameter("mail");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Las contraseñas no coinciden.");
            request.getRequestDispatcher("registroUsu.jsp").forward(request, response);
            return;
        }
        

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Verificar si el usuario ya existe
            UserDAO userDAO = new UserDAO();
            User user = userDAO.checkUser(username);
            
            if (user != null) {
                request.setAttribute("error", "El usuario ya existe.");
                request.getRequestDispatcher("registroUsu.jsp").forward(request, response);
                return;
            }

            // Insertar nuevo usuario
            int userId = userDAO.insertUser(name, surname, mail, username, password);

            response.sendRedirect("login.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("registroUsu.jsp?error=DatabaseError");
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



