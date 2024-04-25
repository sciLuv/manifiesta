package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;


import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetListOfCurrentUsersPlaylistsExample {
    private static final String accessToken = "BQB-NhKcLu5bgqWvm6YLiHKDOA5MX3NhmYEdkUn1lJggVEOIWgTpFzm1sqz3A203rGRqmmOVF5m3TuGpfxIaxoLsR3udVd0LR1mAiCfq6pqLftOwbE4qYxrS97mFy4G3KImhEkdY6jclLb-f_NjQ6dJqGkwdpYbWrdZBkA9tHymk-hmzu4a7TwZbQYE8mi4Zz_sDChB2dQmfIvgaNZ1huYJVAY29UlP4_f3UF2jgS1Uc8CX7gGVLwSMgYcRhm1vGq8hPNI8FR4sNgztnZkzLGIBw7ykyQ2T4-koqGg7yo7J4rnF-lEzv2lKwUF79iRpDjw";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest = spotifyApi
            .getListOfCurrentUsersPlaylists()
//          .limit(10)
//          .offset(0)
            .build();

    public static void getListOfCurrentUsersPlaylists_Sync() {
        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = getListOfCurrentUsersPlaylistsRequest.execute();

            System.out.println("Total: " + playlistSimplifiedPaging.getTotal());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getListOfCurrentUsersPlaylists_Async() {
        try {
            final CompletableFuture<Paging<PlaylistSimplified>> pagingFuture = getListOfCurrentUsersPlaylistsRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = pagingFuture.join();

            System.out.println("Total: " + playlistSimplifiedPaging.getTotal());
            System.out.println(playlistSimplifiedPaging.getItems()[0].getUri());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        getListOfCurrentUsersPlaylists_Sync();
        getListOfCurrentUsersPlaylists_Async();
    }
}