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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Matiere} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.MatiereResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /matieres?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MatiereCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter coefficientMatiere;

    private DoubleFilter coefficientTp;

    private DoubleFilter coefficientDc;

    private DoubleFilter coefficientExem;

    private StringFilter designation;

    private StringFilter nom;

    private LongFilter regimeId;

    public MatiereCriteria() {
    }

    public MatiereCriteria(MatiereCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.coefficientMatiere = other.coefficientMatiere == null ? null : other.coefficientMatiere.copy();
        this.coefficientTp = other.coefficientTp == null ? null : other.coefficientTp.copy();
        this.coefficientDc = other.coefficientDc == null ? null : other.coefficientDc.copy();
        this.coefficientExem = other.coefficientExem == null ? null : other.coefficientExem.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.regimeId = other.regimeId == null ? null : other.regimeId.copy();
    }

    @Override
    public MatiereCriteria copy() {
        return new MatiereCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getCoefficientMatiere() {
        return coefficientMatiere;
    }

    public void setCoefficientMatiere(DoubleFilter coefficientMatiere) {
        this.coefficientMatiere = coefficientMatiere;
    }

    public DoubleFilter getCoefficientTp() {
        return coefficientTp;
    }

    public void setCoefficientTp(DoubleFilter coefficientTp) {
        this.coefficientTp = coefficientTp;
    }

    public DoubleFilter getCoefficientDc() {
        return coefficientDc;
    }

    public void setCoefficientDc(DoubleFilter coefficientDc) {
        this.coefficientDc = coefficientDc;
    }

    public DoubleFilter getCoefficientExem() {
        return coefficientExem;
    }

    public void setCoefficientExem(DoubleFilter coefficientExem) {
        this.coefficientExem = coefficientExem;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public LongFilter getRegimeId() {
        return regimeId;
    }

    public void setRegimeId(LongFilter regimeId) {
        this.regimeId = regimeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MatiereCriteria that = (MatiereCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(coefficientMatiere, that.coefficientMatiere) &&
            Objects.equals(coefficientTp, that.coefficientTp) &&
            Objects.equals(coefficientDc, that.coefficientDc) &&
            Objects.equals(coefficientExem, that.coefficientExem) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(regimeId, that.regimeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        coefficientMatiere,
        coefficientTp,
        coefficientDc,
        coefficientExem,
        designation,
        nom,
        regimeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatiereCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (coefficientMatiere != null ? "coefficientMatiere=" + coefficientMatiere + ", " : "") +
                (coefficientTp != null ? "coefficientTp=" + coefficientTp + ", " : "") +
                (coefficientDc != null ? "coefficientDc=" + coefficientDc + ", " : "") +
                (coefficientExem != null ? "coefficientExem=" + coefficientExem + ", " : "") +
                (designation != null ? "designation=" + designation + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (regimeId != null ? "regimeId=" + regimeId + ", " : "") +
            "}";
    }

}
