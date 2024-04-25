package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;


import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetUsersTopTracksExample {
    private static final String accessToken = "BQB-NhKcLu5bgqWvm6YLiHKDOA5MX3NhmYEdkUn1lJggVEOIWgTpFzm1sqz3A203rGRqmmOVF5m3TuGpfxIaxoLsR3udVd0LR1mAiCfq6pqLftOwbE4qYxrS97mFy4G3KImhEkdY6jclLb-f_NjQ6dJqGkwdpYbWrdZBkA9tHymk-hmzu4a7TwZbQYE8mi4Zz_sDChB2dQmfIvgaNZ1huYJVAY29UlP4_f3UF2jgS1Uc8CX7gGVLwSMgYcRhm1vGq8hPNI8FR4sNgztnZkzLGIBw7ykyQ2T4-koqGg7yo7J4rnF-lEzv2lKwUF79iRpDjw";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
//          .limit(10)
//          .offset(0)
//          .time_range("medium_term")
            .build();

    public static void getUsersTopTracks_Sync() {
        try {
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();

            System.out.println("Total: " + trackPaging.getTotal());
            System.out.println(trackPaging.getItems()[0].getName());
            System.out.println(trackPaging.getItems()[0].getAlbum());
            System.out.println(trackPaging.getItems()[0].getAlbum().getUri());
            System.out.println(trackPaging.getItems()[0].getLinkedFrom());
            System.out.println(trackPaging.getItems()[0].getUri());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        getUsersTopTracks_Sync();
    }
}