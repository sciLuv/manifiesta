package fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify;


import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.player.AddItemToUsersPlaybackQueueRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class AddItemToUsersPlaybackQueue {
    private String accessToken;
    private String trackUri;
    private SpotifyApi spotifyApi;

    private AddItemToUsersPlaybackQueueRequest addItemToUsersPlaybackQueueRequest;

    public AddItemToUsersPlaybackQueue(String accessToken, String trackUri) {
        this.accessToken = accessToken;
        this.trackUri = trackUri;
        this.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken).build();
        this.addItemToUsersPlaybackQueueRequest = spotifyApi
                .addItemToUsersPlaybackQueue(trackUri)
                .build();
    }

    //method to remove all the tracks from the queue
    public void removeAllItemsFromUsersPlaybackQueue() {
        try {
            final String string = addItemToUsersPlaybackQueueRequest.execute();
            System.out.println("---------------------------------");
            System.out.println("Removing all tracks from queue..." + string);
            System.out.println("Null: " + string);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addItemToUsersPlaybackQueue() {
        try {
            final String string = addItemToUsersPlaybackQueueRequest.execute();
            System.out.println("---------------------------------");
            System.out.println("Adding track to queue: " + trackUri + "..." + string);
            System.out.println("Null: " + string);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}