package com.pfe.gestionnote.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.pfe.gestionnote.domain.Examen} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.ExamenResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /examen?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ExamenCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter matricule;

    private StringFilter session;

    private IntegerFilter numcomp;

    private LongFilter matiereId;

    private LongFilter inscriptionId;

    private LongFilter semstreId;

    private LongFilter niveauId;

    private LongFilter specialiteId;

    private LongFilter enveloppeId;

    private LongFilter cycleId;

    public ExamenCriteria() {
    }

    public ExamenCriteria(ExamenCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.matricule = other.matricule == null ? null : other.matricule.copy();
        this.session = other.session == null ? null : other.session.copy();
        this.numcomp = other.numcomp == null ? null : other.numcomp.copy();
        this.matiereId = other.matiereId == null ? null : other.matiereId.copy();
        this.inscriptionId = other.inscriptionId == null ? null : other.inscriptionId.copy();
        this.semstreId = other.semstreId == null ? null : other.semstreId.copy();
        this.niveauId = other.niveauId == null ? null : other.niveauId.copy();
        this.specialiteId = other.specialiteId == null ? null : other.specialiteId.copy();
        this.enveloppeId = other.enveloppeId == null ? null : other.enveloppeId.copy();
        this.cycleId = other.cycleId == null ? null : other.cycleId.copy();
    }

    @Override
    public ExamenCriteria copy() {
        return new ExamenCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMatricule() {
        return matricule;
    }

    public void setMatricule(StringFilter matricule) {
        this.matricule = matricule;
    }

    public StringFilter getSession() {
        return session;
    }

    public void setSession(StringFilter session) {
        this.session = session;
    }

    public IntegerFilter getNumcomp() {
        return numcomp;
    }

    public void setNumcomp(IntegerFilter numcomp) {
        this.numcomp = numcomp;
    }

    public LongFilter getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(LongFilter matiereId) {
        this.matiereId = matiereId;
    }

    public LongFilter getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(LongFilter inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public LongFilter getSemstreId() {
        return semstreId;
    }

    public void setSemstreId(LongFilter semstreId) {
        this.semstreId = semstreId;
    }

    public LongFilter getNiveauId() {
        return niveauId;
    }

    public void setNiveauId(LongFilter niveauId) {
        this.niveauId = niveauId;
    }

    public LongFilter getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(LongFilter specialiteId) {
        this.specialiteId = specialiteId;
    }

    public LongFilter getEnveloppeId() {
        return enveloppeId;
    }

    public void setEnveloppeId(LongFilter enveloppeId) {
        this.enveloppeId = enveloppeId;
    }

    public LongFilter getCycleId() {
        return cycleId;
    }

    public void setCycleId(LongFilter cycleId) {
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
        final ExamenCriteria that = (ExamenCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(matricule, that.matricule) &&
            Objects.equals(session, that.session) &&
            Objects.equals(numcomp, that.numcomp) &&
            Objects.equals(matiereId, that.matiereId) &&
            Objects.equals(inscriptionId, that.inscriptionId) &&
            Objects.equals(semstreId, that.semstreId) &&
            Objects.equals(niveauId, that.niveauId) &&
            Objects.equals(specialiteId, that.specialiteId) &&
            Objects.equals(enveloppeId, that.enveloppeId) &&
            Objects.equals(cycleId, that.cycleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        matricule,
        session,
        numcomp,
        matiereId,
        inscriptionId,
        semstreId,
        niveauId,
        specialiteId,
        enveloppeId,
        cycleId
        );
    }

    @Override
    public String toString() {
        return "ExamenCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (matricule != null ? "matricule=" + matricule + ", " : "") +
                (session != null ? "session=" + session + ", " : "") +
                (numcomp != null ? "numcomp=" + numcomp + ", " : "") +
                (matiereId != null ? "matiereId=" + matiereId + ", " : "") +
                (inscriptionId != null ? "inscriptionId=" + inscriptionId + ", " : "") +
                (semstreId != null ? "semstreId=" + semstreId + ", " : "") +
                (niveauId != null ? "niveauId=" + niveauId + ", " : "") +
                (specialiteId != null ? "specialiteId=" + specialiteId + ", " : "") +
                (enveloppeId != null ? "enveloppeId=" + enveloppeId + ", " : "") +
                (cycleId != null ? "cycleId=" + cycleId + ", " : "") +
            "}";
    }

}
