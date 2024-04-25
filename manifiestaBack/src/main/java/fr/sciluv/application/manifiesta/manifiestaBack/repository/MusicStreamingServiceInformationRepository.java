package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.MusicStreamingServiceInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicStreamingServiceInformationRepository
        extends JpaRepository<MusicStreamingServiceInformation, Long>{
}
