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
    private static final String accessToken = "BQClgv9UXVdvaQU2YFxMouKHfRH0iIDWJp5vzVZic4vdauRTvL2xrepUhrDUcD1qnqpJYp8kpZYx03LT3QtMAMyKMTC8vfZkLfsGqKtOFFvH6PtFtm0uLNnHChMbYVl0BwiU1Xyz4EugaHwGO2ynAgIwvEMD81bjOiql2d7nRKzFFeQX1tkbeKlLHtrVdS0zlXmxndQyo6w00qy360OmZC8zHukY9mfhE_AX87XA0J5YUfPqtr0FVFPHvTR4derTIa-L2AxwDYCsEvVu5J-ks9A0nuH9IdJMP-4GhGLzYSQ0Smw5GLrtHXqFXXgMTGNOrQ";

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