package com.pfe.gestionnote.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Departement.
 */
@Entity
@Table(name = "departement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Departement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_dep")
    private String nomDep;

    @Column(name = "designiation")
    private String designiation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDep() {
        return nomDep;
    }

    public Departement nomDep(String nomDep) {
        this.nomDep = nomDep;
        return this;
    }

    public void setNomDep(String nomDep) {
        this.nomDep = nomDep;
    }

    public String getDesigniation() {
        return designiation;
    }

    public Departement designiation(String designiation) {
        this.designiation = designiation;
        return this;
    }

    public void setDesigniation(String designiation) {
        this.designiation = designiation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departement)) {
            return false;
        }
        return id != null && id.equals(((Departement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Departement{" +
            "id=" + getId() +
            ", nomDep='" + getNomDep() + "'" +
            ", designiation='" + getDesigniation() + "'" +
            "}";
    }
}
