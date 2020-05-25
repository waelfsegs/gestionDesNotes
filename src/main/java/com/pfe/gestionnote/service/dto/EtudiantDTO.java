package com.pfe.gestionnote.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Etudiant} entity.
 */
public class EtudiantDTO implements Serializable {
    
    private Long id;

    private Integer cin;

    private String nom;

    private String matricule;

    private String prenom;

    private Integer tel;

    private LocalDate dateNais;

    
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

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public LocalDate getDateNais() {
        return dateNais;
    }

    public void setDateNais(LocalDate dateNais) {
        this.dateNais = dateNais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtudiantDTO)) {
            return false;
        }

        return id != null && id.equals(((EtudiantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtudiantDTO{" +
            "id=" + getId() +
            ", cin=" + getCin() +
            ", nom='" + getNom() + "'" +
            ", matricule='" + getMatricule() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", tel=" + getTel() +
            ", dateNais='" + getDateNais() + "'" +
            "}";
    }
}
