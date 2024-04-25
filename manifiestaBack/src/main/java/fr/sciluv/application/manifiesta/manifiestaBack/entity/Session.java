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

    // Constructeurs


    public Session() {
    }

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

    // Getters et setters

    public Integer getIdSession() {
        return idSession;
    }

    public void setIdSession(Integer idSession) {
        this.idSession = idSession;
    }

    public LocalDateTime getHourOfBegin() {
        return hourOfBegin;
    }

    public void setHourOfBegin(LocalDateTime hourOfBegin) {
        this.hourOfBegin = hourOfBegin;
    }

    public LocalDateTime getHourOfEnd() {
        return hourOfEnd;
    }

    public void setHourOfEnd(LocalDateTime hourOfEnd) {
        this.hourOfEnd = hourOfEnd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSongsNumber() {
        return songsNumber;
    }

    public void setSongsNumber(int songsNumber) {
        this.songsNumber = songsNumber;
    }

    public int getMusicalStylesNumber() {
        return musicalStylesNumber;
    }

    public void setMusicalStylesNumber(int musicalStylesNumber) {
        this.musicalStylesNumber = musicalStylesNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<PollTurn> getPollTurns() {
        return pollTurns;
    }

    public void setPollTurns(Set<PollTurn> pollTurns) {
        this.pollTurns = pollTurns;
    }

    public Set<SessionParticipant> getSessionParticipants() {
        return sessionParticipants;
    }

    public void setSessionParticipants(Set<SessionParticipant> sessionParticipants) {
        this.sessionParticipants = sessionParticipants;
    }

    public Set<QRCode> getQrCodes() {
        return qrCodes;
    }

    public void setQrCodes(Set<QRCode> qrCodes) {
        this.qrCodes = qrCodes;
    }
}