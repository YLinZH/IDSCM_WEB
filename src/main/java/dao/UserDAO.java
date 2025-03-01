/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.User;
import database.DatabaseConnection;
import java.sql.*;

public class UserDAO {

    public User getUser(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM \"user\" WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(sql);       

            stmt.setString(1, username);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
            
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
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    
    public User checkUser(String username) {
        User user = null;
        String sql = "SELECT * FROM \"user\" WHERE user = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(sql);       

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            
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
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    public int insertUser(String name, String surname, String mail, String username, String password) {
        String sql = "INSERT INTO \"user\" (id, name, surname, mail, username, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

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
        }
        return -1;
    }

    
    public int getLastUserId() {
        String sql = "SELECT MAX(id) FROM \"user\"";
        int nextId = 1; // Por defecto, si no hay registros, empieza en 1

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextId;
    }


}
