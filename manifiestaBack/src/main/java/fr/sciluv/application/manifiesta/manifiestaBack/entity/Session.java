package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "session") // Assure-toi que le nom de la table correspond à ce que tu as dans ta base de données.
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSession;

    private LocalDateTime hourOfBegin;
    private LocalDateTime hourOfEnd;
    private String password;

    private int songsNumber;

    private int musicalStylesNumber;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    // Une session peut avoir plusieurs pollturns
    @OneToMany(mappedBy = "session")
    private Set<PollTurn> pollTurns;

    @ManyToMany
    Set<SessionParticipant> sessionParticipants;

    @OneToMany(mappedBy = "session")
    private Set<QRCode> qrCodes;

    // Constructeurs, getters et setters


    public Session(Integer idSession,
                   LocalDateTime hourOfBegin,
                   LocalDateTime hourOfEnd,
                   String password,
                   int songsNumber,
                   int musicalStylesNumber,
                   User user
    ) {
        this.idSession = idSession;
        this.hourOfBegin = hourOfBegin;
        this.hourOfEnd = hourOfEnd;
        this.password = password;
        this.songsNumber = songsNumber;
        this.musicalStylesNumber = musicalStylesNumber;
        this.user = user;
    }
}