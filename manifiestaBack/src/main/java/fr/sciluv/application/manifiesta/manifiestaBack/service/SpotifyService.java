package fr.sciluv.application.manifiesta.manifiestaBack.service;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;

public class SpotifyService {

    private static final String clientId = "4cb9747367814a04be45cbd1493d586c";
    private static final String clientSecret = "f5be1c0657e745739cf1307b34d897ef";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3000/spotify/");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    public static String exchangeCodeForAccessToken(String code) {
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

