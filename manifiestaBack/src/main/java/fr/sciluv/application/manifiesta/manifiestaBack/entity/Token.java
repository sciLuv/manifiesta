package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idToken;
    @Column(name = "token", length = 1000)
    private String token;
    private LocalDateTime beginDate;
    private Integer expirationTime;
    private Boolean isRefreshToken;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idStreamingService")
    private StreamingService streamingService;

    // Constructeurs, getters et setters

    public Token() {
    }

    public Token(String token, LocalDateTime beginDate, Integer expirationTime, Boolean isRefreshToken, User user, StreamingService streamingService) {
        this.token = token;
        this.beginDate = beginDate;
        this.expirationTime = expirationTime;
        this.isRefreshToken = isRefreshToken;
        this.user = user;
        this.streamingService = streamingService;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public Integer getExpirationTime() {
        return expirationTime;
    }

    public Boolean getRefreshToken() {
        return isRefreshToken;
    }


    @Override
    public String toString() {
        return "Token{" +
                "idToken=" + idToken +
                ", token='" + token + '\'' +
                ", beginDate=" + beginDate +
                ", expirationTime=" + expirationTime +
                ", isRefreshToken=" + isRefreshToken +
                ", user=" + user +
                ", streamingService=" + streamingService +
                '}';
    }
}
