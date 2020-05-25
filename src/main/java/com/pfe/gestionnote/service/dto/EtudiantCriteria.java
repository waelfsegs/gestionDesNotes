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
 * Criteria class for the {@link com.pfe.gestionnote.domain.Etudiant} entity. This class is used
 * in {@link com.pfe.gestionnote.web.rest.EtudiantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /etudiants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EtudiantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter cin;

    private StringFilter nom;

    private StringFilter matricule;

    private StringFilter prenom;

    private IntegerFilter tel;

    private LocalDateFilter dateNais;

    public EtudiantCriteria() {
    }

    public EtudiantCriteria(EtudiantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cin = other.cin == null ? null : other.cin.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.matricule = other.matricule == null ? null : other.matricule.copy();
        this.prenom = other.prenom == null ? null : other.prenom.copy();
        this.tel = other.tel == null ? null : other.tel.copy();
        this.dateNais = other.dateNais == null ? null : other.dateNais.copy();
    }

    @Override
    public EtudiantCriteria copy() {
        return new EtudiantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getCin() {
        return cin;
    }

    public void setCin(IntegerFilter cin) {
        this.cin = cin;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getMatricule() {
        return matricule;
    }

    public void setMatricule(StringFilter matricule) {
        this.matricule = matricule;
    }

    public StringFilter getPrenom() {
        return prenom;
    }

    public void setPrenom(StringFilter prenom) {
        this.prenom = prenom;
    }

    public IntegerFilter getTel() {
        return tel;
    }

    public void setTel(IntegerFilter tel) {
        this.tel = tel;
    }

    public LocalDateFilter getDateNais() {
        return dateNais;
    }

    public void setDateNais(LocalDateFilter dateNais) {
        this.dateNais = dateNais;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EtudiantCriteria that = (EtudiantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cin, that.cin) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(matricule, that.matricule) &&
            Objects.equals(prenom, that.prenom) &&
            Objects.equals(tel, that.tel) &&
            Objects.equals(dateNais, that.dateNais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cin,
        nom,
        matricule,
        prenom,
        tel,
        dateNais
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtudiantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cin != null ? "cin=" + cin + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (matricule != null ? "matricule=" + matricule + ", " : "") +
                (prenom != null ? "prenom=" + prenom + ", " : "") +
                (tel != null ? "tel=" + tel + ", " : "") +
                (dateNais != null ? "dateNais=" + dateNais + ", " : "") +
            "}";
    }

}
