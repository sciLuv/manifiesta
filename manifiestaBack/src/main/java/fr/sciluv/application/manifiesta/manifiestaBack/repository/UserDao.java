package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDao extends CrudRepository<User, Long>{
    User findByUsername(String username);
}
