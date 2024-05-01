package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Music;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.MusicStreamingServiceInformation;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.StreamingService;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.MusicStreamingServiceInformationRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.MusicStreamingServiceInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicStreamingServiceInformationServiceImpl implements MusicStreamingServiceInformationService {

    @Autowired
    MusicStreamingServiceInformationRepository musicStreamingServiceInformationRepository;

    public MusicStreamingServiceInformation findByMusicAndStreamingService(Music music, StreamingService streamingService) {
        return musicStreamingServiceInformationRepository.findByMusicAndStreamingService(music, streamingService);
    }

    public MusicStreamingServiceInformation findByMusic(Music music) {
        return musicStreamingServiceInformationRepository.findByMusic(music);
    }
}
