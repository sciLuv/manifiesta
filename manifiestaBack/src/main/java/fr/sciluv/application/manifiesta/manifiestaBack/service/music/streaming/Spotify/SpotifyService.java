package fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify;

import fr.sciluv.application.manifiesta.manifiestaBack.config.SpotifyConfig;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Token;
import fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify.GetInformationAboutUsersCurrentPlayback;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.TokenRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.time.LocalDateTime;

@Service
public class SpotifyService {

    private SpotifyApi spotifyApi;

    private final SpotifyConfig spotifyConfig;

    @Autowired
    public SpotifyService(SpotifyConfig spotifyConfig) {
        this.spotifyConfig = spotifyConfig;
    }

    @PostConstruct
    private void init() {
        URI redirectUri = SpotifyHttpManager.makeUri(spotifyConfig.getRedirectUri());
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(spotifyConfig.getClientId())
                .setClientSecret(spotifyConfig.getClientSecret())
                .setRedirectUri(redirectUri)
                .build();
    }

    public String exchangeCodeForAccessToken(String code) {
        try {
            AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            // Log des informations reçues de Spotify
            System.out.println("Access Token: " + authorizationCodeCredentials.getAccessToken());
            System.out.println("Refresh Token: " + authorizationCodeCredentials.getRefreshToken());
            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());

            // Enregistrez ces valeurs selon vos besoins (base de données, session, etc.)
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            return authorizationCodeCredentials.getAccessToken() + " " + authorizationCodeCredentials.getRefreshToken();
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error during token retrieval: " + e.getMessage());
            return null;
        }
    }

    public boolean isMusicPlayed(String accessToken, String refreshToken){
        GetInformationAboutUsersCurrentPlayback getInformationAboutUsersCurrentPlayback = new GetInformationAboutUsersCurrentPlayback(accessToken);
        return getInformationAboutUsersCurrentPlayback.getInformationAboutUsersCurrentPlayback();
    }

    public CurrentlyPlaying getCurrentTrack(String accessToken) throws IOException, SpotifyWebApiException, ParseException, org.apache.hc.core5.http.ParseException {
        GetUsersCurrentlyPlayingTrackRequest request = new GetUsersCurrentlyPlayingTrackRequest.Builder(accessToken)
                .additionalTypes("track")  // Si tu veux aussi des épisodes, par exemple
                .build();
        return request.execute();
    }

//    public static void main(String[] args) throws IOException, ParseException, org.apache.hc.core5.http.ParseException, SpotifyWebApiException {
//        SpotifyService spotifyService = new SpotifyService(new SpotifyConfig());
//        CurrentlyPlaying currentlyPlaying = spotifyService.getCurrentTrack("BQDHGth6IqlZuo2V1BOLc5dIdrVpeSYPRX-A4l_LPS3-p3kh6hkLCH40Nq_oEmunSctr6_xrOWQDOqIgPCHIGspDFtj3i1h6CiLlnlIDQxi5xzHtMoBVTzWnKBfqvuh2Gv5z6hjAebnHKRIOZlG3FJIxMBpuaEa4617Kjr9A4i73YTrhy0P2FfjZpyea4a2dVb8ibvTmPagyCb6bjOT38g8XWe7bV2-4BE6Zq-kUknfPJ_CSg0WkvK6kCWXbyX_Fj0hCFS2WsWknmbbNurD2tsYDE3m8jwOHyUcpsgjIqWkV_4YwHl-ylI5XwDs4VnfTcQ");
//        System.out.println(currentlyPlaying);
//        System.out.println(currentlyPlaying.getTimestamp());
//        System.out.println(currentlyPlaying.getProgress_ms());
//        Track track = (Track) currentlyPlaying.getItem();
//
//        System.out.println(track.getName());
//        System.out.println(track.getArtists()[0].getName());
//    }
}
