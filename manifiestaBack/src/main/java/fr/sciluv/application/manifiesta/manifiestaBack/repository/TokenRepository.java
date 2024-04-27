package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Token;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t WHERE t.isRefreshToken = false ORDER BY t.beginDate DESC")
    List<Token> findMostRecentNonRefreshTokens(Pageable pageable);

    default Token findMostRecentNonRefreshToken() {
        return findMostRecentNonRefreshTokens(PageRequest.of(0, 1)).stream().findFirst().orElse(null);
    }
}
