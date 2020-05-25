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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Cycle} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.CycleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cycles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CycleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomcycle;

    public CycleCriteria() {
    }

    public CycleCriteria(CycleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomcycle = other.nomcycle == null ? null : other.nomcycle.copy();
    }

    @Override
    public CycleCriteria copy() {
        return new CycleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomcycle() {
        return nomcycle;
    }

    public void setNomcycle(StringFilter nomcycle) {
        this.nomcycle = nomcycle;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CycleCriteria that = (CycleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomcycle, that.nomcycle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomcycle
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CycleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomcycle != null ? "nomcycle=" + nomcycle + ", " : "") +
            "}";
    }

}
