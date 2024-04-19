package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoSecurityController {

    @GetMapping
    @PreAuthorize("hasRole('client_user')")
    public String hello(){
        return "hello fromSpring Boot ApplicationConnexion (Public)";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('client_admin')")
    public String hello2(){
        return "hello fromSpring Boot ApplicationConnexion (ADMIN)";
    }

}
