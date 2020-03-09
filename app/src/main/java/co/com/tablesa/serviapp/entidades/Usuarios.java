package co.com.tablesa.serviapp.entidades;

public class Usuarios {

    private Integer userID;
    private String userName;
    private String userMail;
    private String userPassword;

    public Usuarios() {

    }

    public Usuarios(Integer userID, String userName, String userMail) {
        this.userID = userID;
        this.userName = userName;
        this.userMail = userMail;
    }

    public Usuarios(String userName, String userMail, String userPassword) {
        this.userName = userName;
        this.userMail = userMail;
        this.userPassword = userPassword;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
