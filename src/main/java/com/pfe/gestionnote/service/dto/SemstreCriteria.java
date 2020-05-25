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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Semstre} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.SemstreResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /semstres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SemstreCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter annee;

    private IntegerFilter numSemstre;

    public SemstreCriteria() {
    }

    public SemstreCriteria(SemstreCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.annee = other.annee == null ? null : other.annee.copy();
        this.numSemstre = other.numSemstre == null ? null : other.numSemstre.copy();
    }

    @Override
    public SemstreCriteria copy() {
        return new SemstreCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getAnnee() {
        return annee;
    }

    public void setAnnee(IntegerFilter annee) {
        this.annee = annee;
    }

    public IntegerFilter getNumSemstre() {
        return numSemstre;
    }

    public void setNumSemstre(IntegerFilter numSemstre) {
        this.numSemstre = numSemstre;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SemstreCriteria that = (SemstreCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(annee, that.annee) &&
            Objects.equals(numSemstre, that.numSemstre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        annee,
        numSemstre
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SemstreCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (annee != null ? "annee=" + annee + ", " : "") +
                (numSemstre != null ? "numSemstre=" + numSemstre + ", " : "") +
            "}";
    }

}
