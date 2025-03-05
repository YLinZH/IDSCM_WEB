package daos;

import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Video;

/**
 *
 * @author zhihan
 */
public class VideoDAO {
    
    public boolean registerVideo(String title, String author, String creationDate, String duration,
                              int views, String description, String format, String videoPath, int userId) {
        String query = "INSERT INTO videos (id, titulo, autor, fecha_creacion, duracion, reproducciones, descripcion, formato, path, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            
            PreparedStatement stmt = conn.prepareStatement(query);
            
            int nextId = getLastVideoId();
            stmt.setInt(1, nextId);
            stmt.setString(2, title);
            stmt.setString(3, author);
            stmt.setString(4, creationDate);
            stmt.setString(5, duration);
            stmt.setInt(6, views);
            stmt.setString(7, description);
            stmt.setString(8, format);
            stmt.setString(9,videoPath);
            stmt.setInt(10,userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;  // Devuelve true si el video fue insertado correctamente
            
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    // Método para obtener todos los videos de un usuario
    public List<Video> getVideosByUserId(int userId) {
        List<Video> videos = new ArrayList<>();
        String query = "SELECT * FROM videos WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Video video = new Video(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("creationDate"),
                        rs.getString("duration"),
                        rs.getInt("views"),
                        rs.getString("description"),
                        rs.getString("format"),
                        rs.getString("path"),
                        rs.getInt("userId")
                );
                videos.add(video);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return videos;
    }
    
    // Obtener el id del último video
    public int getLastVideoId() {
        String sql = "SELECT MAX(id) FROM videos";
        int nextId = 1; // Por defecto, si no hay registros, empieza en 1

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return nextId;
    }
    
    // verifica si el video ya existe
    public boolean checkVideo(String title) {
        
        String sql = "SELECT * FROM videos WHERE titulo = ?";
        boolean isVideoExist = false;
        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement(sql);       

            stmt.setString(1, title);

            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                isVideoExist = true;
            }
            
            rs.close();
            stmt.close();
            conn.close();
            return isVideoExist;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return isVideoExist;
    }
}
