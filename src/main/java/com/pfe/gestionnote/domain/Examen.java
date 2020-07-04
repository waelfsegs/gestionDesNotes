package com.pfe.gestionnote.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Examen.
 */
@Entity
@Table(name = "examen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "session")
    private String session;

    @Column(name = "numcomp")
    private Integer numcomp;

    @ManyToOne
    @JsonIgnoreProperties("examen")
    private Matiere matiere;

    @ManyToOne
    @JsonIgnoreProperties("examen")
    private Inscription inscription;

    @ManyToOne
    @JsonIgnoreProperties("examen")
    private Semstre semstre;

    @ManyToOne
    @JsonIgnoreProperties("examen")
    private Niveau niveau;

    @ManyToOne
    @JsonIgnoreProperties("examen")
    private Specialite specialite;

    @ManyToOne
    @JsonIgnoreProperties("examen")
    private Enveloppe enveloppe;

    @ManyToOne
    @JsonIgnoreProperties("examen")
    private Cycle cycle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public Examen matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getSession() {
        return session;
    }

    public Examen session(String session) {
        this.session = session;
        return this;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Integer getNumcomp() {
        return numcomp;
    }

    public Examen numcomp(Integer numcomp) {
        this.numcomp = numcomp;
        return this;
    }

    public void setNumcomp(Integer numcomp) {
        this.numcomp = numcomp;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public Examen matiere(Matiere matiere) {
        this.matiere = matiere;
        return this;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public Examen inscription(Inscription inscription) {
        this.inscription = inscription;
        return this;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Semstre getSemstre() {
        return semstre;
    }

    public Examen semstre(Semstre semstre) {
        this.semstre = semstre;
        return this;
    }

    public void setSemstre(Semstre semstre) {
        this.semstre = semstre;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public Examen niveau(Niveau niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public Examen specialite(Specialite specialite) {
        this.specialite = specialite;
        return this;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public Enveloppe getEnveloppe() {
        return enveloppe;
    }

    public Examen enveloppe(Enveloppe enveloppe) {
        this.enveloppe = enveloppe;
        return this;
    }

    public void setEnveloppe(Enveloppe enveloppe) {
        this.enveloppe = enveloppe;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public Examen cycle(Cycle cycle) {
        this.cycle = cycle;
        return this;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Examen)) {
            return false;
        }
        return id != null && id.equals(((Examen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Examen{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", session='" + getSession() + "'" +
            ", numcomp=" + getNumcomp() +
            "}";
    }
}
