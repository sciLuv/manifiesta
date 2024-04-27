package fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user;

public class UserDto {
    private String username;
    private String mail;
    private String password;

    public UserDto() {
    }

    public UserDto(String username, String mail, String password) {
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
