package com.pfe.gestionnote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Enseignement.
 */
@Entity
@Table(name = "enseignement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Enseignement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "enseignements", allowSetters = true)
    private Matiere matiere;

    @ManyToOne
    @JsonIgnoreProperties(value = "enseignements", allowSetters = true)
    private Enseignant enseignant;

    @ManyToOne
    @JsonIgnoreProperties(value = "enseignements", allowSetters = true)
    private Groupe groupe;

    @ManyToOne
    @JsonIgnoreProperties(value = "enseignements", allowSetters = true)
    private TypeEnseignement typeEnseignement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public Enseignement matiere(Matiere matiere) {
        this.matiere = matiere;
        return this;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public Enseignement enseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        return this;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public Enseignement groupe(Groupe groupe) {
        this.groupe = groupe;
        return this;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public TypeEnseignement getTypeEnseignement() {
        return typeEnseignement;
    }

    public Enseignement typeEnseignement(TypeEnseignement typeEnseignement) {
        this.typeEnseignement = typeEnseignement;
        return this;
    }

    public void setTypeEnseignement(TypeEnseignement typeEnseignement) {
        this.typeEnseignement = typeEnseignement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enseignement)) {
            return false;
        }
        return id != null && id.equals(((Enseignement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enseignement{" +
            "id=" + getId() +
            "}";
    }
}
