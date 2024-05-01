package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SuggestedMusic;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Token;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.Music.MusicCurrentlyPlayedDto;
import fr.sciluv.application.manifiesta.manifiestaBack.service.util.TimerExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RegularSpotifyApiCallForSessionUpdate {

    void beginRegularApiCallProcess(Session session);

    void executeRegularSpotifyApiCallForSessionUpdate(int delay,Session session);

    SuggestedMusic findMostVotedSuggestedMusic(List<SuggestedMusic> suggestedMusics);

    void stopExecution();

    void StartExecution();
}
