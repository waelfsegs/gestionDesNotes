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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Departement} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.DepartementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /departements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepartementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomDep;

    private StringFilter designiation;

    public DepartementCriteria() {
    }

    public DepartementCriteria(DepartementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomDep = other.nomDep == null ? null : other.nomDep.copy();
        this.designiation = other.designiation == null ? null : other.designiation.copy();
    }

    @Override
    public DepartementCriteria copy() {
        return new DepartementCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomDep() {
        return nomDep;
    }

    public void setNomDep(StringFilter nomDep) {
        this.nomDep = nomDep;
    }

    public StringFilter getDesigniation() {
        return designiation;
    }

    public void setDesigniation(StringFilter designiation) {
        this.designiation = designiation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DepartementCriteria that = (DepartementCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomDep, that.nomDep) &&
            Objects.equals(designiation, that.designiation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomDep,
        designiation
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomDep != null ? "nomDep=" + nomDep + ", " : "") +
                (designiation != null ? "designiation=" + designiation + ", " : "") +
            "}";
    }

}
