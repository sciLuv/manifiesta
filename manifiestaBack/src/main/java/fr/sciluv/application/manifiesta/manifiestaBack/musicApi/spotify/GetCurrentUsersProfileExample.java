package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetCurrentUsersProfileExample {
    private static final String accessToken = "BQAo_YqG9OfaixHeDzKzscGK21xNq5jb4fhV0Gb3reZ0FTNXlG5U_bAI_6nHfhUQlYfk-BP3SoiMNqMXiRnDBSLdtmE8JNFrAqWfyKwucReaCPZ2Wq-s4wBfUs4BWbuo2rfmf_DWQtSrJ1JdWEK4cLirLkdeUIt2E56bxcnhfDIU8IUWFbrPIBiCUXOXBZ5hTVLd3LPCrJBZPwh6jGXjNFsUZVBaxmLoC4HVztTAv9dGx674e52rch_ZaRWKpeEO_VLJoELr625J1Euebcixw59sh4MdLL3XyZHqpUdRkViTl9tHnVsmPpUH8r0Jyl8bgA";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
            .build();

    public static void getCurrentUsersProfile_Sync() {
        try {
            final User user = getCurrentUsersProfileRequest.execute();

            System.out.println("Display name: " + user.getDisplayName());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getCurrentUsersProfile_Async() {
        try {
            final CompletableFuture<User> userFuture = getCurrentUsersProfileRequest.executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final User user = userFuture.join();

            System.out.println("Display name: " + user.getDisplayName());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static void main(String[] args) {
        getCurrentUsersProfile_Sync();
        getCurrentUsersProfile_Async();
    }
}