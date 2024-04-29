package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserDto;
import fr.sciluv.application.manifiesta.manifiestaBack.entity.dto.user.UserLoginDto;
import fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.user.AccountCreation.AddUserToKC;
import fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.user.UserToken;
import fr.sciluv.application.manifiesta.manifiestaBack.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(exposedHeaders = {"New-Access-Token", "New-Refresh-Token"})
@RestController
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("public/createAccount")
    public String createUser(@RequestBody UserDto user) {
        if(user.getUsername() == "" || user.getMail() == "" || user.getPassword() == "") {
            return "{\"response\":\"\"}";
        }

        AddUserToKC addUserToKC = new AddUserToKC();
        String responseKC = addUserToKC.addingUserToKC(user);
        String responseDB;
        System.out.println(responseKC);
        if(responseKC.equals("User created")) {
            User userEntity = new User(user.getUsername(), user.getMail());
            userService.createUser(userEntity);
            responseDB = "User created";
        } else {
            responseDB = "User not created";
        }

        // send the response to the front in JSON format
        return "{\"responseKC\":\"" + responseKC + "\"" +
                ",\"response\":\"" + responseDB + "\"}";
    }


    @CrossOrigin(exposedHeaders = {"Access-Token", "Refresh-Token"})
    @PostMapping("public/login")
    public String login(@RequestBody UserLoginDto user, HttpServletResponse response) {

        UserToken userToken = new UserToken(user.getUsername(), user.getPassword());
        if(userToken.getToken() == null){
            return "{\"response\":\"User not found\"}";
        } else {
            User userFromDB = userService.getUser(user.getUsername());
            response.addHeader("Access-Token", userToken.getToken());
            response.addHeader("Refresh-Token", userToken.getRefreshToken());
            return "{\"username\":\"" + userFromDB.getUsername() +
                    "\",\"mail\": \"" + userFromDB.getMail() +
                    "\",\"role\": \"user\"}";
        }
    }
    @GetMapping("public/test")
    public String test() {
        return "Hello World!";
    }

    @GetMapping("/test")
    public String test2() {
        return "Hello World! with token verification";
    }
}
