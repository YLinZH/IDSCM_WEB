package models;


/**
 *
 * @author zhihan
 */
public class Video {
    private int id;
    private String title;
    private String author;
    private String creationDate;
    private String duration;
    private int views;
    private String description;
    private String format;
    private String path;
    private int userId;

    // Constructores, getters y setters
    public Video(int id, String title, String author, String creationDate, String duration, 
                 int views, String description, String format, String path, int userId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.creationDate = creationDate;
        this.duration = duration;
        this.views = views;
        this.description = description;
        this.format = format;
        this.path = path;
        this.userId = userId;
    }

    public Video() {
        this.id = -1;
        this.title = "";
        this.author = "";
        this.creationDate = "";
        this.duration = "";
        this.views = 0;
        this.description = "";
        this.format = "";
        this.path = "";
        this.userId = -1;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getDuration() {
        return duration;
    }

    public int getViews() {
        return views;
    }

    public String getDescription() {
        return description;
    }

    public String getFormat() {
        return format;
    }

    public String getPath() {
        return path;
    }

    public int getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    
}
