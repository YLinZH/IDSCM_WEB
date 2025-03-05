package services;

import daos.UserDAO;
import daos.VideoDAO;

/**
 *
 * @author zhihan
 */
public class VideoService {
    private VideoDAO videoDAO;

    public VideoService() {
        this.videoDAO = new VideoDAO();
    }
    
    public String registerVideo(String title, String author, String creationDate, String duration,
        int views, String description, String format, String videoPath, int userId){
        
        if (userId < 0){
            return "No existe este usuario";
        }
        
        if (videoPath.isEmpty()){
            return "La ruta del video es vacia";
        }
        
        VideoDAO videoDAO = new VideoDAO();

        if(videoDAO.checkVideo(title)){
            return "Titulo de video repetido";
        }
        
        // Registrar el video
        boolean isVideoRegisteredSuccess = videoDAO.registerVideo(title, author, creationDate, duration, views, description, format, videoPath, userId);
        if(isVideoRegisteredSuccess){
            return "success";
        }
        return "error";
    }
    
}
