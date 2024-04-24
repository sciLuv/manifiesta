package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import se.michaelthelin.spotify.requests.data.player.GetInformationAboutUsersCurrentPlaybackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class GetInformationAboutUsersCurrentPlayback {
    private String accessToken;

    private SpotifyApi spotifyApi;
    private GetInformationAboutUsersCurrentPlaybackRequest getInformationAboutUsersCurrentPlaybackRequest;

    public GetInformationAboutUsersCurrentPlayback(String accessToken) {
        this.accessToken = accessToken;
        this.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken).build();
        this.getInformationAboutUsersCurrentPlaybackRequest =
                spotifyApi.getInformationAboutUsersCurrentPlayback().build();
    }

    public boolean getInformationAboutUsersCurrentPlayback() {
        try {
            CurrentlyPlayingContext currentlyPlayingContext = getInformationAboutUsersCurrentPlaybackRequest.execute();
            System.out.println("Timestamp: " + currentlyPlayingContext.getTimestamp());
            System.out.println(currentlyPlayingContext.getIs_playing());
            System.out.println(currentlyPlayingContext.getItem());
            System.out.println(currentlyPlayingContext);
            return currentlyPlayingContext.getIs_playing();
        } catch (NullPointerException e) {
            System.out.println("No track currently playing.");
            return false;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

//    public static void main(String[] args) {
//        new GetInformationAboutUsersCurrentPlayback("BQCUI9kOi0qF54M5b2WG-Ta059RGDhD2UMRtXgiEsqODe7THNH9RZgGKsUwTphWhT_AS9UqTJtreeSkDEDU8qTeq_YjIX9SNhUdWnz96-ftrHSicaAiutaIWW-a_et8XMRh3xKA5RKfcTfIsmyj0uBCMK5cG5A503UBV1hCej9BOESNCrmrmL62UeXJvajttJxd72jLXZeIRuNWfZpFBn7bdubaol5wZxRLP9Pup6GyKwk1yftjqef00r1bEWy8t5zB5rSMMC4evJ-vY3tzjwKyXF66zl75PjYCCywWiLab8oABVEZ5EcNLICpcDVZSDDA").
//        getInformationAboutUsersCurrentPlayback_Sync();
//
//    }
}