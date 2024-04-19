package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.User;
import fr.sciluv.application.manifiesta.manifiestaBack.security.keycloak.AddUser;
import fr.sciluv.application.manifiesta.manifiestaBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

        AddUser addUser = new AddUser();
        boolean keycloakResponse = addUser.addUser(user);

        if(keycloakResponse) return userService.createUser(user).toJSON();
        else return "{\"User\": \"not created\"}";
    }
}
