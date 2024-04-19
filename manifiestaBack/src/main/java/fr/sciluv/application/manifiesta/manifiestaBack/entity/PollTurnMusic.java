package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class PollTurnMusic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPollTurnMusic;

    @ManyToOne
    @JoinColumn(name = "idMusic")
    private Music music;

    @OneToMany(mappedBy = "pollTurnMusic")
    private Set<Vote> votes;

    // Getters and setters

    public Long getIdPollTurnMusic() {
        return idPollTurnMusic;
    }

    public void setIdPollTurnMusic(Long idPollTurnMusic) {
        this.idPollTurnMusic = idPollTurnMusic;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }
}