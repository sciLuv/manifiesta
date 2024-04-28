package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.SuggestedMusic;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.SuggestedMusicRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.VoteRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    VoteRepository voteRepository;

    @Override
    public int countBySuggestedMusic(SuggestedMusic suggestedMusic) {
        return voteRepository.countBySuggestedMusic(suggestedMusic);
    }
}
