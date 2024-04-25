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
    private String album;

    // Les musiques peuvent être associées à plusieurs PollTurn via SuggestedMusic
    @OneToMany(mappedBy = "music")
    private Set<SuggestedMusic> suggestedMusics;

    @OneToMany(mappedBy = "music")
    private List<SelectedMusic> selectedMusic;

    @OneToMany(mappedBy = "music")
    private Set<MusicStreamingServiceInformation> streamingInfos;

    // Constructeurs, getters et setters

    public Music() {
    }

    public Music(String name, String artist, String album) {
        this.numMusic = -1L;
        this.name = name;
        this.artist = artist;
        this.album = album;
    }
}
