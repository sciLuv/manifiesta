package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;


import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.*;
import fr.sciluv.application.manifiesta.manifiestaBack.service.SessionService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.TokenService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SpotifyService spotifyService;

    @PostMapping("/createSession")
    public String createSession(@RequestBody CreateSessionRequestDto requestDto) {
        boolean isMusicActived = spotifyService.isMusicPlayed(requestDto.getTokenDto().getAccessToken(), requestDto.getTokenDto().getAccessToken());
        if(isMusicActived){
            tokenService.createToken(requestDto.getTokenDto(), requestDto.getUserLoginDto());
            sessionService.createSession(requestDto.getSessionDto(), requestDto.getUserLoginDto());
            System.out.println("Session created");
            return "{\"response\":\"Session created\"}";
        } else {
            return "{\"response\":\"Music is not played\"}";
        }

    }


}
