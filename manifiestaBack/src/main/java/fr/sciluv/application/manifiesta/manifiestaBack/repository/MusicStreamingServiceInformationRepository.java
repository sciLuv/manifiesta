package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Music;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.MusicStreamingServiceInformation;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.StreamingService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicStreamingServiceInformationRepository
        extends JpaRepository<MusicStreamingServiceInformation, Long>{
        MusicStreamingServiceInformation findByMusicAndStreamingService(Music music, StreamingService streamingService);

}
