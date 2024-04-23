package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "session") // Assure-toi que le nom de la table correspond à ce que tu as dans ta base de données.
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSession;

    private Date hourOfBegin;
    private Date hourOfEnd;
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

    @ManyToMany
    @JoinTable(
            name = "session_token",
            joinColumns = @JoinColumn(name = "idSession"),
            inverseJoinColumns = @JoinColumn(name = "idToken")
    )
    private Set<Token> tokens;

    @OneToMany(mappedBy = "session")
    private Set<QRCode> qrCodes;

    // Constructeurs, getters et setters
}