package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;


import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.player.AddItemToUsersPlaybackQueueRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class addItemToUsersPlaybackQueue {
    private static final String accessToken = "BQBc0NlsxS7D8_e0mxlY597_TZzsskzCP0ddB30RVPeOAVuuWqClUVTaulRl_nIqZAC0Z98Bf1c7j7pbAiHZfNlgb52u1K4Aa_2_enCh8AsB1dqU-sYUT_o-RYLHmGgX20I6_eT58GelxtlpbsByEuhdppABZP-tq26z-UXpE6Mmsz-gWnpoJaaeRNWReznJ8PIXiCiLDj0mPH6JyWVIi1vpkwCm77Sxw3uLAqf2KnUZLXXPOFkc_7GFuFnYc4u6c0KuvugBSY_PlKC8626-eVxpZEcngAhRmStcQ2kV2zOk2W_S4b4no8NAbu3_pxhCZw";
    private static final String trackUri = "spotify:track:3Dy4REq8O09IlgiwuHQ3sk";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final AddItemToUsersPlaybackQueueRequest addItemToUsersPlaybackQueueRequest = spotifyApi
            .addItemToUsersPlaybackQueue(trackUri)
            .build();

    public static void addItemToUsersPlaybackQueue_Sync() {
        try {
            final String string = addItemToUsersPlaybackQueueRequest.execute();

            System.out.println("Null: " + string);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        addItemToUsersPlaybackQueue_Sync();
    }
}