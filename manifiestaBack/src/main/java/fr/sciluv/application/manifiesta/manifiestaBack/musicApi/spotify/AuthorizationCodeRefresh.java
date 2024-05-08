package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;


import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class AuthorizationCodeRefresh {

    SpotifyAttributes spotifyAttributes = new SpotifyAttributes();


    private String clientId;
    private String clientSecret;
    private String refreshToken;

    private SpotifyApi spotifyApi;
    private AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest;

    public AuthorizationCodeRefresh(SpotifyAttributes spotifyAttributes) {
        this.spotifyAttributes = spotifyAttributes;
        this.refreshToken = spotifyAttributes.getRefreshToken();
        this.clientId = spotifyAttributes.getClientId();
        this.clientSecret = spotifyAttributes.getClientSecret();

        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRefreshToken(refreshToken)
                .build();

        this.authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();
    }



    public String authorizationCodeRefresh_Sync() {
        try {
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            return authorizationCodeCredentials.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            return "Error: " + e.getMessage();
        }
    }


    public static void main(String[] args) {
        SpotifyAttributes spotifyAttributes1 = new SpotifyAttributes();
        new AuthorizationCodeRefresh(spotifyAttributes1).
        authorizationCodeRefresh_Sync();
    }
}