package com.pfe.gestionnote.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Departement} entity.
 */
public class DepartementDTO implements Serializable {
    
    private Long id;

    private String nomDep;

    private String designiation;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDep() {
        return nomDep;
    }

    public void setNomDep(String nomDep) {
        this.nomDep = nomDep;
    }

    public String getDesigniation() {
        return designiation;
    }

    public void setDesigniation(String designiation) {
        this.designiation = designiation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartementDTO)) {
            return false;
        }

        return id != null && id.equals(((DepartementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartementDTO{" +
            "id=" + getId() +
            ", nomDep='" + getNomDep() + "'" +
            ", designiation='" + getDesigniation() + "'" +
            "}";
    }
}
