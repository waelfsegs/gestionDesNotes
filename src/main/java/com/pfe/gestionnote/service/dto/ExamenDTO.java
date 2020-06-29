package com.pfe.gestionnote.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Examen} entity.
 */
public class ExamenDTO implements Serializable {

    private Long id;

    private String matricule;

    private String session;

    private Integer numcomp;


    private Long matiereId;

    private Long inscriptionId;

    private Long semstreId;

    private Long niveauId;

    private Long specialiteId;

    private Long enveloppeId;

    private Long cycleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Integer getNumcomp() {
        return numcomp;
    }

    public void setNumcomp(Integer numcomp) {
        this.numcomp = numcomp;
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

    public Long getSemstreId() {
        return semstreId;
    }

    public void setSemstreId(Long semstreId) {
        this.semstreId = semstreId;
    }

    public Long getNiveauId() {
        return niveauId;
    }

    public void setNiveauId(Long niveauId) {
        this.niveauId = niveauId;
    }

    public Long getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(Long specialiteId) {
        this.specialiteId = specialiteId;
    }

    public Long getEnveloppeId() {
        return enveloppeId;
    }

    public void setEnveloppeId(Long enveloppeId) {
        this.enveloppeId = enveloppeId;
    }

    public Long getCycleId() {
        return cycleId;
    }

    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExamenDTO examenDTO = (ExamenDTO) o;
        if (examenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamenDTO{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", session='" + getSession() + "'" +
            ", numcomp=" + getNumcomp() +
            ", matiereId=" + getMatiereId() +
            ", inscriptionId=" + getInscriptionId() +
            ", semstreId=" + getSemstreId() +
            ", niveauId=" + getNiveauId() +
            ", specialiteId=" + getSpecialiteId() +
            ", enveloppeId=" + getEnveloppeId() +
            ", cycleId=" + getCycleId() +
            "}";
    }
}
