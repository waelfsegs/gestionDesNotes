package com.pfe.gestionnote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Resultat.
 */
@Entity
@Table(name = "resultat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resultat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notecc_1")
    private Double notecc1;

    @Column(name = "notecc_2")
    private Double notecc2;

    @Column(name = "noteexmen")
    private Double noteexmen;

    @ManyToOne
    @JsonIgnoreProperties(value = "resultats", allowSetters = true)
    private Matiere matiere;

    @ManyToOne
    @JsonIgnoreProperties(value = "resultats", allowSetters = true)
    private Inscription inscription;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNotecc1() {
        return notecc1;
    }

    public Resultat notecc1(Double notecc1) {
        this.notecc1 = notecc1;
        return this;
    }

    public void setNotecc1(Double notecc1) {
        this.notecc1 = notecc1;
    }

    public Double getNotecc2() {
        return notecc2;
    }

    public Resultat notecc2(Double notecc2) {
        this.notecc2 = notecc2;
        return this;
    }

    public void setNotecc2(Double notecc2) {
        this.notecc2 = notecc2;
    }

    public Double getNoteexmen() {
        return noteexmen;
    }

    public Resultat noteexmen(Double noteexmen) {
        this.noteexmen = noteexmen;
        return this;
    }

    public void setNoteexmen(Double noteexmen) {
        this.noteexmen = noteexmen;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public Resultat matiere(Matiere matiere) {
        this.matiere = matiere;
        return this;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public Resultat inscription(Inscription inscription) {
        this.inscription = inscription;
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resultat)) {
            return false;
        }
        return id != null && id.equals(((Resultat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resultat{" +
            "id=" + getId() +
            ", notecc1=" + getNotecc1() +
            ", notecc2=" + getNotecc2() +
            ", noteexmen=" + getNoteexmen() +
            "}";
    }
}
