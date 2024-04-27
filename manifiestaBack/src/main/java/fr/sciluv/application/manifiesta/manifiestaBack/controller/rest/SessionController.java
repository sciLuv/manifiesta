package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.CreateSessionRequestDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.JoinSessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationToSendDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.SessionRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify.GetUsersTopTracks;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.StreamingServiceRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.*;
import fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify.SpotifyService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.util.FindUsersInformationInJWT;
import fr.sciluv.application.manifiesta.manifiestaBack.service.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
    private PollTurnService pollTurnService;
    @Autowired
    private SessionParticipantService sessionParticipantService;
    @Autowired
    private UserService userService;

    @Autowired
    private StreamingServiceRepository streamingServiceRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @PostMapping("/createSession")
    public String createSession(@RequestBody CreateSessionRequestDto requestDto) throws IOException, ParseException, org.apache.hc.core5.http.ParseException, SpotifyWebApiException {
        String accessToken = requestDto.getTokenDto().getAccessToken();

        // Check if music is played
        if(spotifyService.isMusicPlayed(accessToken, accessToken)){
            // put spotify token (refresh et access) on db
            tokenService.createToken(requestDto.getTokenDto(), requestDto.getUserLoginDto());
            // generate QRCode
            QRCode code = qrCodeService.generateQRCode(requestDto.getUserLoginDto());
            // create session
            Session newSession = sessionService.createSession(requestDto.getSessionDto(), requestDto.getUserLoginDto(), code);
            if(newSession != null){
                // generate QRCode (in reality, it's a string with code to go to the session)
                if(code != null){
                    // get top tracks of user
                    GetUsersTopTracks getUsersTopTracks = new GetUsersTopTracks(accessToken);
                    //in function of top tracks, generate musics, suggested musics and music streaming service informations

                    getUsersTopTracks.getUsersTopTracks().ifPresent(trackPaging -> {

                        PollTurn pollTurn = pollTurnService.createPollTurn(newSession);
                        StreamingService streamingService = streamingServiceRepository.findByName("Spotify");

                        int offSet = trackPaging.getOffset();
                        System.out.println("offset" + offSet);

                        for (Integer number : NumberUtil.generateNumbers(50, requestDto.getSessionDto().getSongsNumber())) {
                            Music music = musicService.generateMusic(trackPaging.getItems()[(number)]);
                            musicService.generateMusicStreamingServiceInformation(trackPaging.getItems()[(number)], music, streamingService);
                            musicService.generateSuggestedMusic(music, pollTurn);
                        }

                        User user = userService.getUser(requestDto.getUserLoginDto().getUsername());
                        sessionParticipantService.createParticipantForSessionOwner(user, newSession);
                    });
                    return"{\"sessionCode\":\"" + code.getQrCodeInfo() + "\"" +
                            ",\"SessionPassword\":\"" + newSession.getPassword() + "\"}";
                } else {
                    return"{\"response\":\"QRCode not generated\"}";
                }
            } else {
                return "{\"response\":\"Session not created\"}";
            }
        } else {
            return "{\"response\":\"Music is not played\"}";
        }
    }


    @PostMapping("joinSession")
    public SessionInformationToSendDto joinSession(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody JoinSessionDto joinSessionDto) {

        FindUsersInformationInJWT findUsersInformationInJWT = new FindUsersInformationInJWT(authHeader);
        String username = findUsersInformationInJWT.findUserNameinJWT();
        String role = findUsersInformationInJWT.findUserRolesInJWT();

        sessionParticipantService.createParticipantForSession(username, joinSessionDto.getQrCodeInfo() , role);
        SessionInformationToSendDto joinSessionDto1 = sessionService.joinSession(joinSessionDto);
        System.out.println(joinSessionDto1.toString());
        return joinSessionDto1;
    }

    @GetMapping("/numberOfParticipantsInSession")
    public String sessionParticipantsNumbers() {
        return "session";
    }

    @GetMapping("/ListOfParticipantsInSession")
    public String sessionListOfParticipants() {
        return "session";
    }



}
