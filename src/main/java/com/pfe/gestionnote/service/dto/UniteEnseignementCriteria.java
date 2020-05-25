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
 * Criteria class for the {@link com.pfe.gestionnote.domain.UniteEnseignement} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.UniteEnseignementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /unite-enseignements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UniteEnseignementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nomUE;

    private StringFilter desgnationUE;

    private IntegerFilter coefficientUE;

    public UniteEnseignementCriteria() {
    }

    public UniteEnseignementCriteria(UniteEnseignementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nomUE = other.nomUE == null ? null : other.nomUE.copy();
        this.desgnationUE = other.desgnationUE == null ? null : other.desgnationUE.copy();
        this.coefficientUE = other.coefficientUE == null ? null : other.coefficientUE.copy();
    }

    @Override
    public UniteEnseignementCriteria copy() {
        return new UniteEnseignementCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNomUE() {
        return nomUE;
    }

    public void setNomUE(StringFilter nomUE) {
        this.nomUE = nomUE;
    }

    public StringFilter getDesgnationUE() {
        return desgnationUE;
    }

    public void setDesgnationUE(StringFilter desgnationUE) {
        this.desgnationUE = desgnationUE;
    }

    public IntegerFilter getCoefficientUE() {
        return coefficientUE;
    }

    public void setCoefficientUE(IntegerFilter coefficientUE) {
        this.coefficientUE = coefficientUE;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UniteEnseignementCriteria that = (UniteEnseignementCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nomUE, that.nomUE) &&
            Objects.equals(desgnationUE, that.desgnationUE) &&
            Objects.equals(coefficientUE, that.coefficientUE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nomUE,
        desgnationUE,
        coefficientUE
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UniteEnseignementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nomUE != null ? "nomUE=" + nomUE + ", " : "") +
                (desgnationUE != null ? "desgnationUE=" + desgnationUE + ", " : "") +
                (coefficientUE != null ? "coefficientUE=" + coefficientUE + ", " : "") +
            "}";
    }

}
