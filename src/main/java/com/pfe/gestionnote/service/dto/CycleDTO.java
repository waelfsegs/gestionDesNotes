package com.pfe.gestionnote.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Cycle} entity.
 */
public class CycleDTO implements Serializable {
    
    private Long id;

    private String nomcycle;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomcycle() {
        return nomcycle;
    }

    public void setNomcycle(String nomcycle) {
        this.nomcycle = nomcycle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CycleDTO)) {
            return false;
        }

        return id != null && id.equals(((CycleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CycleDTO{" +
            "id=" + getId() +
            ", nomcycle='" + getNomcycle() + "'" +
            "}";
    }
}
