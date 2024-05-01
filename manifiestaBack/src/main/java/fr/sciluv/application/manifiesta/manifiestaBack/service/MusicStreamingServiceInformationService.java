package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Music;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.MusicStreamingServiceInformation;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.StreamingService;

public interface MusicStreamingServiceInformationService {

    MusicStreamingServiceInformation findByMusicAndStreamingService(Music music, StreamingService streamingService);

    MusicStreamingServiceInformation findByMusic(Music music);
}
