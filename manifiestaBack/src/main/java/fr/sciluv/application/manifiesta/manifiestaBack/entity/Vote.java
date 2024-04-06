package fr.sciluv.application.manifiesta.manifiestaBack.entity;


import jakarta.persistence.*;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVote;

    @ManyToOne
    @JoinColumn(name = "idPollTurnMusic")
    private PollTurnMusic pollTurnMusic;

    @ManyToOne
    @JoinColumn(name = "idParticipant")
    private SessionParticipant sessionParticipant;

    //Getters et Setters


    public Long getIdVote() {
        return idVote;
    }

    public void setIdVote(Long idVote) {
        this.idVote = idVote;
    }

    public PollTurnMusic getPollTurnMusic() {
        return pollTurnMusic;
    }

    public void setPollTurnMusic(PollTurnMusic pollTurnMusic) {
        this.pollTurnMusic = pollTurnMusic;
    }

    public SessionParticipant getSessionParticipant() {
        return sessionParticipant;
    }

    public void setSessionParticipant(SessionParticipant sessionParticipant) {
        this.sessionParticipant = sessionParticipant;
    }
}