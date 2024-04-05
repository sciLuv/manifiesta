package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
@CrossOrigin("${front.url}")
public class TestController {

    @GetMapping("/test")
    public String test1() {
        return "oui";
    }

    @MessageMapping("/update")
    @SendTo("/topic/updates")
    public String sendUpdate(String message) {
        return message;
    }
}

