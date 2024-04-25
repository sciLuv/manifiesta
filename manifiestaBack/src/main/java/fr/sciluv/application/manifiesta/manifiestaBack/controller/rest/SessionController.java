package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;


import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.*;
import fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify.GetUsersTopTracks;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.StreamingServiceRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.*;
import fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify.SpotifyService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.util.NumberUtil;
import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        // Check if music is played
        boolean isMusicActived = spotifyService.isMusicPlayed(requestDto.getTokenDto().getAccessToken(), requestDto.getTokenDto().getAccessToken());
        if(isMusicActived){
            // put spotify token (refresh et access) on db
            tokenService.createToken(requestDto.getTokenDto(), requestDto.getUserLoginDto());
            // create session
            Session newSession = sessionService.createSession(requestDto.getSessionDto(), requestDto.getUserLoginDto());
            if(newSession != null){
                // generate QRCode (in reality, it's a string with code to go to the session)
                QRCode code = qrCodeService.generateQRCode(newSession, requestDto.getUserLoginDto());
                if(code != null){
                    // get top tracks of user
                    GetUsersTopTracks getUsersTopTracks = new GetUsersTopTracks(requestDto.getTokenDto().getAccessToken());
                    //in function of top tracks, generate musics, suggested musics and music streaming service informations

                    getUsersTopTracks.getUsersTopTracks().ifPresent(trackPaging -> {

                        PollTurn pollTurn = pollTurnService.createPollTurn(newSession);
                        StreamingService streamingService = streamingServiceRepository.findByName("spotify");

                        int totalOfMusics = trackPaging.getTotal();
                        int offSet = trackPaging.getOffset();
                        System.out.println("offset" + offSet);

                        List<Music> musics = new ArrayList<>();
                        List<MusicStreamingServiceInformation> musicStreamingServiceInformations = new ArrayList<>();
                        Set<Integer> numbers = NumberUtil.generateNumbers(50);
                        for (Integer number : numbers) {
                            Music music = musicService.generateMusic(trackPaging.getItems()[(number)]);
                            musics.add(music);

                            MusicStreamingServiceInformation musicStreamingServiceInformation =
                                    musicService.generateMusicStreamingServiceInformation(
                                            trackPaging.getItems()[(number)], music, streamingService);

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
