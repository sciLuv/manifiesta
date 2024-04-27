package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String mail;
    private LocalDateTime creationDate;

    public User() {
    }

    public User(String username, String mail) {
        this.id = -1L;
        this.username = username;
        this.mail = mail;
        this.creationDate = LocalDateTime.now();
    }


    @OneToMany(mappedBy = "user")
    private Set<Session> session;

    @OneToMany(mappedBy = "user")
    private Set<SessionParticipant> sessionParticipant;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public String toJSON() {
        return "{" +
                "\"username\":\"" + username + "\"," +
                "\"mail\":\"" + mail + "\"," +
                "\"creationDate\":\"" + creationDate + "\"" +
                "}";
    }

    @OneToMany(mappedBy = "user")
    private Set<QRCode> qrCodes;

    @ManyToMany
    @JoinTable(
            name = "user_token",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "idToken")
    )
    private Set<Token> tokens;

    //Getter


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMail() {
        return mail;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Set<Token> getTokens() {
        return tokens;
    }

    //Setter
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

}
