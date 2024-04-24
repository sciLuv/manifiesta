package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;


import fr.sciluv.application.manifiesta.manifiestaBack.entity.Session;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.SessionDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.TokenDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.UserDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.UserLoginDto;
import fr.sciluv.application.manifiesta.manifiestaBack.service.SessionService;
import fr.sciluv.application.manifiesta.manifiestaBack.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("public/createSession")
    public String createSession(
            @RequestBody SessionDto sessionDto,
            @RequestBody UserLoginDto userLoginDto,
            @RequestBody TokenDto tokenDto
    )
    {
        tokenService.createToken(tokenDto, userLoginDto);
        sessionService.createSession(sessionDto, userLoginDto);
        System.out.println("Session created");
        return "{\"response\":\"Session created\"}";
    }


}
