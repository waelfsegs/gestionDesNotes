package com.pfe.gestionnote.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Enseignement} entity.
 */
public class EnseignementDTO implements Serializable {

    private Long id;


    private Long matiereId;

    private Long enseignantId;

    private Long groupeId;

    private Long typeEnseignementId;

    private Long classeId;
    private String nommatiere;

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

    public Long getClasseId() {
        return classeId;
    }

    public void setClasseId(Long classeId) {
        this.classeId = classeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnseignementDTO enseignementDTO = (EnseignementDTO) o;
        if (enseignementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enseignementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnseignementDTO [classeId=" + classeId + ", enseignantId=" + enseignantId + ", groupeId=" + groupeId
                + ", id=" + id + ", matiereId=" + matiereId + ", nommatiere=" + nommatiere + ", typeEnseignementId="
                + typeEnseignementId + "]";
    }

   
}
