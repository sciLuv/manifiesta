package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Music;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.PollTurn;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SuggestedMusic;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.PollTurnRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.PollTurnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PollTurnServiceImpl implements PollTurnService {

    @Autowired
    private PollTurnRepository pollTurnRepository;

    @Override
    public PollTurn createPollTurn(Session session) {
        PollTurn pollTurn = new PollTurn(
            session
        );
        return pollTurnRepository.save(pollTurn);
    }
}
