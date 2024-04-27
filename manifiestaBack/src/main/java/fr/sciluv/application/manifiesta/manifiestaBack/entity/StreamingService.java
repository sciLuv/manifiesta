package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "streaming_service")
public class StreamingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStreamingService;

    private String name;

    @OneToMany(mappedBy = "streamingService")
    private Set<MusicStreamingServiceInformation> musicInfos;

    @OneToMany(mappedBy = "streamingService")
    private Set<Token> tokens;
    // Constructeurs, getters et setters


    public StreamingService() {
    }

    public StreamingService(String name) {
        this.name = name;
    }

    public StreamingService(Integer idStreamingService, String name) {
        this.idStreamingService = idStreamingService;
        this.name = name;
    }

    public Integer getIdStreamingService() {
        return idStreamingService;
    }

    public String getName() {
        return name;
    }

    public Set<MusicStreamingServiceInformation> getMusicInfos() {
        return musicInfos;
    }

    @Override
    public String toString() {
        return "StreamingService{" +
                "idStreamingService=" + idStreamingService +
                ", name='" + name + '\'' +
                '}';
    }
}
