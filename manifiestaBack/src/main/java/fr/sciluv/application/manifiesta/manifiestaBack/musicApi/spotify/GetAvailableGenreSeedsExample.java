package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;


import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.browse.miscellaneous.GetAvailableGenreSeedsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetAvailableGenreSeedsExample {
    private static final String accessToken = "BQC6JtXVhihy0ZOhGedSmR5Bi3Yg3xGXdGhL-pSCG3SphBeBXRGc7La7iY__xGDd70baF6h8uTCMN4u_p2DOsSWiBeqVOIp5aXnu30NbanMppUC9tyBHS25bxLOvcnIYmzEnJhcmKSohN8xiq6zUZmAj9S7HFso8dmmyfo-eeekQ4tQywFmR8lpwAFMwfIIOMgGMzeRND8oAdLnaCbFmwQb0zbU5sttNaA8txxkrMxK9BK-Y2o9XUcHThrJ3dLBRIisKzxAZScz9gCnktlf5aR6NHEWRS_mrR4M3RGCj5nyysfjaNMgUusNyqGHXReUJAg";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetAvailableGenreSeedsRequest getAvailableGenreSeedsRequest = spotifyApi.getAvailableGenreSeeds()
            .build();

    public static void getAvailableGenreSeeds_Sync() {
        try {
            final String[] strings = getAvailableGenreSeedsRequest.execute();

            System.out.println("Length: " + strings.length);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getAvailableGenreSeeds_Async() {
        try {
            final CompletableFuture<String[]> stringsFuture = getAvailableGenreSeedsRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final String[] strings = stringsFuture.join();

            System.out.println("Length: " + strings.length);
            System.out.println(strings[0]);
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        getAvailableGenreSeeds_Sync();
        getAvailableGenreSeeds_Async();
    }
}