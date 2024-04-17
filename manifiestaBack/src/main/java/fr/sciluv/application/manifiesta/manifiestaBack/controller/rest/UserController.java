package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    //controller to create a new user
    @PostMapping("/createAccount")
    public String createUser(@RequestBody String user) {
        System.out.println("---------------------------------------");
        System.out.println(user);
        System.out.println("---------------------------------------");

        return "{\"message\":\"User created\"}";
    }
}
