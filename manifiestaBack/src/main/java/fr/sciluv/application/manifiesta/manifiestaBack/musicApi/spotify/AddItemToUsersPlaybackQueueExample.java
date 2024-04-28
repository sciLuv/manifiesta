package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;


        import se.michaelthelin.spotify.SpotifyApi;
        import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
        import se.michaelthelin.spotify.requests.data.player.AddItemToUsersPlaybackQueueRequest;
        import org.apache.hc.core5.http.ParseException;

        import java.io.IOException;
        import java.util.concurrent.CancellationException;
        import java.util.concurrent.CompletableFuture;
        import java.util.concurrent.CompletionException;

public class AddItemToUsersPlaybackQueueExample {
    private static final String accessToken = "BQCxOw3N6dUFcb37gAyZTdV0dADWS2THvr6NQBrr_Qj6KbIbsbVMkb63zVuV3CSLFGur5JrnKGhlXpHrRCbOTnaF08HchaoY8wEthrY0CkWb3G_RdQQYP_JCXObKfmt6VyFJgD-WSvlwuRd0sXGXYZsUdPx0SDKsXr3I4_wGNePtO46p-XilwJSJ8cmFK2xx6bA0oJHB9fnATYzmnarX_lM8ZkA3jBs6cT74kpvRNKgBKFzFmpzTBUrhv3YSw-tI4kgZW7SZ9is51SPaQNFBryRsdDcvkPGaG3aZLtY5OuZtpliz1qZe2cN3pfEsnNBRnw";
    private static final String trackUri = "spotify:track:01iyCAUm8EvOFqVWYJ3dVX";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final AddItemToUsersPlaybackQueueRequest addItemToUsersPlaybackQueueRequest = spotifyApi
            .addItemToUsersPlaybackQueue(trackUri)
//    .device_id("5fbb3ba6aa454b5534c4ba43a8c7e8e45a63ad0e")
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