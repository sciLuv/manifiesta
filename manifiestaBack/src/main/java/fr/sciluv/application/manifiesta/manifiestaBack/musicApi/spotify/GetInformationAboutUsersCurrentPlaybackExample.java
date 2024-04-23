package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlayingContext;
import se.michaelthelin.spotify.requests.data.player.GetInformationAboutUsersCurrentPlaybackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetInformationAboutUsersCurrentPlaybackExample {
    private static final String accessToken = "BQDTxWwErThUvHBQPjgaaDQfx4bFHgymvgq2aZBNjqv-fefxiKlxuR_5Xu-wY2tZ0BoIgUi9po-0j2BUn6eNJW4ETKmvSykbHrmd5VOE2FDvs8cWDms393djAueL6M0YIIYWdlsAm0LFDyaqo5o643z054K8f2KLU_R2P8EHtyzmZ48sedUw4AWEOOrRjXh2hgxLU9ECZmkKKs9w9XFI-DG3ETj4I1B-4FTDiM6a1xZxj4RXoeoUQURd4Odb5akJYod32Gm-Bre19mxDDifVOZLS6O03ION_m1hZ3KQc5THsU3cLjC6gdvbacbiLRL-Maw";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetInformationAboutUsersCurrentPlaybackRequest getInformationAboutUsersCurrentPlaybackRequest =
            spotifyApi.getInformationAboutUsersCurrentPlayback()
//                  .market(CountryCode.SE)
//                  .additionalTypes("track,episode")
                    .build();

    public static void getInformationAboutUsersCurrentPlayback_Sync() {
        try {
            final CurrentlyPlayingContext currentlyPlayingContext = getInformationAboutUsersCurrentPlaybackRequest.execute();

            System.out.println("Timestamp: " + currentlyPlayingContext.getTimestamp());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getInformationAboutUsersCurrentPlayback_Async() {
        try {
            final CompletableFuture<CurrentlyPlayingContext> currentlyPlayingContextFuture = getInformationAboutUsersCurrentPlaybackRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final CurrentlyPlayingContext currentlyPlayingContext = currentlyPlayingContextFuture.join();

            System.out.println("Timestamp: " + currentlyPlayingContext.getTimestamp());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        getInformationAboutUsersCurrentPlayback_Sync();
        getInformationAboutUsersCurrentPlayback_Async();
    }
}