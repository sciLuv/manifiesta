package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Music;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.PollTurn;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SuggestedMusic;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.PollTurnRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.SessionRepository;
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

        int existingPollTurns = pollTurnRepository.countBySession(session);

        System.out.println("Nombre de PollTurns existants pour la session : " + existingPollTurns);


        PollTurn pollTurn = new PollTurn(
            session,
            existingPollTurns + 1
        );
        return pollTurnRepository.save(pollTurn);
    }

    @Override
    public int getPollTurnsBySession(Session session) {
        return pollTurnRepository.findAllBySession(session).size();
    }
}
