package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.UserDao;
import fr.sciluv.application.manifiesta.manifiestaBack.service.QRCodeService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    private final QRCodeService qrCodeService;

    @Autowired
    public UserServiceImpl(UserDao userDao, @Lazy QRCodeService qrCodeService) {
        this.userDao = userDao;
        this.qrCodeService = qrCodeService;
    }

    @Override
    public User createUser(User user) {

        user.setCreationDate(LocalDateTime.now());
        User userEntity = userDao.save(user);
        qrCodeService.generateGlobalQRCode(userEntity);
        return userEntity;
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

