package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.PollTurn;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.SuggestedMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SuggestedMusicRepository extends JpaRepository<SuggestedMusic, Long>{

    List<SuggestedMusic> findByPollTurn(PollTurn pollTurn);
}
