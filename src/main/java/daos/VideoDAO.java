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
    
    /**
     * 
     * @param title titulo del video
     * @param author autor del video
     * @param creationDate fecha de creación del video
     * @param duration duración del video
     * @param views número de reproducciones del video
     * @param description descripcion del video
     * @param format formato del video
     * @param videoPath ruta del video
     * @param userId id de usuario que ha registrado el video
     * @return true si el video ha sido registrado, false el caso contrario
     * @throws SQLException Error al registrar video
     */
    public boolean registerVideo(String title, String author, String creationDate, String duration,
                              int views, String description, String format, String videoPath, int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "INSERT INTO videos (id, titulo, autor, fecha_creacion, duracion, reproducciones, descripcion, formato, path, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            
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
            e.printStackTrace();  
            throw new SQLException("Error al registrar video", e);
        }finally {
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
    }
    
    
    /**
     * Método para obtener todos los videos de un usuario
     * @param userId id de usuario
     * @return lista de videos
     * @throws SQLException Error al obtener lista de videos
     */
    public List<Video> getVideosByUserId(int userId) throws SQLException {
        List<Video> videos = new ArrayList<>();
        String query = "SELECT * FROM videos WHERE user_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Video video = new Video(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("fecha_creacion"),
                        rs.getString("duracion"),
                        rs.getInt("reproducciones"),
                        rs.getString("descripcion"),
                        rs.getString("formato"),
                        rs.getString("path"),
                        rs.getInt("user_id")
                );
                videos.add(video);
            }
            return videos;
            
        } catch (SQLException e) {
            e.printStackTrace();  
            throw new SQLException("Error al obtener lista de videos", e);
        }finally {
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
    }
    
    
    /**
     * Obtener el id del último video
     * @return último id mas grande de video
     * @throws SQLException Error al obtener el último id de videos
     */
    public int getLastVideoId() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT MAX(id) FROM videos";
        int nextId = 1; // Por defecto, si no hay registros, empieza en 1

        try{
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }
            
            return nextId;
            
        }catch (SQLException e) {
            e.printStackTrace();  
            throw new SQLException("Error al obtener el último id de videos", e);
        }finally {
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

    }
    

    /**
     * verifica si el video ya existe para un usuario especifico
     * @param title titulo del Video
     * @return true si el video existe, false si el video no existe
     * @throws SQLException Error al validar si el video existe en un usuario
     */
    public boolean checkVideo(String title, int userId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM videos WHERE titulo = ? and user_id = ?";
        boolean isVideoExist = false;
        try{
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);       

            stmt.setString(1, title);
            stmt.setInt(2, userId);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                isVideoExist = true;
            }

            return isVideoExist;
        } catch (SQLException e) {
            e.printStackTrace();  
            throw new SQLException("Error al validar si el video existe en un usuario", e);
        }finally {
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
    }
}
