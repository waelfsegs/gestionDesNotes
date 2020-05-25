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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Resultat} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.ResultatResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resultats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResultatCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter notecc1;

    private DoubleFilter notecc2;

    private DoubleFilter noteexmen;

    private LongFilter matiereId;

    private LongFilter inscriptionId;

    public ResultatCriteria() {
    }

    public ResultatCriteria(ResultatCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.notecc1 = other.notecc1 == null ? null : other.notecc1.copy();
        this.notecc2 = other.notecc2 == null ? null : other.notecc2.copy();
        this.noteexmen = other.noteexmen == null ? null : other.noteexmen.copy();
        this.matiereId = other.matiereId == null ? null : other.matiereId.copy();
        this.inscriptionId = other.inscriptionId == null ? null : other.inscriptionId.copy();
    }

    @Override
    public ResultatCriteria copy() {
        return new ResultatCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getNotecc1() {
        return notecc1;
    }

    public void setNotecc1(DoubleFilter notecc1) {
        this.notecc1 = notecc1;
    }

    public DoubleFilter getNotecc2() {
        return notecc2;
    }

    public void setNotecc2(DoubleFilter notecc2) {
        this.notecc2 = notecc2;
    }

    public DoubleFilter getNoteexmen() {
        return noteexmen;
    }

    public void setNoteexmen(DoubleFilter noteexmen) {
        this.noteexmen = noteexmen;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResultatCriteria that = (ResultatCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(notecc1, that.notecc1) &&
            Objects.equals(notecc2, that.notecc2) &&
            Objects.equals(noteexmen, that.noteexmen) &&
            Objects.equals(matiereId, that.matiereId) &&
            Objects.equals(inscriptionId, that.inscriptionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        notecc1,
        notecc2,
        noteexmen,
        matiereId,
        inscriptionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (notecc1 != null ? "notecc1=" + notecc1 + ", " : "") +
                (notecc2 != null ? "notecc2=" + notecc2 + ", " : "") +
                (noteexmen != null ? "noteexmen=" + noteexmen + ", " : "") +
                (matiereId != null ? "matiereId=" + matiereId + ", " : "") +
                (inscriptionId != null ? "inscriptionId=" + inscriptionId + ", " : "") +
            "}";
    }

}
