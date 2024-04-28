package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;

import java.util.Optional;

public interface UserService {
   User createUser(User user);

    User getUser(String username);

    User getUserBySessionId(Integer id);


}
