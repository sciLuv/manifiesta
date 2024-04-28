package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.UserDao;
import fr.sciluv.application.manifiesta.manifiestaBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired // Injection de userDao
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(User user) {
        user.setCreationDate(LocalDateTime.now());
        return userDao.save(user);
    }

    @Override
    public User getUser(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User getUserBySessionId(Integer SessionId) {
        return userDao.findUserBySessionId(SessionId);
    }

}

