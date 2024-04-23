package fr.sciluv.application.manifiesta.manifiestaBack.entity;


import jakarta.persistence.*;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVote;

    @ManyToOne
    @JoinColumn(name = "idParticipant")
    private SessionParticipant sessionParticipant;

    @ManyToOne
    @JoinColumn(name = "suggestedMusicId") // Assure-toi que le nom de la colonne correspond à la colonne clé étrangère dans ta base de données.
    private SuggestedMusic suggestedMusic;

    //Getters et Setters


    public Long getIdVote() {
        return idVote;
    }

    public void setIdVote(Long idVote) {
        this.idVote = idVote;
    }


    public SessionParticipant getSessionParticipant() {
        return sessionParticipant;
    }

    public void setSessionParticipant(SessionParticipant sessionParticipant) {
        this.sessionParticipant = sessionParticipant;
    }
}