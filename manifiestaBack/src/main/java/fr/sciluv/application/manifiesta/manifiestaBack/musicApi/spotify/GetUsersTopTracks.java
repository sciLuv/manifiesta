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

    public Optional<Paging<Track>> getUsersTopTracks() {
        try {
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
            return Optional.ofNullable(trackPaging);

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    //Constructeur

    public GetUsersTopTracks(String accessToken, int limit, int offset) {
        this.accessToken = accessToken;
        this.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();
        this.getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
                .limit(limit)
                .offset(offset)
                .time_range("medium_term")
                .build();
    }
}