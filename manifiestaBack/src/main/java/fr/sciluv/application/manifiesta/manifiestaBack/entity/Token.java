package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idToken;

    private String token;
    private Date beginDate;
    private Integer expirationTime;
    private Boolean isRefreshToken;

    @ManyToMany(mappedBy = "tokens")
    private Set<Session> sessions;

    @ManyToOne
    @JoinColumn(name = "idService")
    private StreamingService streamingService;

    // Constructeurs, getters et setters
}
