package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.*;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicCurrentlyPlayedDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.MusicRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.SelectedMusicRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.StreamingServiceRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.*;
import fr.sciluv.application.manifiesta.manifiestaBack.service.music.streaming.Spotify.AddItemToUsersPlaybackQueue;
import fr.sciluv.application.manifiesta.manifiestaBack.service.util.TimerExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegularSpotifyApiCallForSessionUpdateImpl implements RegularSpotifyApiCallForSessionUpdate {

    boolean isSessionEnded = false;

    @Autowired
    MusicService musicService;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    SuggestedMusicService suggestedMusicService;
    @Autowired
    VoteService voteService;
    @Autowired
    PollTurnService pollTurnService;

    @Autowired
    private SelectedMusicRepository selectedMusicRepository;

    @Autowired
    private StreamingServiceRepository streamingServiceRepository;

    @Autowired
    MusicStreamingServiceInformationService musicStreamingServiceInformationService;



    TimerExecution executor = new TimerExecution();

    public void beginRegularApiCallProcess(Session session) {
        StartExecution();

        Token token = tokenService.findMostRecentNonRefreshToken(userService.getUserBySessionId(session.getIdSession()));
        MusicCurrentlyPlayedDto musicCurrentlyPlayedDto =  musicService.musicCurrentlyPlayingToJSON(token);
        int delay = musicCurrentlyPlayedDto.getDurationMs() - musicCurrentlyPlayedDto.getProgressMs();

        executeRegularSpotifyApiCallForSessionUpdate(delay, session);
    }

    public void executeRegularSpotifyApiCallForSessionUpdate(int delay, Session session) {
        if(isSessionEnded) {
            return;
        }
        Token token = tokenService.findMostRecentNonRefreshToken(userService.getUserBySessionId(session.getIdSession()));
        MusicCurrentlyPlayedDto musicCurrentlyPlayedDto =  musicService.musicCurrentlyPlayingToJSON(token);

        int trueDelay = delay-3000;
        executor.executeAfterDelay(trueDelay, () -> {
            System.out.println("Ce message est affiché après un délai de " + trueDelay + " secondes.");

            PollTurn pt = pollTurnService.findFirstBySessionOrderByNumberTurnDesc(session);
            List<SuggestedMusic> sm = suggestedMusicService.findByPollTurn(pt);

            SuggestedMusic winnerSuggestedMusic = findMostVotedSuggestedMusic(sm);
            Music winnerMusic = musicService.findMusicBySuggestedMusic(winnerSuggestedMusic);

            SelectedMusic selectedMusic = new SelectedMusic(winnerMusic, pt);
            selectedMusicRepository.save(selectedMusic);

            musicService.findMusicsOnStreamingServiceForAPollTurn1(session, token.getToken());

            StreamingService streamingService = streamingServiceRepository.findByName("Spotify");

            MusicStreamingServiceInformation musicStreamingServiceInformation = musicStreamingServiceInformationService.findByMusicAndStreamingService(winnerMusic, streamingService);

            System.out.println(musicStreamingServiceInformation.getUrl_link());
            AddItemToUsersPlaybackQueue addItemToUsersPlaybackQueue =
                    new AddItemToUsersPlaybackQueue(
                            token.getToken(),
                            musicStreamingServiceInformation.getUrl_link()
                    );

            addItemToUsersPlaybackQueue.addItemToUsersPlaybackQueue();
            executeRegularSpotifyApiCallForSessionUpdate(musicStreamingServiceInformation.getDuration(), session);

        });
    }

    public SuggestedMusic findMostVotedSuggestedMusic(List<SuggestedMusic> suggestedMusics) {
        SuggestedMusic mostVoted = null;
        int maxVotes = 0;

        for (SuggestedMusic suggestedMusic : suggestedMusics) {
            int voteCount = voteService.countBySuggestedMusic(suggestedMusic);
            if (voteCount >= maxVotes) {
                maxVotes = voteCount;
                mostVoted = suggestedMusic;
            }
        }

        return mostVoted;
    }


    public void stopExecution() {
        isSessionEnded = true;
    }

    @Override
    public void StartExecution() {
        isSessionEnded = false;
    }
}
