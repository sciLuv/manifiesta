package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;


import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Category;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.browse.GetListOfCategoriesRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetListOfCategoriesExample {
    private static final String accessToken = "BQB-NhKcLu5bgqWvm6YLiHKDOA5MX3NhmYEdkUn1lJggVEOIWgTpFzm1sqz3A203rGRqmmOVF5m3TuGpfxIaxoLsR3udVd0LR1mAiCfq6pqLftOwbE4qYxrS97mFy4G3KImhEkdY6jclLb-f_NjQ6dJqGkwdpYbWrdZBkA9tHymk-hmzu4a7TwZbQYE8mi4Zz_sDChB2dQmfIvgaNZ1huYJVAY29UlP4_f3UF2jgS1Uc8CX7gGVLwSMgYcRhm1vGq8hPNI8FR4sNgztnZkzLGIBw7ykyQ2T4-koqGg7yo7J4rnF-lEzv2lKwUF79iRpDjw";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetListOfCategoriesRequest getListOfCategoriesRequest = spotifyApi.getListOfCategories()
//          .country(CountryCode.SE)
//          .limit(10)
//          .offset(0)
//          .locale("sv_SE")
            .build();

    public static void getListOfCategories_Sync() {
        try {
            final Paging<Category> categoryPaging = getListOfCategoriesRequest.execute();

            System.out.println("Total: " + categoryPaging.getTotal());
            System.out.println(categoryPaging.getItems()[0].getName());
            System.out.println(categoryPaging.getItems()[0]);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getListOfCategories_Async() {
        try {
            final CompletableFuture<Paging<Category>> pagingFuture = getListOfCategoriesRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final Paging<Category> categoryPaging = pagingFuture.join();

            System.out.println("Total: " + categoryPaging.getTotal());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        getListOfCategories_Sync();
        getListOfCategories_Async();
    }
}