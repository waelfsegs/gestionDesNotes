package com.pfe.gestionnote.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Semstre} entity.
 */
public class SemstreDTO implements Serializable {
    
    private Long id;

    private Integer annee;

    private Integer numSemstre;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getNumSemstre() {
        return numSemstre;
    }

    public void setNumSemstre(Integer numSemstre) {
        this.numSemstre = numSemstre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SemstreDTO)) {
            return false;
        }

        return id != null && id.equals(((SemstreDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SemstreDTO{" +
            "id=" + getId() +
            ", annee=" + getAnnee() +
            ", numSemstre=" + getNumSemstre() +
            "}";
    }
}
