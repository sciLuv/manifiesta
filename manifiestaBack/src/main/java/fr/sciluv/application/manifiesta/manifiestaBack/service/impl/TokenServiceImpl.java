package fr.sciluv.application.manifiesta.manifiestaBack.service.impl;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.StreamingService;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.Token;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.TokenDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserLoginDto;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.StreamingServiceRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.TokenRepository;
import fr.sciluv.application.manifiesta.manifiestaBack.service.TokenService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserService userService;

    @Autowired
    StreamingServiceRepository streamingServiceRepository;

    @Override
    public void createToken(TokenDto tokenDto, UserLoginDto userDto) {

        System.out.println(userDto.getUsername());
        User user = userService.getUser(userDto.getUsername());

        StreamingService streamingService = streamingServiceRepository.findByName("spotify");

        Token accessToken = new Token(
                tokenDto.getAccessToken(),
                LocalDateTime.now(),
                3600,
                false,
                user,
                streamingService
        );
        Token refreshToken = new Token(
                tokenDto.getRefreshToken(),
                LocalDateTime.now(),
                3600,
                true,
                user,
                streamingService
        );

        tokenRepository.save(accessToken);
        tokenRepository.save(refreshToken);
    }

    @Override
    public Token findMostRecentNonRefreshToken(User user) {
        return tokenRepository.findMostRecentNonRefreshToken(user);
    }
}
