package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;


import fr.sciluv.application.manifiesta.manifiestaBack.config.SpotifyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class AuthorizationCodeRefresh {

    private String clientId;
    private String clientSecret;
    private String refreshToken;

    private SpotifyApi spotifyApi;
    private AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest;

    public AuthorizationCodeRefresh(String refreshToken, String clientId, String clientSecret) {
        this.refreshToken = refreshToken;
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken)
                .build();

        this.authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();
    }



    public String authorizationCodeRefresh() {
        try {
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            System.out.println("Access Token: " + authorizationCodeCredentials.getAccessToken());
            return authorizationCodeCredentials.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            return "Error: " + e.getMessage();
        }
    }
}