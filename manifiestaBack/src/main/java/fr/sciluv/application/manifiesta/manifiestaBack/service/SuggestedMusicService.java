package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.PollTurn;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SuggestedMusic;

import java.util.List;
import java.util.Optional;

public interface SuggestedMusicService {
    List<SuggestedMusic> findByPollTurn(PollTurn pollTurn);

    Optional<SuggestedMusic> findById(int id);

}
