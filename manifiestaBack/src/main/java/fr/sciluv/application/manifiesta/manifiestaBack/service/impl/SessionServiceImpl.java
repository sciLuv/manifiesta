package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Token;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.SessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.UserLoginDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.TokenRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.SessionService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessionServiceImpl implements SessionService {

    UserService userService;

    public Session createSession(SessionDto sessionDto, UserLoginDto userLoginDto) {

        User user = userService.getUser(userLoginDto.getUsername());



        Session newSession = new Session(
                -1,
                LocalDateTime.now(),
                null,
                sessionDto.getPassword(),
                sessionDto.getSongsNumber(),
                sessionDto.getMusicalStylesNumber(),
                user);
        // Set properties for the session
        return newSession;
    }

}
