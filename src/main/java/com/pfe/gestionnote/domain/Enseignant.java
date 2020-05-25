package com.pfe.gestionnote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Enseignant.
 */
@Entity
@Table(name = "enseignant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Enseignant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "pernom")
    private String pernom;

    @Column(name = "mail")
    private String mail;

    @Column(name = "matricule")
    private Integer matricule;

    @Column(name = "cin")
    private Integer cin;

    @Column(name = "date_embauchement")
    private LocalDate dateEmbauchement;

    @ManyToOne
    @JsonIgnoreProperties(value = "enseignants", allowSetters = true)
    private Departement departement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Enseignant nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPernom() {
        return pernom;
    }

    public Enseignant pernom(String pernom) {
        this.pernom = pernom;
        return this;
    }

    public void setPernom(String pernom) {
        this.pernom = pernom;
    }

    public String getMail() {
        return mail;
    }

    public Enseignant mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getMatricule() {
        return matricule;
    }

    public Enseignant matricule(Integer matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(Integer matricule) {
        this.matricule = matricule;
    }

    public Integer getCin() {
        return cin;
    }

    public Enseignant cin(Integer cin) {
        this.cin = cin;
        return this;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public LocalDate getDateEmbauchement() {
        return dateEmbauchement;
    }

    public Enseignant dateEmbauchement(LocalDate dateEmbauchement) {
        this.dateEmbauchement = dateEmbauchement;
        return this;
    }

    public void setDateEmbauchement(LocalDate dateEmbauchement) {
        this.dateEmbauchement = dateEmbauchement;
    }

    public Departement getDepartement() {
        return departement;
    }

    public Enseignant departement(Departement departement) {
        this.departement = departement;
        return this;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enseignant)) {
            return false;
        }
        return id != null && id.equals(((Enseignant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enseignant{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", pernom='" + getPernom() + "'" +
            ", mail='" + getMail() + "'" +
            ", matricule=" + getMatricule() +
            ", cin=" + getCin() +
            ", dateEmbauchement='" + getDateEmbauchement() + "'" +
            "}";
    }
}
