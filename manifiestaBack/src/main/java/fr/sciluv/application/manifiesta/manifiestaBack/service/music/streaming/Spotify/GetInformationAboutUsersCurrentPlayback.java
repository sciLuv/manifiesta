package fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify;

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
}