package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;

import fr.sciluv.application.manifiesta.manifiestaBack.config.SpotifyConfig;
import fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify.SpotifyService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

import java.net.URI;
import java.util.Map;

@RestController
public class SpotifyController {
    private final SpotifyConfig spotifyConfig;
    private final SpotifyService spotifyService; // Ajout du service Spotify comme dépendance
    private SpotifyApi spotifyApi;

    @Autowired
    public SpotifyController(SpotifyConfig spotifyConfig, SpotifyService spotifyService) { // Injection de SpotifyService
        this.spotifyConfig = spotifyConfig;
        this.spotifyService = spotifyService; // Initialisation de SpotifyService
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
    @GetMapping("public/spotify/authorize")
    public String getAuthorizationUri() {
        String[] scopes = {
                "ugc-image-upload", "user-read-recently-played", "user-top-read", "user-read-playback-position",
                "user-read-playback-state", "user-modify-playback-state", "user-read-currently-playing",
                "app-remote-control", "streaming", "playlist-modify-public", "playlist-modify-private",
                "playlist-read-private", "playlist-read-collaborative", "user-follow-modify", "user-follow-read",
                "user-library-modify", "user-library-read", "user-read-email", "user-read-private"
        };

        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
                .state("some_state")
                .scope(String.join(" ", scopes))
                .show_dialog(true)
                .build();

        URI authorizationUri = authorizationCodeUriRequest.execute();
        return authorizationUri.toString();
    }

    @PostMapping("public/spotify/callback")
    public ResponseEntity<?> handleAuthorization(@RequestBody Map<String, Object> payload) {
        String code = (String) payload.get("code");
        System.out.println("Code reçu: " + code);

        // Utilisation de l'instance spotifyService pour appeler la méthode
        String[] accessAndRefreshToken = spotifyService.exchangeCodeForAccessToken(code).split(" ");


        // Vous pouvez retourner le token d'accès ou simplement confirmer la réussite
        return ResponseEntity.ok().body(
                "{\"spotifyToken\":\"" + accessAndRefreshToken[0] +
                "\",\"spotifyRefreshToken\":\"" + accessAndRefreshToken[1] + "\"}");
    }

}