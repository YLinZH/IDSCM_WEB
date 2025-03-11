package daos;
import models.User;
import utils.DatabaseConnection;
import java.sql.*;

public class UserDAO {

    /**
     * obtener el usuario des de base de datos
     * @param username nombre de usuario
     * @param password contraseña
     * @return Class User
     * @throws SQLException Error obtener un usuario
     */
    public User getUser(String username, String password) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);       

            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
           
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error obtener un usuario", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }
    
    /**
     * validar si el usuario existe en base de datos
     * @param username nombre de usuario
     * @return Class User
     * @throws SQLException Error al validar si existe el usuario
     */
    public User checkUser(String username) throws SQLException {
        User user = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM usuarios WHERE username = ?";

        try{
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);       

            stmt.setString(1, username);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("mail"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al validar si existe el usuario", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return user;
    }
    
    /**
     * Insertar nuevo usuario en base de datos
     * @param name nombre
     * @param surname Apellido
     * @param mail correo
     * @param username nombre de usuario
     * @param password contraseña
     * @return id de usuario
     * @throws SQLException Error al insertar un nuevo usuario
     */
    public int insertUser(String name, String surname, String mail, String username, String password) throws SQLException {
        String sql = "INSERT INTO usuarios (id, name, surname, mail, username, password) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try{
            conn = DatabaseConnection.getConnection(); 
            stmt = conn.prepareStatement(sql);
            int nextId = getLastUserId(); // Obtener el próximo ID disponible

            stmt.setInt(1, nextId);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, mail);
            stmt.setString(5, username);
            stmt.setString(6, password);
            stmt.executeUpdate();
            
            return nextId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al insertar un nuevo usuario", e);
        }finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * obtener el último id más grande de usuario
     * @return el id más grande de usuario
     * @throws SQLException Error al obtener el último ID del usuario
     */
    public int getLastUserId() throws SQLException {
        String sql = "SELECT MAX(id) FROM usuarios";
        int nextId = 1; // Por defecto, si no hay registros, empieza en 1
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener el último ID del usuario", e);
        } finally {
            // Asegurarse de que los recursos sean cerrados en cualquier caso
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return nextId;
    }
    

    /**
     * Método para obtener el user_id a partir del username
     * @param username nombre de usuario
     * @return id de usuario
     * @throws SQLException Error al obtener el ID del usuario
     */
    public int getUserIdByUsername(String username) throws SQLException{
        int userId = -1;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT id FROM usuarios WHERE username = ?";
        
        try{
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                userId = rs.getInt("id");  // Obtener el user_id
            }
        } catch (SQLException e) {
            e.printStackTrace();  
            throw new SQLException("Error al obtener el ID del usuario", e);
        } finally {
        // Asegurarse de que los recursos sean cerrados en cualquier caso
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return userId;
    }


}
