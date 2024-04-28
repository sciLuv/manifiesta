package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.SuggestedMusic;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.suggestedMusic = :suggestedMusic")
    int countBySuggestedMusic(SuggestedMusic suggestedMusic);
}
