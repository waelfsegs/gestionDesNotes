package com.pfe.gestionnote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Matiere.
 */
@Entity
@Table(name = "matiere")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Matiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coefficient_matiere")
    private Double coefficientMatiere;

    @Column(name = "coefficient_tp")
    private Double coefficientTp;

    @Column(name = "coefficient_dc")
    private Double coefficientDc;

    @Column(name = "coefficient_exem")
    private Double coefficientExem;

    @Column(name = "designation")
    private String designation;

    @Column(name = "nom")
    private String nom;

    @ManyToOne
    @JsonIgnoreProperties(value = "matieres", allowSetters = true)
    private Regime regime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCoefficientMatiere() {
        return coefficientMatiere;
    }

    public Matiere coefficientMatiere(Double coefficientMatiere) {
        this.coefficientMatiere = coefficientMatiere;
        return this;
    }

    public void setCoefficientMatiere(Double coefficientMatiere) {
        this.coefficientMatiere = coefficientMatiere;
    }

    public Double getCoefficientTp() {
        return coefficientTp;
    }

    public Matiere coefficientTp(Double coefficientTp) {
        this.coefficientTp = coefficientTp;
        return this;
    }

    public void setCoefficientTp(Double coefficientTp) {
        this.coefficientTp = coefficientTp;
    }

    public Double getCoefficientDc() {
        return coefficientDc;
    }

    public Matiere coefficientDc(Double coefficientDc) {
        this.coefficientDc = coefficientDc;
        return this;
    }

    public void setCoefficientDc(Double coefficientDc) {
        this.coefficientDc = coefficientDc;
    }

    public Double getCoefficientExem() {
        return coefficientExem;
    }

    public Matiere coefficientExem(Double coefficientExem) {
        this.coefficientExem = coefficientExem;
        return this;
    }

    public void setCoefficientExem(Double coefficientExem) {
        this.coefficientExem = coefficientExem;
    }

    public String getDesignation() {
        return designation;
    }

    public Matiere designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getNom() {
        return nom;
    }

    public Matiere nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Regime getRegime() {
        return regime;
    }

    public Matiere regime(Regime regime) {
        this.regime = regime;
        return this;
    }

    public void setRegime(Regime regime) {
        this.regime = regime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Matiere)) {
            return false;
        }
        return id != null && id.equals(((Matiere) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Matiere{" +
            "id=" + getId() +
            ", coefficientMatiere=" + getCoefficientMatiere() +
            ", coefficientTp=" + getCoefficientTp() +
            ", coefficientDc=" + getCoefficientDc() +
            ", coefficientExem=" + getCoefficientExem() +
            ", designation='" + getDesignation() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
