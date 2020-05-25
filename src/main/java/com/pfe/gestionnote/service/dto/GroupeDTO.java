package com.pfe.gestionnote.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Groupe} entity.
 */
public class GroupeDTO implements Serializable {
    
    private Long id;

    private String nomgroup;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomgroup() {
        return nomgroup;
    }

    public void setNomgroup(String nomgroup) {
        this.nomgroup = nomgroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupeDTO)) {
            return false;
        }

        return id != null && id.equals(((GroupeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupeDTO{" +
            "id=" + getId() +
            ", nomgroup='" + getNomgroup() + "'" +
            "}";
    }
}
