package com.pfe.gestionnote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Corrige.
 */
@Entity
@Table(name = "corrige")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Corrige implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("corriges")
    private Enseignant enseignant;

    @ManyToOne
    @JsonIgnoreProperties("corriges")
    private Enveloppe enveloppe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public Corrige enseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        return this;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Enveloppe getEnveloppe() {
        return enveloppe;
    }

    public Corrige enveloppe(Enveloppe enveloppe) {
        this.enveloppe = enveloppe;
        return this;
    }

    public void setEnveloppe(Enveloppe enveloppe) {
        this.enveloppe = enveloppe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Corrige)) {
            return false;
        }
        return id != null && id.equals(((Corrige) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Corrige{" +
            "id=" + getId() +
            "}";
    }
}
