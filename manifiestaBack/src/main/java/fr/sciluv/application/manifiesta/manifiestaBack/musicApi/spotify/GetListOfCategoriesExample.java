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
    private static final String accessToken = "BQBdJ6jVJ-9rIWA3ocFWdJT-hPX50Xz9AVOmzRcpM9JVDLpGSjL8n_tJetsZ8QQ9fJ7FeHEmZ1DwvOq0ZMMzA9rUopmUt3Q1XJ1eU5RUSXbqFHCy2KweWBJH-nwMmRGMhM81HBMOL9tTQT-ZdXCmyu9DzCie1Y78VyKFOyWYvhUoENrRbHd8MBG5VRp7hSXLHfUZajTGI1EsIpUg3N_N8iL2GWjtuq0UpsowLI3VDRr3VOHdjCvmbGvrt53hEV3fkegWNdSwIyE5Kop8Q4QY_folaWiVmKjWjhNPHbdMcR9i86paJzUdmeP4fqI9PU68uQ";

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