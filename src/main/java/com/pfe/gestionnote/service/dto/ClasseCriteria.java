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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Classe} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.ClasseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /classes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClasseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private LongFilter specialiteId;

    private LongFilter niveauId;

    public ClasseCriteria() {
    }

    public ClasseCriteria(ClasseCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.specialiteId = other.specialiteId == null ? null : other.specialiteId.copy();
        this.niveauId = other.niveauId == null ? null : other.niveauId.copy();
    }

    @Override
    public ClasseCriteria copy() {
        return new ClasseCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public LongFilter getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(LongFilter specialiteId) {
        this.specialiteId = specialiteId;
    }

    public LongFilter getNiveauId() {
        return niveauId;
    }

    public void setNiveauId(LongFilter niveauId) {
        this.niveauId = niveauId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClasseCriteria that = (ClasseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(specialiteId, that.specialiteId) &&
            Objects.equals(niveauId, that.niveauId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        specialiteId,
        niveauId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClasseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (specialiteId != null ? "specialiteId=" + specialiteId + ", " : "") +
                (niveauId != null ? "niveauId=" + niveauId + ", " : "") +
            "}";
    }

}
