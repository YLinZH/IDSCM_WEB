package services;

import daos.UserDAO;
import java.sql.SQLException;
import models.User;

/**
 *Classe Servicio que usan clase DAOs para manipular Base de datos
 * @author zhihan
 */
public class UserService {
    
    private UserDAO userDAO;
    
    // Constructor
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Login del usuario
     * @param username nombre de usuario    
     * @param password contraseña
     * @return Class User
     * @throws SQLException Error sobre obtener usuario en base de datos
     */
    public User loginUser(String username, String password) throws SQLException{
         return userDAO.getUser(username, password);
    }
    
    /**
     * registrar un usuario
     * @param name nombre
     * @param surname Apellido
     * @param mail correo
     * @param username nombre de usuario
     * @param password contraseña
     * @param confirmPassword contraseña de confirmación
     * @return cadena de texto sobre el estado del registro
     * @throws SQLException error sobre registrar un usuario
     */
    public String registerUser(String name, String surname, String mail, String username, String password, String confirmPassword) throws SQLException {
        if (!password.equals(confirmPassword)) {
            return "Las contraseñas no coinciden.";
        }

        User user = userDAO.checkUser(username);

        if (user != null) {
            return "El usuario ya existe.";
        }

        int userId = userDAO.insertUser(name, surname, mail, username, password);
        return userId > 0 ? "success" : "Error al registrar el usuario.";
    }
    
    public int getUserIdByUsername(String username) throws SQLException{
        return userDAO.getUserIdByUsername(username);
    }
}
