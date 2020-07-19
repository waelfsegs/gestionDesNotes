package com.pfe.gestionnote.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Spicialitematiere} entity.
 */
public class SpicialitematiereDTO implements Serializable {

    private Long id;

    private String libelle;


    private Long matiereId;

    private Long specialiteId;
    private String matiereNom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(Long matiereId) {
        this.matiereId = matiereId;
    }

    public Long getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(Long specialiteId) {
        this.specialiteId = specialiteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpicialitematiereDTO spicialitematiereDTO = (SpicialitematiereDTO) o;
        if (spicialitematiereDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), spicialitematiereDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

   

    public String getMatiereNom() {
        return matiereNom;
    }

    public void setMatiereNom(String matiereNom) {
        this.matiereNom = matiereNom;
    }

    @Override
    public String toString() {
        return "SpicialitematiereDTO [id=" + id + ", libelle=" + libelle + ", matiereId=" + matiereId + ", matiereNom="
                + matiereNom + ", specialiteId=" + specialiteId + "]";
    }
}
