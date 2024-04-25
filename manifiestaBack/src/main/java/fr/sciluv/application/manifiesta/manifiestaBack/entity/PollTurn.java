package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "poll_turn")
public class PollTurn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPollTurn;

    private int number_turn;

    // Un PollTurn peut avoir plusieurs musiques suggérées
    @OneToMany(mappedBy = "pollTurn")
    private Set<SuggestedMusic> suggestedMusics;

    @OneToOne(mappedBy = "pollTurn", cascade = CascadeType.ALL)
    private SelectedMusic selectedMusic;

    //pollturn est associé a une seule session tandis qu'une session peut avoir plusieurs pollturn
    @ManyToOne
    @JoinColumn(name = "idSession", referencedColumnName = "idSession")
    private Session session;

    // Constructeurs, getters et setters
    public PollTurn() {
    }

    public PollTurn(Session session, int number_turn) {
        this.session = session;
        this.number_turn = number_turn;
    }
}