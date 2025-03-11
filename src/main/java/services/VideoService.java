package services;

import daos.VideoDAO;
import java.sql.SQLException;
import java.util.List;
import models.Video;

/**
 *Classe Servicio que usan clase DAOs para manipular Base de datos
 * @author zhihan
 */
public class VideoService {
    private VideoDAO videoDAO;

    public VideoService() {
        this.videoDAO = new VideoDAO();
    }
    
    /**
     * Registrar un video
     * @param title título
     * @param author autor
     * @param creationDate fecha de creación
     * @param duration duración del video
     * @param views número de reproducciones
     * @param description descripción del video
     * @param format formato del video
     * @param videoPath ruta del video
     * @param userId id del usuario
     * @return cadena de texto sobre estado del registro de video
     * @throws SQLException Error sobre registro de video
     */
    public String registerVideo(String title, String author, String creationDate, String duration,
        int views, String description, String format, String videoPath, int userId) throws SQLException{
        
        if (userId < 0){
            return "No existe este usuario";
        }
        
        if (videoPath.isEmpty()){
            return "La ruta del video es vacia";
        }
        

        if(videoDAO.checkVideo(title, userId)){
            return "Titulo de video repetido";
        }
        
        // Registrar el video
        boolean isVideoRegisteredSuccess = videoDAO.registerVideo(title, author, creationDate, duration, views, description, format, videoPath, userId);
        if(isVideoRegisteredSuccess){
            return "success";
        }
        return "error";
    }
    /**
     * Obtener el listado de Videos de un usuario concreto
     * @param userId id de usuario
     * @return Lista de videos
     * @throws SQLException Error sobre obtener el listado de Videos
     */
    public List<Video> getListaVideos(int userId) throws SQLException{
        return videoDAO.getVideosByUserId(userId);
    }
    
}
