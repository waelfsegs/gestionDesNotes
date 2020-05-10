package com.pfe.gestionnote.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Etudiant} entity.
 */
public class EtudiantDTO implements Serializable {
    
    private Long id;

    private Integer cin;

    private String nom;

    private String matericule;

    private String prenom;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCin() {
        return cin;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatericule() {
        return matericule;
    }

    public void setMatericule(String matericule) {
        this.matericule = matericule;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EtudiantDTO etudiantDTO = (EtudiantDTO) o;
        if (etudiantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etudiantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtudiantDTO{" +
            "id=" + getId() +
            ", cin=" + getCin() +
            ", nom='" + getNom() + "'" +
            ", matericule='" + getMatericule() + "'" +
            ", prenom='" + getPrenom() + "'" +
            "}";
    }
}
