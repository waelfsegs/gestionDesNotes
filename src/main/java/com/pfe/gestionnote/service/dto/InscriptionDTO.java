package com.pfe.gestionnote.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Inscription} entity.
 */
public class InscriptionDTO implements Serializable {
    
    private Long id;

    private LocalDate date;

    private LocalDate annee;


    private Long etudiantId;

    private Long classeId;

    private Long groupeId;

    private Long semstreId;
    
    private String matricule;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getAnnee() {
        return annee;
    }

    public void setAnnee(LocalDate annee) {
        this.annee = annee;
    }

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public Long getClasseId() {
        return classeId;
    }

    public void setClasseId(Long classeId) {
        this.classeId = classeId;
    }

    public Long getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Long groupeId) {
        this.groupeId = groupeId;
    }

    public Long getSemstreId() {
        return semstreId;
    }

    public void setSemstreId(Long semstreId) {
        this.semstreId = semstreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InscriptionDTO)) {
            return false;
        }

        return id != null && id.equals(((InscriptionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InscriptionDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", annee='" + getAnnee() + "'" +
            ", etudiantId=" + getEtudiantId() +
            ", classeId=" + getClasseId() +
            ", groupeId=" + getGroupeId() +
            ", semstreId=" + getSemstreId() +
            "}";
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
}
