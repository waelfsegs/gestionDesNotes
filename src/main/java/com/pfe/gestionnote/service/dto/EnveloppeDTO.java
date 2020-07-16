package com.pfe.gestionnote.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Enveloppe} entity.
 */
public class EnveloppeDTO implements Serializable {

    private Long id;

    private String nameenv;


    private Long maiereId;
    private String maiere;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameenv() {
        return nameenv;
    }

    public void setNameenv(String nameenv) {
        this.nameenv = nameenv;
    }

    public Long getMaiereId() {
        return maiereId;
    }

    public void setMaiereId(Long matiereId) {
        this.maiereId = matiereId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnveloppeDTO enveloppeDTO = (EnveloppeDTO) o;
        if (enveloppeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enveloppeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnveloppeDTO{" +
            "id=" + getId() +
            ", nameenv='" + getNameenv() + "'" +
            ", maiereId=" + getMaiereId() +
            "}";
    }

    public String getMaiere() {
        return maiere;
    }

    public void setMaiere(String maiere) {
        this.maiere = maiere;
    }
}
