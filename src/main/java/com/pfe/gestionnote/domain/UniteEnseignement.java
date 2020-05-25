package com.pfe.gestionnote.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UniteEnseignement.
 */
@Entity
@Table(name = "unite_enseignement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UniteEnseignement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_ue")
    private String nomUE;

    @Column(name = "desgnation_ue")
    private String desgnationUE;

    @Column(name = "coefficient_ue")
    private Integer coefficientUE;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomUE() {
        return nomUE;
    }

    public UniteEnseignement nomUE(String nomUE) {
        this.nomUE = nomUE;
        return this;
    }

    public void setNomUE(String nomUE) {
        this.nomUE = nomUE;
    }

    public String getDesgnationUE() {
        return desgnationUE;
    }

    public UniteEnseignement desgnationUE(String desgnationUE) {
        this.desgnationUE = desgnationUE;
        return this;
    }

    public void setDesgnationUE(String desgnationUE) {
        this.desgnationUE = desgnationUE;
    }

    public Integer getCoefficientUE() {
        return coefficientUE;
    }

    public UniteEnseignement coefficientUE(Integer coefficientUE) {
        this.coefficientUE = coefficientUE;
        return this;
    }

    public void setCoefficientUE(Integer coefficientUE) {
        this.coefficientUE = coefficientUE;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UniteEnseignement)) {
            return false;
        }
        return id != null && id.equals(((UniteEnseignement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UniteEnseignement{" +
            "id=" + getId() +
            ", nomUE='" + getNomUE() + "'" +
            ", desgnationUE='" + getDesgnationUE() + "'" +
            ", coefficientUE=" + getCoefficientUE() +
            "}";
    }
}
