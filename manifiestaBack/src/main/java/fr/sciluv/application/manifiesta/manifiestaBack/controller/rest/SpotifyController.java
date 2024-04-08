package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;

import fr.sciluv.application.manifiesta.manifiestaBack.config.SpotifyConfig;
import fr.sciluv.application.manifiesta.manifiestaBack.service.SpotifyService;
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
    private static final String clientId = "4cb9747367814a04be45cbd1493d586c";
    private static final String clientSecret = "zudknyqbh3wunbhcvg9uyvo7uwzeu6nne";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:3000/spotify/");

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(redirectUri)
            .build();

    @GetMapping("/spotify/authorize")
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

    @PostMapping("/spotify/callback")
    public ResponseEntity<?> handleAuthorization(@RequestBody Map<String, Object> payload) {
        String code = (String) payload.get("code");
        System.out.println("Code reçu: " + code);

        // Appeler une méthode pour échanger le code contre un token d'accès
        String accessToken = SpotifyService.exchangeCodeForAccessToken(code);

        // Vous pouvez retourner le token d'accès ou simplement confirmer la réussite
        return ResponseEntity.ok().body("Token récupéré avec succès. Access Token: " + accessToken);
    }

}