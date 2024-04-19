package fr.sciluv.application.manifiesta.manifiestaBack.repository;

import fr.sciluv.application.manifiesta.manifiestaBack.entity.Compteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteurRepository extends JpaRepository<Compteur, Long> {
}
