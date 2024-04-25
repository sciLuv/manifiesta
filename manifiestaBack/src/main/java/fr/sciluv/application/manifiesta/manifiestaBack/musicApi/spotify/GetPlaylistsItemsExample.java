package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;


import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Episode;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetPlaylistsItemsExample {
    private static final String accessToken = "BQB-NhKcLu5bgqWvm6YLiHKDOA5MX3NhmYEdkUn1lJggVEOIWgTpFzm1sqz3A203rGRqmmOVF5m3TuGpfxIaxoLsR3udVd0LR1mAiCfq6pqLftOwbE4qYxrS97mFy4G3KImhEkdY6jclLb-f_NjQ6dJqGkwdpYbWrdZBkA9tHymk-hmzu4a7TwZbQYE8mi4Zz_sDChB2dQmfIvgaNZ1huYJVAY29UlP4_f3UF2jgS1Uc8CX7gGVLwSMgYcRhm1vGq8hPNI8FR4sNgztnZkzLGIBw7ykyQ2T4-koqGg7yo7J4rnF-lEzv2lKwUF79iRpDjw";
    private static final String playlistId = "3AGOiaoRXMSjswCLtuNqv5";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetPlaylistsItemsRequest getPlaylistsItemsRequest = spotifyApi
            .getPlaylistsItems(playlistId)
//          .fields("description")
//          .limit(10)
//          .offset(0)
//          .market(CountryCode.SE)
//          .additionalTypes("track,episode")
            .build();

    public static void getPlaylistsItems_Sync() {
        try {
            final Paging<PlaylistTrack> playlistTrackPaging = getPlaylistsItemsRequest.execute();

            System.out.println("Total: " + playlistTrackPaging.getTotal());
            System.out.println("Track's first artist: " + ((Track) playlistTrackPaging.getItems()[0].getTrack()));
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        getPlaylistsItems_Sync();
    }
}