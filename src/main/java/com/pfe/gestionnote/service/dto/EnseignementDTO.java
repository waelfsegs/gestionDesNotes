package com.pfe.gestionnote.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Enseignement} entity.
 */
public class EnseignementDTO implements Serializable {
    
    private Long id;


    private Long matiereId;

    private Long enseignantId;

    private Long groupeId;

    private Long typeEnseignementId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(Long matiereId) {
        this.matiereId = matiereId;
    }

    public Long getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(Long enseignantId) {
        this.enseignantId = enseignantId;
    }

    public Long getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Long groupeId) {
        this.groupeId = groupeId;
    }

    public Long getTypeEnseignementId() {
        return typeEnseignementId;
    }

    public void setTypeEnseignementId(Long typeEnseignementId) {
        this.typeEnseignementId = typeEnseignementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnseignementDTO)) {
            return false;
        }

        return id != null && id.equals(((EnseignementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnseignementDTO{" +
            "id=" + getId() +
            ", matiereId=" + getMatiereId() +
            ", enseignantId=" + getEnseignantId() +
            ", groupeId=" + getGroupeId() +
            ", typeEnseignementId=" + getTypeEnseignementId() +
            "}";
    }
}
