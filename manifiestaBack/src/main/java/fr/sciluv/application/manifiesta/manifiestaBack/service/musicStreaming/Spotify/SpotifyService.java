package fr.sciluv.application.manifiesta.manifiestaBack.service.musicStreaming.Spotify;

import fr.sciluv.application.manifiesta.manifiestaBack.config.SpotifyConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;

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

            return authorizationCodeCredentials.getAccessToken();
        } catch (IOException | SpotifyWebApiException | org.apache.hc.core5.http.ParseException e) {
            System.out.println("Error during token retrieval: " + e.getMessage());
            return null;
        }
    }
}
