package fr.sciluv.application.manifiesta.manifiestaBack.service;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Compteur;
import fr.sciluv.application.manifiesta.manifiestaBack.repository.CompteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompteurService {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private CompteurRepository compteurRepository;

    @Transactional
    public void incrementerEtNotifier() {
        Compteur compteur = compteurRepository.findById(1L).orElse(new Compteur());
        compteur.setValeur(compteur.getValeur() + 1);
        compteurRepository.save(compteur);

        // Envoie la nouvelle valeur du compteur à tous les abonnés du topic "/topic/compteur"
        template.convertAndSend("/topic/compteur", compteur.getValeur());
    }
}
