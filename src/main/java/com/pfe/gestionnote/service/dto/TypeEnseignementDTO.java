package com.pfe.gestionnote.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.TypeEnseignement} entity.
 */
public class TypeEnseignementDTO implements Serializable {
    
    private Long id;

    private String type;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeEnseignementDTO)) {
            return false;
        }

        return id != null && id.equals(((TypeEnseignementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeEnseignementDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
