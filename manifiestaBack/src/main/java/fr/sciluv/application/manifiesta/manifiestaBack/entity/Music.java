package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "music")
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numMusic;

    private String name;
    private String artist;

    // Les musiques peuvent être associées à plusieurs PollTurn via SuggestedMusic
    @OneToMany(mappedBy = "music")
    private Set<SuggestedMusic> suggestedMusics;

    @OneToMany(mappedBy = "music")
    private List<SelectedMusic> selectedMusic;

    @OneToMany(mappedBy = "music")
    private Set<MusicStreamingServiceInformation> streamingInfos;

    // Constructeurs, getters et setters
}
