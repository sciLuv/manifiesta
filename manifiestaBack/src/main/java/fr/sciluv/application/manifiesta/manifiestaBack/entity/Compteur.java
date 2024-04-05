package fr.sciluv.application.manifiesta.manifiestaBack.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "compteur")
public class Compteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private int valeur;

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
}
