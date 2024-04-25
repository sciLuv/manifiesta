package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.MusicDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.MusicRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.MusicStreamingServiceInformationRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.SuggestedMusicRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.util.Optional;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private MusicStreamingServiceInformationRepository musicStreamingServiceInformationRepository;

    @Autowired
    private SuggestedMusicRepository suggestedMusicRepository;

    @Override
    public Music generateMusic(Track track) {

        Optional<Music> musicOptional = musicRepository.findByNameAndArtistAndAlbum(track.getName(), track.getArtists()[0].getName(), track.getAlbum().getName());
        if(musicOptional.isPresent()){
            return musicOptional.get();
        } else {
            Music music = new Music(
                    track.getName(),
                    track.getArtists()[0].getName(),
                    track.getAlbum().getName()
            );

            return musicRepository.save(music);
        }
    }

    @Override
    public MusicStreamingServiceInformation generateMusicStreamingServiceInformation(Track track, Music music, StreamingService streamingService) {

        MusicStreamingServiceInformation musicStreamingServiceInformation = new MusicStreamingServiceInformation(
                track.getDurationMs(),
                track.getAlbum().getImages()[0].getUrl(),
                track.getUri(),
                streamingService,
                music
        );

        return musicStreamingServiceInformationRepository.save(musicStreamingServiceInformation);
    }

    public SuggestedMusic generateSuggestedMusic(Music music, PollTurn pollTurn){
        SuggestedMusic suggestedMusic = new SuggestedMusic(
                music,
                pollTurn
        );
        return suggestedMusicRepository.save(suggestedMusic);
    }
}
