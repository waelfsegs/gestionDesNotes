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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.pfe.gestionnote.domain.Inscription} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.InscriptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inscriptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InscriptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private LocalDateFilter annee;

    private LongFilter etudiantId;

    private LongFilter classeId;

    private LongFilter groupeId;

    private LongFilter semstreId;

    public InscriptionCriteria() {
    }

    public InscriptionCriteria(InscriptionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.annee = other.annee == null ? null : other.annee.copy();
        this.etudiantId = other.etudiantId == null ? null : other.etudiantId.copy();
        this.classeId = other.classeId == null ? null : other.classeId.copy();
        this.groupeId = other.groupeId == null ? null : other.groupeId.copy();
        this.semstreId = other.semstreId == null ? null : other.semstreId.copy();
    }

    @Override
    public InscriptionCriteria copy() {
        return new InscriptionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public LocalDateFilter getAnnee() {
        return annee;
    }

    public void setAnnee(LocalDateFilter annee) {
        this.annee = annee;
    }

    public LongFilter getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(LongFilter etudiantId) {
        this.etudiantId = etudiantId;
    }

    public LongFilter getClasseId() {
        return classeId;
    }

    public void setClasseId(LongFilter classeId) {
        this.classeId = classeId;
    }

    public LongFilter getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(LongFilter groupeId) {
        this.groupeId = groupeId;
    }

    public LongFilter getSemstreId() {
        return semstreId;
    }

    public void setSemstreId(LongFilter semstreId) {
        this.semstreId = semstreId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InscriptionCriteria that = (InscriptionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(annee, that.annee) &&
            Objects.equals(etudiantId, that.etudiantId) &&
            Objects.equals(classeId, that.classeId) &&
            Objects.equals(groupeId, that.groupeId) &&
            Objects.equals(semstreId, that.semstreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        annee,
        etudiantId,
        classeId,
        groupeId,
        semstreId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InscriptionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (annee != null ? "annee=" + annee + ", " : "") +
                (etudiantId != null ? "etudiantId=" + etudiantId + ", " : "") +
                (classeId != null ? "classeId=" + classeId + ", " : "") +
                (groupeId != null ? "groupeId=" + groupeId + ", " : "") +
                (semstreId != null ? "semstreId=" + semstreId + ", " : "") +
            "}";
    }

}
