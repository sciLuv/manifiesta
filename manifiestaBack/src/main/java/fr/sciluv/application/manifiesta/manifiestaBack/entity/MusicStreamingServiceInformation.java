package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "music_streaming_service_information")
public class MusicStreamingServiceInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMusicStreamingServiceInformation;

    private Integer duration;
    private String url_img;
    private String url_link;

    @ManyToOne
    @JoinColumn(name = "idStreamingService")
    private StreamingService streamingService;

    @ManyToOne
    @JoinColumn(name = "numMusic")
    private Music music;

    // Constructeurs, getters et setters

    public MusicStreamingServiceInformation() {
    }

    public MusicStreamingServiceInformation(Integer duration, String url_img, String url_link, StreamingService streamingService, Music music) {
        this.duration = duration;
        this.url_img = url_img;
        this.url_link = url_link;
        this.streamingService = streamingService;
        this.music = music;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getUrl_img() {
        return url_img;
    }

    public String getUrl_link() {
        return url_link;
    }
}
