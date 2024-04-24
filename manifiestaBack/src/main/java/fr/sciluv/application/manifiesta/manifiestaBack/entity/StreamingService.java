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
}
