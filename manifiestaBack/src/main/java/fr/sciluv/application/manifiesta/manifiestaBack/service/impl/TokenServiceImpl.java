package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.StreamingService;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Token;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.TokenDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.UserLoginDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.TokenRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.TokenService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    TokenRepository tokenRepository;
    UserService userService;

    @Override
    public void createToken(TokenDto tokenDto, UserLoginDto userDto) {

        User user = userService.getUser(userDto.getUsername());

        Token accessToken = new Token(
                tokenDto.getAccessToken(),
                LocalDateTime.now(),
                3600,
                false,
                user,
                new StreamingService()
        );
        Token refreshToken = new Token(
                tokenDto.getRefreshToken(),
                LocalDateTime.now(),
                3600,
                true,
                user,
                new StreamingService()
        );

        tokenRepository.save(accessToken);
        tokenRepository.save(refreshToken);
    }
}
