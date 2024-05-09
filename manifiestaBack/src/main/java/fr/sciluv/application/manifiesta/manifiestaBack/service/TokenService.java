package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Token;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.TokenDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserLoginDto;

public interface TokenService {
    void createToken(TokenDto token, UserLoginDto user);

    void createToken(String token, User user);

    Token findMostRecentNonRefreshToken(User user);

    Token findFirstRefreshToken(User user);

    User findUserByToken(String token);

    Token findByToken(String token);
}
