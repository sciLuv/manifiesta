package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicCurrentlyPlayedDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicListDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.MusicRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.MusicStreamingServiceInformationRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.StreamingServiceRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.SuggestedMusicRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.MusicService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.PollTurnService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify.GetUsersTopTracks;
import fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify.SpotifyService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private MusicStreamingServiceInformationRepository musicStreamingServiceInformationRepository;

    @Autowired
    private SuggestedMusicRepository suggestedMusicRepository;

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private StreamingServiceRepository streamingServiceRepository;

    @Autowired
    private PollTurnService pollTurnService;


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

    @Override
    public Music findMusicBySuggestedMusic(SuggestedMusic suggestedMusic) {
        return musicRepository.findBySuggestedMusics(suggestedMusic);
    }


    @Override
    public MusicListDto musicsToJSON(List<Music> musics, List<MusicStreamingServiceInformation> musicStreamingServiceInformations) {
        List<MusicDto> musicsDto = new ArrayList<>();
        for (int i = 0; i < musics.size(); i++) {
            musicsDto.add(
                            new MusicDto(
                            musics.get(i).getName(),
                            musics.get(i).getArtist(),
                            musics.get(i).getAlbum(),
                            musicStreamingServiceInformations.get(i).getUrl_link(),
                            musicStreamingServiceInformations.get(i).getUrl_img(),
                            musicStreamingServiceInformations.get(i).getDuration()
            ));
        }
        return new MusicListDto(musicsDto);
    }

    @Override
    public MusicCurrentlyPlayedDto musicCurrentlyPlayingToJSON(Token accessToken) {
        StreamingService streamingService = streamingServiceRepository.findByName("spotify");
        CurrentlyPlaying currentlyPlaying = null;
        try {
            currentlyPlaying = spotifyService.getCurrentTrack(accessToken.getToken());
        } catch (IOException | org.apache.hc.core5.http.ParseException | ParseException | SpotifyWebApiException e) {
            throw new RuntimeException(e);
        }
        Track track = (Track) currentlyPlaying.getItem();
        Music musicActual = generateMusic(track);

        MusicStreamingServiceInformation musicStreamingServiceInformation =
                generateMusicStreamingServiceInformation(track, musicActual, streamingService);

        return new MusicCurrentlyPlayedDto(
                musicActual.getName(),
                musicActual.getArtist(),
                musicActual.getAlbum(),
                musicStreamingServiceInformation.getUrl_link(),
                musicStreamingServiceInformation.getUrl_img(),
                musicStreamingServiceInformation.getDuration(),
                currentlyPlaying.getProgress_ms()
        );
    }

    public String musicToJSON(Music music, MusicStreamingServiceInformation musicStreamingServiceInformation) {
        return String.format("{\"name\": \"%s\", \"artist\": \"%s\", \"album\": \"%s\", " +
                        "\"duration\": \"%d\", \"url_img\": \"%s\", \"url_link\": \"%s\"}",
                music.getName(), music.getArtist(), music.getAlbum(),
                musicStreamingServiceInformation.getDuration(), musicStreamingServiceInformation.getUrl_img(),
                musicStreamingServiceInformation.getUrl_link());
    }

    @Override
    public void findMusicsOnStreamingServiceForAPollTurn1(Session session, String StreamingServiceToken) {
        GetUsersTopTracks getUsersTopTracks = new GetUsersTopTracks(StreamingServiceToken);
        //in function of top tracks, generate musics, suggested musics and music streaming service informations

        getUsersTopTracks.getUsersTopTracks().ifPresent(trackPaging -> {

            PollTurn pollTurn = pollTurnService.createPollTurn(session);
            StreamingService streamingService = streamingServiceRepository.findByName("Spotify");

            int offSet = trackPaging.getOffset();
            System.out.println("offset" + offSet);

            for (Integer number : NumberUtil.generateNumbers(50, session.getSongsNumber())) {
                Music music = generateMusic(trackPaging.getItems()[(number)]);
                generateMusicStreamingServiceInformation(trackPaging.getItems()[(number)], music, streamingService);
                generateSuggestedMusic(music, pollTurn);
            }


        });
    }






}
