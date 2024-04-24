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
    private static final String accessToken = "BQCdaqqNh0LXkDTRD5K4XYbz6Py0NrXOMDq_Cb0EnHZsSi_Nyv-3iMNEjs4cVc7Y-FqOMQPE2FHAxFGntFviY0VxpdSt46VtrdJUqy1qm9JPTdPOGnMUYF07Jeff3c7NmgKbAtP2o50x31NSzhGym1-Rt-ROL3exrGFISuuPOX0fZPUgD44Vcv-QEdjXLeb_QMCTTH_IIZFYfVahzGRgvw-pAYqx5ZHFJGpmC5progbV0rYOmbPo41Kiji-i3EMfDxka4EpTDFR4HZV8wbjkXZiTdQbfoIlwiHFKUVikaNRA_h5oUdpq5fsZMdkuA5cl1Q";
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