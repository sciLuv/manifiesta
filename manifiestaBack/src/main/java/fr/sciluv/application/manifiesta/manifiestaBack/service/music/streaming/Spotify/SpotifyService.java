package fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify;

import fr.sciluv.application.manifiesta.manifiestaBack.config.SpotifyConfig;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Token;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify.AuthorizationCodeRefresh;
import fr.sciluv.application.manifiesta.manifiestaBack.service.TokenService;
import jakarta.annotation.PostConstruct;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import se.michaelthelin.spotify.exceptions.detailed.UnauthorizedException;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

@Service
public class SpotifyService {

    private SpotifyApi spotifyApi;

    private final SpotifyConfig spotifyConfig;

    @Autowired
    TokenService tokenService;

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
        String realAccessToken = createNewAccessIfExpired(accessToken);
        GetInformationAboutUsersCurrentPlayback getInformationAboutUsersCurrentPlayback =
                new GetInformationAboutUsersCurrentPlayback(realAccessToken);
        return getInformationAboutUsersCurrentPlayback.getInformationAboutUsersCurrentPlayback();
    }

    public CurrentlyPlaying getCurrentTrack(String accessToken) throws IOException, ParseException, SpotifyWebApiException {
        String realAccessToken = createNewAccessIfExpired(accessToken);
            GetUsersCurrentlyPlayingTrackRequest request = new GetUsersCurrentlyPlayingTrackRequest.Builder(realAccessToken)
                    .additionalTypes("track")
                    .build();
            return request.execute();
    }
    public String getNewAccessToken(String refreshToken) {
        AuthorizationCodeRefresh authorizationCodeRefresh = new AuthorizationCodeRefresh(refreshToken, spotifyConfig.getClientId(), spotifyConfig.getClientSecret());
        return authorizationCodeRefresh.authorizationCodeRefresh();
    }

    private String findRefreshToken(String tokenFromUser) {
        return tokenService.findFirstRefreshToken(tokenService.findUserByToken(tokenFromUser)).getToken();
    }

    public String createNewAccessToken(String tokenFromUser) {
        User user = tokenService.findUserByToken(tokenFromUser);
        String refreshToken = tokenService.findFirstRefreshToken(user).getToken();
        String newAccessToken = getNewAccessToken(refreshToken);
        tokenService.createToken(newAccessToken, user);
        return newAccessToken;
    }

    public String createNewAccessIfExpired(String accessToken) {
        System.out.println(accessToken);
        Token token = tokenService.findByToken(accessToken);
        System.out.println(token);
        // Calcul de la date d'expiration en ajoutant les secondes d'expiration à beginDate
        LocalDateTime expirationDate = token.getBeginDate().plusSeconds(token.getExpirationTime());

        if (LocalDateTime.now().isAfter(expirationDate)) {
            // Le token a expiré, créer un nouveau access token
            return createNewAccessToken(accessToken);
        }
        // Le token n'est pas expiré, renvoyer l'access token actuel ou une autre logique appropriée
        return accessToken;
    }
}
