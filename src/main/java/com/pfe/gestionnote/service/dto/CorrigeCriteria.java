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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Corrige} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.CorrigeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /corriges?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CorrigeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter enseignantId;

    private LongFilter enveloppeId;

    public CorrigeCriteria() {
    }

    public CorrigeCriteria(CorrigeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.enseignantId = other.enseignantId == null ? null : other.enseignantId.copy();
        this.enveloppeId = other.enveloppeId == null ? null : other.enveloppeId.copy();
    }

    @Override
    public CorrigeCriteria copy() {
        return new CorrigeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(LongFilter enseignantId) {
        this.enseignantId = enseignantId;
    }

    public LongFilter getEnveloppeId() {
        return enveloppeId;
    }

    public void setEnveloppeId(LongFilter enveloppeId) {
        this.enveloppeId = enveloppeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CorrigeCriteria that = (CorrigeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(enseignantId, that.enseignantId) &&
            Objects.equals(enveloppeId, that.enveloppeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        enseignantId,
        enveloppeId
        );
    }

    @Override
    public String toString() {
        return "CorrigeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (enseignantId != null ? "enseignantId=" + enseignantId + ", " : "") +
                (enveloppeId != null ? "enveloppeId=" + enveloppeId + ", " : "") +
            "}";
    }

}
