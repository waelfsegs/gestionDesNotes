package com.pfe.gestionnote.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Regime} entity.
 */
public class RegimeDTO implements Serializable {
    
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
        if (!(o instanceof RegimeDTO)) {
            return false;
        }

        return id != null && id.equals(((RegimeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegimeDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
