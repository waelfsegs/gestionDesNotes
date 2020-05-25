package com.pfe.gestionnote.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Resultat} entity.
 */
public class ResultatDTO implements Serializable {
    
    private Long id;

    private Double notecc1;

    private Double notecc2;

    private Double noteexmen;


    private Long matiereId;

    private Long inscriptionId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNotecc1() {
        return notecc1;
    }

    public void setNotecc1(Double notecc1) {
        this.notecc1 = notecc1;
    }

    public Double getNotecc2() {
        return notecc2;
    }

    public void setNotecc2(Double notecc2) {
        this.notecc2 = notecc2;
    }

    public Double getNoteexmen() {
        return noteexmen;
    }

    public void setNoteexmen(Double noteexmen) {
        this.noteexmen = noteexmen;
    }

    public Long getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(Long matiereId) {
        this.matiereId = matiereId;
    }

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultatDTO)) {
            return false;
        }

        return id != null && id.equals(((ResultatDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultatDTO{" +
            "id=" + getId() +
            ", notecc1=" + getNotecc1() +
            ", notecc2=" + getNotecc2() +
            ", noteexmen=" + getNoteexmen() +
            ", matiereId=" + getMatiereId() +
            ", inscriptionId=" + getInscriptionId() +
            "}";
    }
}
