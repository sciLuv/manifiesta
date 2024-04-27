package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "suggested_music")
public class SuggestedMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSuggestMusic;

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

    // Tu peux aussi avoir des attributs supplémentaires ici pour les détails spécifiques à la suggestion

    // Constructeurs, getters et setters
}