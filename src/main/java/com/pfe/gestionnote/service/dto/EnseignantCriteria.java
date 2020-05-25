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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.pfe.gestionnote.domain.Enseignant} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.EnseignantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enseignants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnseignantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter pernom;

    private StringFilter mail;

    private IntegerFilter matricule;

    private IntegerFilter cin;

    private LocalDateFilter dateEmbauchement;

    private LongFilter departementId;

    public EnseignantCriteria() {
    }

    public EnseignantCriteria(EnseignantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.pernom = other.pernom == null ? null : other.pernom.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.matricule = other.matricule == null ? null : other.matricule.copy();
        this.cin = other.cin == null ? null : other.cin.copy();
        this.dateEmbauchement = other.dateEmbauchement == null ? null : other.dateEmbauchement.copy();
        this.departementId = other.departementId == null ? null : other.departementId.copy();
    }

    @Override
    public EnseignantCriteria copy() {
        return new EnseignantCriteria(this);
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

    public StringFilter getPernom() {
        return pernom;
    }

    public void setPernom(StringFilter pernom) {
        this.pernom = pernom;
    }

    public StringFilter getMail() {
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
    }

    public IntegerFilter getMatricule() {
        return matricule;
    }

    public void setMatricule(IntegerFilter matricule) {
        this.matricule = matricule;
    }

    public IntegerFilter getCin() {
        return cin;
    }

    public void setCin(IntegerFilter cin) {
        this.cin = cin;
    }

    public LocalDateFilter getDateEmbauchement() {
        return dateEmbauchement;
    }

    public void setDateEmbauchement(LocalDateFilter dateEmbauchement) {
        this.dateEmbauchement = dateEmbauchement;
    }

    public LongFilter getDepartementId() {
        return departementId;
    }

    public void setDepartementId(LongFilter departementId) {
        this.departementId = departementId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnseignantCriteria that = (EnseignantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(pernom, that.pernom) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(matricule, that.matricule) &&
            Objects.equals(cin, that.cin) &&
            Objects.equals(dateEmbauchement, that.dateEmbauchement) &&
            Objects.equals(departementId, that.departementId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        pernom,
        mail,
        matricule,
        cin,
        dateEmbauchement,
        departementId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnseignantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (pernom != null ? "pernom=" + pernom + ", " : "") +
                (mail != null ? "mail=" + mail + ", " : "") +
                (matricule != null ? "matricule=" + matricule + ", " : "") +
                (cin != null ? "cin=" + cin + ", " : "") +
                (dateEmbauchement != null ? "dateEmbauchement=" + dateEmbauchement + ", " : "") +
                (departementId != null ? "departementId=" + departementId + ", " : "") +
            "}";
    }

}
