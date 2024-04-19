package fr.sciluv.application.manifiesta.manifiestaBack.controller.rest;

import fr.sciluv.application.manifiesta.manifiestaBack.service.CompteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompteurController {

    @Autowired
    private CompteurService compteurService;

    @GetMapping("/incrementer")
    public String incrementerCompteur() {
        compteurService.incrementerEtNotifier();
        return "Compteur incrementé";
    }
}
