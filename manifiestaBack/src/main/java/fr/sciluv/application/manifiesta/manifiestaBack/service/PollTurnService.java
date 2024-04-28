package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Music;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.PollTurn;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import org.springframework.security.core.parameters.P;

import java.util.Set;

public interface PollTurnService {
    PollTurn createPollTurn(Session session);
    int getPollTurnsBySession(Session session);
    PollTurn findPollTurnBySession(Session session);

    PollTurn findFirstBySessionOrderByNumberTurnDesc(Session session);
}
