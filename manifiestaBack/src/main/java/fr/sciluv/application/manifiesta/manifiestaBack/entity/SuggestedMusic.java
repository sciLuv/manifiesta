package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "suggested_music")
public class SuggestedMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSuggestMusic;

    @ManyToOne
    @JoinColumn(name = "numMusic", referencedColumnName = "numMusic")
    private Music music;

    @ManyToOne
    @JoinColumn(name = "idPollTurn", referencedColumnName = "idPollTurn")
    private PollTurn pollTurn;

    @OneToMany(mappedBy = "suggestedMusic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;


public SuggestedMusic() {
    }

    public SuggestedMusic(Music music, PollTurn pollTurn) {
        this.music = music;
        this.pollTurn = pollTurn;
    }

    // Constructeurs, getters et setters
    public int getIdSuggestMusic() {
        return idSuggestMusic;
    }
}