package com.pfe.gestionnote.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Matiere} entity.
 */
public class MatiereDTO implements Serializable {
    
    private Long id;

    private Double coefficientMatiere;

    private Double coefficientTp;

    private Double coefficientDc;

    private Double coefficientExem;

    private String designation;

    private String nom;


    private Long regimeId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCoefficientMatiere() {
        return coefficientMatiere;
    }

    public void setCoefficientMatiere(Double coefficientMatiere) {
        this.coefficientMatiere = coefficientMatiere;
    }

    public Double getCoefficientTp() {
        return coefficientTp;
    }

    public void setCoefficientTp(Double coefficientTp) {
        this.coefficientTp = coefficientTp;
    }

    public Double getCoefficientDc() {
        return coefficientDc;
    }

    public void setCoefficientDc(Double coefficientDc) {
        this.coefficientDc = coefficientDc;
    }

    public Double getCoefficientExem() {
        return coefficientExem;
    }

    public void setCoefficientExem(Double coefficientExem) {
        this.coefficientExem = coefficientExem;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getRegimeId() {
        return regimeId;
    }

    public void setRegimeId(Long regimeId) {
        this.regimeId = regimeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatiereDTO)) {
            return false;
        }

        return id != null && id.equals(((MatiereDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatiereDTO{" +
            "id=" + getId() +
            ", coefficientMatiere=" + getCoefficientMatiere() +
            ", coefficientTp=" + getCoefficientTp() +
            ", coefficientDc=" + getCoefficientDc() +
            ", coefficientExem=" + getCoefficientExem() +
            ", designation='" + getDesignation() + "'" +
            ", nom='" + getNom() + "'" +
            ", regimeId=" + getRegimeId() +
            "}";
    }
}
