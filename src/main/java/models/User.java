package models;

/**
 *
 * @author zhihan
 */
public class User {
    private int id;
    private String name;
    private String surname;
    private String mail;
    private String username;
    private String password;

    /**
     * Constructor
     * @param id id de usuario
     * @param name nombre
     * @param surname Apellido
     * @param mail Correo
     * @param username nombre de usuario
     * @param password contraseña
     */
    public User(int id, String name, String surname, String mail, String username, String password) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.username = username;
        this.password = password;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
