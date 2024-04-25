package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import se.michaelthelin.spotify.model_objects.specification.Track;

public interface MusicService {
    Music generateMusic(Track track);
    MusicStreamingServiceInformation generateMusicStreamingServiceInformation(Track track, Music music, StreamingService streamingService);
    SuggestedMusic generateSuggestedMusic(Music music, PollTurn pollTurn);
}
