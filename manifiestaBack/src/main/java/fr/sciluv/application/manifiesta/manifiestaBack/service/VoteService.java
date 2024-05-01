package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.SessionParticipant;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SuggestedMusic;
import org.springframework.stereotype.Service;


public interface VoteService {
    int countBySuggestedMusic(SuggestedMusic suggestedMusic);

    void addVote(String username, int suggestedMusicId, String qrCode, String password);
}
