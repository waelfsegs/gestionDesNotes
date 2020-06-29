package com.pfe.gestionnote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Enveloppe.
 */
@Entity
@Table(name = "enveloppe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Enveloppe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nameenv")
    private String nameenv;

    @ManyToOne
    @JsonIgnoreProperties("enveloppes")
    private Matiere maiere;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameenv() {
        return nameenv;
    }

    public Enveloppe nameenv(String nameenv) {
        this.nameenv = nameenv;
        return this;
    }

    public void setNameenv(String nameenv) {
        this.nameenv = nameenv;
    }

    public Matiere getMaiere() {
        return maiere;
    }

    public Enveloppe maiere(Matiere matiere) {
        this.maiere = matiere;
        return this;
    }

    public void setMaiere(Matiere matiere) {
        this.maiere = matiere;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enveloppe)) {
            return false;
        }
        return id != null && id.equals(((Enveloppe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Enveloppe{" +
            "id=" + getId() +
            ", nameenv='" + getNameenv() + "'" +
            "}";
    }
}
