package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;


import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.session.JoinSessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify.GetUsersTopTracks;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.StreamingServiceRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.*;
import fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify.SpotifyService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

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
    public String createSession(@RequestBody CreateSessionRequestDto requestDto) throws IOException, ParseException, org.apache.hc.core5.http.ParseException, SpotifyWebApiException {
        String accessToken = requestDto.getTokenDto().getAccessToken();

        AtomicReference<String> returnMessage = new AtomicReference<>("");
        // Check if music is played
        boolean isMusicActived = spotifyService.isMusicPlayed(accessToken, accessToken);
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
                    GetUsersTopTracks getUsersTopTracks = new GetUsersTopTracks(accessToken);
                    //in function of top tracks, generate musics, suggested musics and music streaming service informations

                    getUsersTopTracks.getUsersTopTracks().ifPresent(trackPaging -> {

                        PollTurn pollTurn = pollTurnService.createPollTurn(newSession);
                        StreamingService streamingService = streamingServiceRepository.findByName("spotify");

                        int totalOfMusics = trackPaging.getTotal();
                        int offSet = trackPaging.getOffset();
                        System.out.println("offset" + offSet);

                        List<Music> musics = new ArrayList<>();
                        List<MusicStreamingServiceInformation> musicStreamingServiceInformations = new ArrayList<>();
                        Set<Integer> numbers = NumberUtil.generateNumbers(50, requestDto.getSessionDto().getSongsNumber());
                        for (Integer number : numbers) {
                            Music music = musicService.generateMusic(trackPaging.getItems()[(number)]);
                            musics.add(music);

                            MusicStreamingServiceInformation musicStreamingServiceInformation =
                                    musicService.generateMusicStreamingServiceInformation(
                                            trackPaging.getItems()[(number)], music, streamingService);

                            musicStreamingServiceInformations.add(musicStreamingServiceInformation);
                            SuggestedMusic suggestedMusic = musicService.generateSuggestedMusic(music, pollTurn);
                        }


                        //create JSON for musics
                        String json = "{\"musics\":[";
                        for (int i = 0; i < musics.size(); i++) {
                            json += musicService.musicToJSON(musics.get(i), musicStreamingServiceInformations.get(i));
                            if(i != musics.size()-1) json += ",";
                                    else json += "],";
                        }
                        System.out.println(json);

                        CurrentlyPlaying currentlyPlaying = null;
                        try {
                            currentlyPlaying = spotifyService.getCurrentTrack(accessToken);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (SpotifyWebApiException e) {
                            throw new RuntimeException(e);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        } catch (org.apache.hc.core5.http.ParseException e) {
                            throw new RuntimeException(e);
                        }
                        Track track = (Track) currentlyPlaying.getItem();
                        Music musicActual = musicService.generateMusic(track);
                        MusicStreamingServiceInformation musicStreamingServiceInformation =
                                musicService.generateMusicStreamingServiceInformation(track, musicActual, streamingService);

                        json += " \"currentlyPlayingMusic\" : " + musicService.musicToJSON(musicActual, musicStreamingServiceInformation) + ",";

                        json += "\"currentMusicInformation\": {"
                                + "\"timeStamp\": \"" + currentlyPlaying.getTimestamp() + "\","
                                + "\"progressMs\": \"" + currentlyPlaying.getProgress_ms() + "\"}";

                        json += ",\"sessionInformations\" :  {" +
                                 "\"sessionName\": \"" + newSession.getPassword() + "\"," +
                                "\"sessionCode\": \"" + code.getQrCodeInfo() + "\"," +
                                "\"codeIsGlobal\": \"" + code.getGlobal() + "\"" +
                                "}}";

                        returnMessage.set(json);
                    });

                } else {
                    returnMessage.set("{\"response\":\"QRCode not generated\"}");
                }
            }
            return returnMessage.get();
        } else {
            return "{\"response\":\"Music is not played\"}";
        }

    }


    @PostMapping("/joinSession")
    public String joinSession(@RequestBody JoinSessionDto joinSessionDto) {
        return "session";
    }

    @PostMapping("/findOwnUserActualSession")
    public String joinOwnSession(@RequestBody String joinOwnSessionDto) {
        return "session";
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
