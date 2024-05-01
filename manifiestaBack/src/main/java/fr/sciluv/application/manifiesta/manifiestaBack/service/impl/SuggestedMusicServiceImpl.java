package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.PollTurn;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SuggestedMusic;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.SuggestedMusicRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.SuggestedMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuggestedMusicServiceImpl implements SuggestedMusicService {

    @Autowired
    SuggestedMusicRepository suggestedMusicRepository;
    @Override
    public List<SuggestedMusic> findByPollTurn(PollTurn pollTurn) {
        return suggestedMusicRepository.findByPollTurn(pollTurn);
    }

    @Override
    public Optional<SuggestedMusic> findById(int id) {
        return suggestedMusicRepository.findById(Long.valueOf(id));
    }


}
