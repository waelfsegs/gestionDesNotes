package com.pfe.gestionnote.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Corrige} entity.
 */
public class CorrigeDTO implements Serializable {

    private Long id;
    private Long enseignantId;
    private Long enveloppeId;
    private String enseignantNom;
    private String enseignantPrenom;
    private String enveloppenameenv;
    private Integer nbrecopieCorrige;

    private Integer nbreCopieinenvloppe;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(Long enseignantId) {
        this.enseignantId = enseignantId;
    }

    public Long getEnveloppeId() {
        return enveloppeId;
    }

    public void setEnveloppeId(Long enveloppeId) {
        this.enveloppeId = enveloppeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CorrigeDTO corrigeDTO = (CorrigeDTO) o;
        if (corrigeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), corrigeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CorrigeDTO{" + "id=" + getId() + ", enseignantId=" + getEnseignantId() + ", enveloppeId="
                + getEnveloppeId()+", nbrecopieCorrige=" + getNbrecopieCorrige() +
                ", nbreCopieinenvloppe=" + getNbreCopieinenvloppe() + "}";
    }

    public String getEnseignantNom() {
        return enseignantNom;
    }

    public void setEnseignantNom(String enseignantNom) {
        this.enseignantNom = enseignantNom;
    }

    public String getEnseignantPrenom() {
        return enseignantPrenom;
    }

    public void setEnseignantPrenom(String enseignantPrenom) {
        this.enseignantPrenom = enseignantPrenom;
    }

    public String getEnveloppenameenv() {
        return enveloppenameenv;
    }

    public void setEnveloppenameenv(String enveloppenameenv) {
        this.enveloppenameenv = enveloppenameenv;
    }


   public Integer getNbrecopieCorrige() {
    return nbrecopieCorrige;
}

public void setNbrecopieCorrige(Integer nbrecopieCorrige) {
    this.nbrecopieCorrige = nbrecopieCorrige;
}

public Integer getNbreCopieinenvloppe() {
    return nbreCopieinenvloppe;
}

public void setNbreCopieinenvloppe(Integer nbreCopieinenvloppe) {
    this.nbreCopieinenvloppe = nbreCopieinenvloppe;
}

}
