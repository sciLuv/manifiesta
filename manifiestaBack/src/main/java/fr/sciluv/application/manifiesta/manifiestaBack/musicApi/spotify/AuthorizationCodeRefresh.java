package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;


import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class AuthorizationCodeRefresh {

    SpotifyAttributes spotifyAttributes = new SpotifyAttributes();


    private final String clientId = spotifyAttributes.getClientId();
    private final String clientSecret = spotifyAttributes.getClientSecret();
    private final String refreshToken = spotifyAttributes.getRefreshToken();


    private final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRefreshToken(refreshToken)
            .build();
    private final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
            .build();

    public String authorizationCodeRefresh_Sync() {
        System.out.println(refreshToken);
        try {
            AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
            System.out.println("Access Token: " + authorizationCodeCredentials.getAccessToken());

            return authorizationCodeCredentials.getAccessToken();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return "Error: " + e.getMessage();
        }
    }


    public static void main(String[] args) {
        new AuthorizationCodeRefresh().
        authorizationCodeRefresh_Sync();
    }
}