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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Spicialitematiere} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.SpicialitematiereResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /spicialitematieres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SpicialitematiereCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelle;

    private LongFilter matiereId;

    private LongFilter specialiteId;

    public SpicialitematiereCriteria() {
    }

    public SpicialitematiereCriteria(SpicialitematiereCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.matiereId = other.matiereId == null ? null : other.matiereId.copy();
        this.specialiteId = other.specialiteId == null ? null : other.specialiteId.copy();
    }

    @Override
    public SpicialitematiereCriteria copy() {
        return new SpicialitematiereCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public LongFilter getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(LongFilter matiereId) {
        this.matiereId = matiereId;
    }

    public LongFilter getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(LongFilter specialiteId) {
        this.specialiteId = specialiteId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SpicialitematiereCriteria that = (SpicialitematiereCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(matiereId, that.matiereId) &&
            Objects.equals(specialiteId, that.specialiteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelle,
        matiereId,
        specialiteId
        );
    }

    @Override
    public String toString() {
        return "SpicialitematiereCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelle != null ? "libelle=" + libelle + ", " : "") +
                (matiereId != null ? "matiereId=" + matiereId + ", " : "") +
                (specialiteId != null ? "specialiteId=" + specialiteId + ", " : "") +
            "}";
    }

}
