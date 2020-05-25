package com.pfe.gestionnote.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Semstre.
 */
@Entity
@Table(name = "semstre")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Semstre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "num_semstre")
    private Integer numSemstre;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnnee() {
        return annee;
    }

    public Semstre annee(Integer annee) {
        this.annee = annee;
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getNumSemstre() {
        return numSemstre;
    }

    public Semstre numSemstre(Integer numSemstre) {
        this.numSemstre = numSemstre;
        return this;
    }

    public void setNumSemstre(Integer numSemstre) {
        this.numSemstre = numSemstre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Semstre)) {
            return false;
        }
        return id != null && id.equals(((Semstre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Semstre{" +
            "id=" + getId() +
            ", annee=" + getAnnee() +
            ", numSemstre=" + getNumSemstre() +
            "}";
    }
}
