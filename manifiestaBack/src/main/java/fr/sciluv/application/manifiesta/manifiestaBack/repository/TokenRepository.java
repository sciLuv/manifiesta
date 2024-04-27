package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Token;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("SELECT t FROM Token t WHERE t.isRefreshToken = false AND t.user = :user ORDER BY t.beginDate DESC")
    List<Token> findMostRecentNonRefreshTokens(@Param("user") User user, Pageable pageable);

    default Token findMostRecentNonRefreshToken(User user) {
        return findMostRecentNonRefreshTokens(user, PageRequest.of(0, 1)).stream().findFirst().orElse(null);
    }
}
