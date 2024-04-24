package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Token;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.TokenDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.UserLoginDto;

public interface TokenService {
    void createToken(TokenDto token, UserLoginDto user);
}
