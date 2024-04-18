package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String mail;
    private LocalDateTime creationDate;

    public User() {
    }

    public User(String username, String password, String mail) {
        this.id = -1L;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.creationDate = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public String toJSON() {
        return "{" +
                "\"username\":\"" + username + "\"," +
                "\"password\":\"" + password + "\"," +
                "\"mail\":\"" + mail + "\"," +
                "\"creationDate\":\"" + creationDate + "\"" +
                "}";
    }

    //Getter
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    //Setter
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
