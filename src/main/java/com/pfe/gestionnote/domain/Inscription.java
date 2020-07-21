package com.pfe.gestionnote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Inscription.
 */
@Entity
@Table(name = "inscription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "annee")
    private LocalDate annee;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Etudiant etudiant;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Classe classe;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Groupe groupe;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Semstre semstre;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Cycle cycle;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Niveau niveau;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Specialite specialite;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Inscription date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getAnnee() {
        return annee;
    }

    public Inscription annee(LocalDate annee) {
        this.annee = annee;
        return this;
    }

    public void setAnnee(LocalDate annee) {
        this.annee = annee;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public Inscription etudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        return this;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Classe getClasse() {
        return classe;
    }

    public Inscription classe(Classe classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public Inscription groupe(Groupe groupe) {
        this.groupe = groupe;
        return this;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Semstre getSemstre() {
        return semstre;
    }

    public Inscription semstre(Semstre semstre) {
        this.semstre = semstre;
        return this;
    }

    public void setSemstre(Semstre semstre) {
        this.semstre = semstre;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public Inscription cycle(Cycle cycle) {
        this.cycle = cycle;
        return this;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public Inscription niveau(Niveau niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public Inscription specialite(Specialite specialite) {
        this.specialite = specialite;
        return this;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inscription)) {
            return false;
        }
        return id != null && id.equals(((Inscription) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Inscription{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", annee='" + getAnnee() + "'" +
            "}";
    }
}
