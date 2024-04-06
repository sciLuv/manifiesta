package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMusic;
    private String name;
    private Integer duration;
    private String artist;
    private String spotifyUrl;
    private String imageUrl;

    @OneToMany(mappedBy = "music")
    private Set<PollTurnMusic> pollTurnMusics;

    // Getters and setters


    public Long getIdMusic() {
        return idMusic;
    }

    public void setIdMusic(Long idMusic) {
        this.idMusic = idMusic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSpotifyUrl() {
        return spotifyUrl;
    }

    public void setSpotifyUrl(String spotifyUrl) {
        this.spotifyUrl = spotifyUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<PollTurnMusic> getPollTurnMusics() {
        return pollTurnMusics;
    }

    public void setPollTurnMusics(Set<PollTurnMusic> pollTurnMusics) {
        this.pollTurnMusics = pollTurnMusics;
    }
}
