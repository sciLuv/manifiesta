package fr.sciluv.application.manifiesta.manifiestaBack.musicApi.spotify;


import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.Optional;

public class GetUsersTopTracks {
    private String accessToken;
    private SpotifyApi spotifyApi;
    private GetUsersTopTracksRequest getUsersTopTracksRequest;

    private int offset;

    public Optional<Paging<Track>> getUsersTopTracks() {
        try {
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
            int numbersOfTracks = trackPaging.getTotal();

            int RealOffset = (int) Math.floor((Math.random()*numbersOfTracks));
            if(RealOffset+50 > numbersOfTracks){
                RealOffset = numbersOfTracks - 50;
            }

            final GetUsersTopTracksRequest realGetUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
                    .limit(50)
                    .offset(RealOffset)
                    .time_range("medium_term")
                    .build();

            final Paging<Track> realTrackPaging = realGetUsersTopTracksRequest.execute();

            for (Track item : realTrackPaging.getItems()) {
                System.out.println(item.getName());
            }

            return Optional.ofNullable(realTrackPaging);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    //Constructeur

    public GetUsersTopTracks(String accessToken) {
        this.accessToken = accessToken;
        this.offset = offset;
        this.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
        this.getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
                .time_range("medium_term")
                .build();
    }
}