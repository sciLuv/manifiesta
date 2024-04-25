package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;


import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.*;
import fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify.GetUsersTopTracks;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.StreamingServiceRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.*;
import fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private MusicService musicService;

    @Autowired
    private StreamingServiceRepository streamingServiceRepository;

    @Autowired
    private PollTurnService pollTurnService;

    @PostMapping("/createSession")
    public String createSession(@RequestBody CreateSessionRequestDto requestDto) {
        String returnMessage = "";
        boolean isMusicActived = spotifyService.isMusicPlayed(requestDto.getTokenDto().getAccessToken(), requestDto.getTokenDto().getAccessToken());
        if(isMusicActived){
            tokenService.createToken(requestDto.getTokenDto(), requestDto.getUserLoginDto());
            Session newSession = sessionService.createSession(requestDto.getSessionDto(), requestDto.getUserLoginDto());
            if(newSession != null){
                QRCode code = qrCodeService.generateQRCode(newSession, requestDto.getUserLoginDto());
                System.out.println(code.toString());
                if(code != null){
                    GetUsersTopTracks getUsersTopTracks = new GetUsersTopTracks(requestDto.getTokenDto().getAccessToken(), 10, 0);
                    getUsersTopTracks.getUsersTopTracks().ifPresent(trackPaging -> {
                        PollTurn pollTurn = pollTurnService.createPollTurn(newSession);
                        List<Music> musics = new ArrayList<>();
                        List<MusicStreamingServiceInformation> musicStreamingServiceInformations = new ArrayList<>();
                        StreamingService streamingService = streamingServiceRepository.findByName("spotify");
                        for (int i = 0; i < 3; i++) {
                            System.out.println("test");
                            Music music = musicService.generateMusic(trackPaging.getItems()[i]);
                            musics.add(music);

                            MusicStreamingServiceInformation musicStreamingServiceInformation =
                                    musicService.generateMusicStreamingServiceInformation(
                                            trackPaging.getItems()[i], music, streamingService);

                            musicStreamingServiceInformations.add(musicStreamingServiceInformation);
                            SuggestedMusic suggestedMusic = musicService.generateSuggestedMusic(music, pollTurn);
                        }
                    });
                    returnMessage = "{\"response\":\"QRCode generated\"}";
                } else {
                    returnMessage = "{\"response\":\"QRCode not generated\"}";
                }
            }
            return returnMessage;
        } else {
            return "{\"response\":\"Music is not played\"}";
        }

    }


}
