package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.user.AccountCreation.AddUserToKC;
import fr.sciluv.application.manifiesta.manifiestaBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createAccount")
    public String createUser(@RequestBody User user) {


        AddUserToKC addUserToKC = new AddUserToKC();
        String responseKC = addUserToKC.addingUserToKC(user);
        if(responseKC.equals("User created")) {
        }

        // send the response to the front in JSON format
        return "{\"responseKC\":\"" + responseKC + "\"}";
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }
}
