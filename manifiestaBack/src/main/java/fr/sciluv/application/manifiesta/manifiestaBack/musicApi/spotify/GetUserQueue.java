package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;

import se.michaelthelin.spotify.requests.data.player.GetTheUsersQueueRequest;
import se.michaelthelin.spotify.model_objects.special.PlaybackQueue;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.*;


import org.apache.hc.core5.http.ParseException;
import java.io.IOException;
import java.util.List;

public class GetUserQueue {

    private String accessToken;

    public GetUserQueue(String accessToken) {
        this.accessToken = accessToken;
    }



    /**
     * Fetches the current user's playback queue from Spotify.
     *
     * @return PlaybackQueue
     * @throws IOException If there is a problem with the network communication.
     * @throws SpotifyWebApiException If Spotify's API returns an error.
     * @throws ParseException If there is an error parsing the response from Spotify.
     */
    public PlaybackQueue getUsersQueue() throws IOException, SpotifyWebApiException, ParseException {
        GetTheUsersQueueRequest request = new GetTheUsersQueueRequest.Builder(accessToken)
                .build();


        return request.execute();
    }

    public static void main(String[] args) {
        try {
            String accessToken = "BQB-NhKcLu5bgqWvm6YLiHKDOA5MX3NhmYEdkUn1lJggVEOIWgTpFzm1sqz3A203rGRqmmOVF5m3TuGpfxIaxoLsR3udVd0LR1mAiCfq6pqLftOwbE4qYxrS97mFy4G3KImhEkdY6jclLb-f_NjQ6dJqGkwdpYbWrdZBkA9tHymk-hmzu4a7TwZbQYE8mi4Zz_sDChB2dQmfIvgaNZ1huYJVAY29UlP4_f3UF2jgS1Uc8CX7gGVLwSMgYcRhm1vGq8hPNI8FR4sNgztnZkzLGIBw7ykyQ2T4-koqGg7yo7J4rnF-lEzv2lKwUF79iRpDjw"; // Remplacez par votre jeton d'accès
            GetUserQueue queueService = new GetUserQueue(accessToken);
            PlaybackQueue queue = queueService.getUsersQueue();
            Track aSong = (Track) queue.getQueue().get(10);
            //get name of song, it image, it id, it duration, it artist
            System.out.println(aSong);
            System.out.println(aSong.getName());
            System.out.println(aSong.getDurationMs());
            System.out.println(aSong.getUri());
            System.out.println(aSong.getArtists()[0].getName());
            System.out.println(aSong.getAlbum().getName());
            Image[] imgs = aSong.getAlbum().getImages();
            for (Image img : imgs) {
                System.out.println(img.getUrl());
            }
            System.out.println("Playback Queue: " + queue);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
        }
    }
}