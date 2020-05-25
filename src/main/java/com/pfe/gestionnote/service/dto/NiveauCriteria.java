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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Niveau} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.NiveauResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /niveaus?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NiveauCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter niveau;

    private LongFilter cycleId;

    public NiveauCriteria() {
    }

    public NiveauCriteria(NiveauCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.niveau = other.niveau == null ? null : other.niveau.copy();
        this.cycleId = other.cycleId == null ? null : other.cycleId.copy();
    }

    @Override
    public NiveauCriteria copy() {
        return new NiveauCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNiveau() {
        return niveau;
    }

    public void setNiveau(StringFilter niveau) {
        this.niveau = niveau;
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
        final NiveauCriteria that = (NiveauCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(niveau, that.niveau) &&
            Objects.equals(cycleId, that.cycleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        niveau,
        cycleId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NiveauCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (niveau != null ? "niveau=" + niveau + ", " : "") +
                (cycleId != null ? "cycleId=" + cycleId + ", " : "") +
            "}";
    }

}
