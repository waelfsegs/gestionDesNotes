package com.pfe.gestionnote.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.UniteEnseignement} entity.
 */
public class UniteEnseignementDTO implements Serializable {
    
    private Long id;

    private String nomUE;

    private String desgnationUE;

    private Integer coefficientUE;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomUE() {
        return nomUE;
    }

    public void setNomUE(String nomUE) {
        this.nomUE = nomUE;
    }

    public String getDesgnationUE() {
        return desgnationUE;
    }

    public void setDesgnationUE(String desgnationUE) {
        this.desgnationUE = desgnationUE;
    }

    public Integer getCoefficientUE() {
        return coefficientUE;
    }

    public void setCoefficientUE(Integer coefficientUE) {
        this.coefficientUE = coefficientUE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UniteEnseignementDTO)) {
            return false;
        }

        return id != null && id.equals(((UniteEnseignementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UniteEnseignementDTO{" +
            "id=" + getId() +
            ", nomUE='" + getNomUE() + "'" +
            ", desgnationUE='" + getDesgnationUE() + "'" +
            ", coefficientUE=" + getCoefficientUE() +
            "}";
    }
}
