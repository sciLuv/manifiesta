package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicCurrentlyPlayedDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicListDto;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.List;

public interface MusicService {
    Music generateMusic(Track track);
    MusicStreamingServiceInformation generateMusicStreamingServiceInformation(Track track, Music music, StreamingService streamingService);

    MusicListDto musicsToJSON(List<Music> musics, List<MusicStreamingServiceInformation> musicStreamingServiceInformations);

    MusicCurrentlyPlayedDto musicCurrentlyPlayingToJSON(Token token);

    String musicToJSON(Music music, MusicStreamingServiceInformation musicStreamingServiceInformation);
    SuggestedMusic generateSuggestedMusic(Music music, PollTurn pollTurn);

    Music findMusicBySuggestedMusic(SuggestedMusic suggestedMusic);

}
