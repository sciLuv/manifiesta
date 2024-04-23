package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "selected_music")
public class SelectedMusic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlayedMusic;

    private Integer listenTurn;

    // Un SelectedMusic est associé à une musique
    @ManyToOne
    @JoinColumn(name = "numMusic", referencedColumnName = "numMusic")
    private Music music;

    @OneToOne
    @JoinColumn(name = "idPollTurn", referencedColumnName = "idPollTurn")
    private PollTurn pollTurn;






    // Constructeurs, getters et setters
}
