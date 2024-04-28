package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDao extends CrudRepository<User, Long>{
    User findByUsername(String username);

    @Query("SELECT s.user FROM Session s WHERE s.id = :id")
    User findUserBySessionId(Integer id);

}
