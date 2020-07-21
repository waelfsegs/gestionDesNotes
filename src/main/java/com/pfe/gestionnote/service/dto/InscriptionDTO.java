package com.pfe.gestionnote.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Inscription} entity.
 */
public class InscriptionDTO implements Serializable {
    
    private Long id;

    private LocalDate date;

    private LocalDate annee;


    private Long etudiantId;

    private Long classeId;

    private Long groupeId;

    private Long semstreId;
    
    private String matricule;
    private Integer cin;

    private String nom;


    private String prenom;

    private Integer tel;
    private LocalDate dateNais;

    private String nomclass;
	  private String nomgroup;
      private Integer numSemstre;
      public Long cycleId;

      public Long niveauId;

      public Long specialiteId;
	
	
      public String cyclenom;
      public String niveau;
      public String specialitelabelle;
     
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getAnnee() {
        return annee;
    }

    public void setAnnee(LocalDate annee) {
        this.annee = annee;
    }

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public Long getClasseId() {
        return classeId;
    }

    public void setClasseId(Long classeId) {
        this.classeId = classeId;
    }

    public Long getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(Long groupeId) {
        this.groupeId = groupeId;
    }

    public Long getSemstreId() {
        return semstreId;
    }

    public void setSemstreId(Long semstreId) {
        this.semstreId = semstreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InscriptionDTO)) {
            return false;
        }

        return id != null && id.equals(((InscriptionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Integer getCin() {
        return cin;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getNomclass() {
        return nomclass;
    }

    public void setNomclass(String nomclass) {
        this.nomclass = nomclass;
    }

    public String getNomgroup() {
        return nomgroup;
    }

    public void setNomgroup(String nomgroup) {
        this.nomgroup = nomgroup;
    }

    public Integer getNumSemstre() {
        return numSemstre;
    }

    public void setNumSemstre(Integer numSemstre) {
        this.numSemstre = numSemstre;
    }

  

    public LocalDate getDateNais() {
        return dateNais;
    }

    public void setDateNais(LocalDate dateNais) {
        this.dateNais = dateNais;
    }

    @Override
    public String toString() {
        return "InscriptionDTO [annee=" + annee + ", cin=" + cin + ", classeId=" + classeId + ", date=" + date
                + ", dateNais=" + dateNais + ", etudiantId=" + etudiantId + ", groupeId=" + groupeId + ", id=" + id
                + ", matricule=" + matricule + ", nom=" + nom + ", nomclass=" + nomclass + ", nomgroup=" + nomgroup
                + ", numSemstre=" + numSemstre + ", prenom=" + prenom + ", semstreId=" + semstreId + ", tel=" + tel
                + "]";
    }
}
