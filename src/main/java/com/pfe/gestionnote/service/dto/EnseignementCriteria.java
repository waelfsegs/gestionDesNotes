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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Enseignement} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.EnseignementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enseignements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnseignementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter matiereId;

    private LongFilter enseignantId;

    private LongFilter groupeId;

    private LongFilter typeEnseignementId;

    public EnseignementCriteria() {
    }

    public EnseignementCriteria(EnseignementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.matiereId = other.matiereId == null ? null : other.matiereId.copy();
        this.enseignantId = other.enseignantId == null ? null : other.enseignantId.copy();
        this.groupeId = other.groupeId == null ? null : other.groupeId.copy();
        this.typeEnseignementId = other.typeEnseignementId == null ? null : other.typeEnseignementId.copy();
    }

    @Override
    public EnseignementCriteria copy() {
        return new EnseignementCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(LongFilter matiereId) {
        this.matiereId = matiereId;
    }

    public LongFilter getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(LongFilter enseignantId) {
        this.enseignantId = enseignantId;
    }

    public LongFilter getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(LongFilter groupeId) {
        this.groupeId = groupeId;
    }

    public LongFilter getTypeEnseignementId() {
        return typeEnseignementId;
    }

    public void setTypeEnseignementId(LongFilter typeEnseignementId) {
        this.typeEnseignementId = typeEnseignementId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnseignementCriteria that = (EnseignementCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(matiereId, that.matiereId) &&
            Objects.equals(enseignantId, that.enseignantId) &&
            Objects.equals(groupeId, that.groupeId) &&
            Objects.equals(typeEnseignementId, that.typeEnseignementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        matiereId,
        enseignantId,
        groupeId,
        typeEnseignementId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnseignementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (matiereId != null ? "matiereId=" + matiereId + ", " : "") +
                (enseignantId != null ? "enseignantId=" + enseignantId + ", " : "") +
                (groupeId != null ? "groupeId=" + groupeId + ", " : "") +
                (typeEnseignementId != null ? "typeEnseignementId=" + typeEnseignementId + ", " : "") +
            "}";
    }

}
