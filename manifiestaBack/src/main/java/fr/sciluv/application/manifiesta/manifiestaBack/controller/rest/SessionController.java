package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;


import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.sessionParticipant.SessionParticipantDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.CreateSessionRequestDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.JoinSessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationForHomePageDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.SessionInformationToSendDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.sessionParticipant.SessionParticipantNameAndIsGuestDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.sessionParticipant.SessionParticipantNameAndIsGuestListDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.StreamingServiceRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.*;
import fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify.SpotifyService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.util.FindUsersInformationInJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@CrossOrigin(exposedHeaders = {"New-Access-Token", "New-Refresh-Token"})
@RestController
public class SessionController {

    private final SessionService sessionService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SpotifyService spotifyService;
    @Autowired
    private MusicService musicService;
    @Autowired
    private PollTurnService pollTurnService;
    @Autowired
    private UserService userService;
    @Autowired
    private StreamingServiceRepository streamingServiceRepository;
    @Autowired
    private RegularSpotifyApiCallForSessionUpdate regularSpotifyApiCallForSessionUpdate;
    @Autowired
    private SpotifyApiCallManager spotifyApiCallManager;

    


    public SessionController(@Lazy SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/createSession")
    public String createSession(@RequestBody CreateSessionRequestDto requestDto) throws IOException, ParseException, org.apache.hc.core5.http.ParseException, SpotifyWebApiException {
        String accessToken = requestDto.getTokenDto().getAccessToken();
        System.out.println(requestDto.toString());


        // Check if music is played
        if(spotifyService.isMusicPlayed(accessToken, accessToken)){
            // put spotify token (refresh et access) on db
            tokenService.createToken(requestDto.getTokenDto(), requestDto.getUserLoginDto());
            // create session
            Session newSession = sessionService.createSession(requestDto.getSessionDto(), requestDto.getUserLoginDto(), requestDto.isQrCodeGlobal());
            if(newSession != null){
                // generate QRCode (in reality, it's a string with code to go to the session)
                    // get top tracks of user
                    musicService.findMusicsOnStreamingServiceForAPollTurn1(newSession, accessToken);
                    User user = userService.getUser(requestDto.getUserLoginDto().getUsername());
                    sessionService.createParticipantForSessionOwner(user, newSession);

                    // Get or create a new instance of RegularSpotifyApiCallForSessionUpdate
                    RegularSpotifyApiCallForSessionUpdate apiCall = spotifyApiCallManager.getOrCreateApiCall(newSession.getIdSession());
                    apiCall.beginRegularApiCallProcess(newSession);

                    return"{\"sessionCode\":\"" + newSession.getQrCode().getQrCodeInfo() + "\"" +
                            ",\"SessionPassword\":\"" + newSession.getPassword() + "\"}";
            } else {
                return "{\"response\":\"Session not created\"}";
            }
        } else {
            return "{\"response\":\"Music is not played\"}";
        }
    }


    @PostMapping("/joinSession")
    public synchronized SessionInformationToSendDto joinSession(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody JoinSessionDto joinSessionDto) {
        System.out.println("+++++++++++++++entrer dans joinSessionController+++++++++++++++");
        if(joinSessionDto.getQrCodeInfo() == null){
            System.out.println("QRCode is null");
            return null;
        }
        FindUsersInformationInJWT findUsersInformationInJWT = new FindUsersInformationInJWT(authHeader);
        String username = findUsersInformationInJWT.findUserNameinJWT();
        String role = findUsersInformationInJWT.findUserRolesInJWT();
        System.out.println("joinSessionController Test");
        SessionParticipantDto sp = sessionService.createParticipantForSession(username, joinSessionDto.getQrCodeInfo() , role);
        SessionInformationToSendDto joinSessionDto1 = sessionService.joinSession(joinSessionDto, sp, username);
        System.out.println("+++++++++++++++Sortie de joinSessionController+++++++++++++++");
        return joinSessionDto1;
    }

    @GetMapping("getOwnSessionInformation")
    public SessionInformationForHomePageDto GetOwnerSessionInformation(@RequestHeader("Authorization") String authHeader) {
        FindUsersInformationInJWT findUsersInformationInJWT = new FindUsersInformationInJWT(authHeader);
        String username = findUsersInformationInJWT.findUserNameinJWT();

        SessionInformationForHomePageDto sessionInformationForHomePageDto = sessionService.findOwnAndNotEndSessionInformation(username);
        if(sessionInformationForHomePageDto != null){
            return sessionInformationForHomePageDto;
        } else {
            return null;
        }
    }

    @GetMapping("getParticipantSessionInformation")
    public List<SessionInformationForHomePageDto> getParticipantSessionInformation(@RequestHeader("Authorization") String authHeader) {
        FindUsersInformationInJWT findUsersInformationInJWT = new FindUsersInformationInJWT(authHeader);
        String username = findUsersInformationInJWT.findUserNameinJWT();

        List<SessionInformationForHomePageDto> sessionInformationForHomePageDtos = sessionService.findParticipantNotEndSessionInformation(username);
        if(sessionInformationForHomePageDtos != null){
            return sessionInformationForHomePageDtos;
        } else {
            return null;
        }
    }

    @PostMapping("endSession")
    public String endSession(@RequestHeader("Authorization") String authHeader, @RequestBody JoinSessionDto joinSessionDto)
    {
        FindUsersInformationInJWT findUsersInformationInJWT = new FindUsersInformationInJWT(authHeader);
        String username = findUsersInformationInJWT.findUserNameinJWT();
        sessionService.endSession(username, joinSessionDto.getQrCodeInfo());
        return "{\"response\":\"Session ended\"}";
    }

    @PostMapping("leaveSession")
    public String leaveSession(@RequestHeader("Authorization") String authHeader, @RequestBody JoinSessionDto joinSessionDto)
    {
        FindUsersInformationInJWT findUsersInformationInJWT = new FindUsersInformationInJWT(authHeader);
        String username = findUsersInformationInJWT.findUserNameinJWT();
        String response =  sessionService.leaveSession(username, joinSessionDto.getQrCodeInfo());
        return "{\"response\":\"" + response + "\"}";
    }

    @PostMapping("GetAllParticipantsOfSession")
    public SessionParticipantNameAndIsGuestListDto getAllParticipantsOfSession(@RequestBody JoinSessionDto joinSessionDto){
        return sessionService.getAllParticipantsOfSession(joinSessionDto.getQrCodeInfo());
    }


}
