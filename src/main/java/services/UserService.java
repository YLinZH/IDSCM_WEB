package services;

import daos.UserDAO;
import models.User;

/**
 *
 * @author zhihan
 */
public class UserService {
    
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    public User loginUser(String username, String password){
         return userDAO.getUser(username, password);
    }
    
    public String registerUser(String name, String surname, String mail, String username, String password, String confirmPassword) {
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
    
    public int getUserIdByUsername(String username){
        return userDAO.getUserIdByUsername(username);
    }
}
