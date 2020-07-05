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
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Enseignement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("enseignements")
    private Matiere matiere;

    @ManyToOne
    @JsonIgnoreProperties("enseignements")
    private Enseignant enseignant;

    @ManyToOne
    @JsonIgnoreProperties("enseignements")
    private Groupe groupe;

    @ManyToOne
    @JsonIgnoreProperties("enseignements")
    private TypeEnseignement typeEnseignement;

    @ManyToOne
    @JsonIgnoreProperties("inscriptions")
    private Classe classe;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public Classe getClasse() {
        return classe;
    }

    public Enseignement classe(Classe classe) {
        this.classe = classe;
        return this;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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

    @Override
    public String toString() {
        return "Enseignement{" +
            "id=" + getId() +
            "}";
    }
}
