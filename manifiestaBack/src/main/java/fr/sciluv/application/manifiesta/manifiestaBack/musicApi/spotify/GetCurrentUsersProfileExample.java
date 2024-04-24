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
    private static final String accessToken = "BQBbkfrJm5pjgz49lL3DHe5x7VPuKX3q_nAMfBr6lFsUtRHtzbqSt6FwWkmAaomeTbMyIm2vOFpd2rxkMMbTLPKsCPDdJIbVmrHXlwhBOVOzK5orGzK8YtNMmQiwkHFeJkCj4rnhlmBJiiO0xt_NGQzNfsOkJ4Bhn_5LUhyZHlzsaEKNzv76eMA5Ac9D950jUj-vt83BAt4U1TScT9V7D5cKJQB0KGZBRTE57pidknTpwTGLYaSHUunfAJxDTYGGPjI2pNt4lLn-mq57Js3swGxKFjvQHn5COTeIe1wQUuCG3eIEFt86t5RMztahB0aOyQ";

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