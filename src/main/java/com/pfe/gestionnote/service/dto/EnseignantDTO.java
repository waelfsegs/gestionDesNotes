package com.pfe.gestionnote.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Enseignant} entity.
 */
public class EnseignantDTO implements Serializable {
    
    private Long id;

    private String nom;

    private String pernom;

    private String mail;

    private Integer matricule;

    private Integer cin;

    private LocalDate dateEmbauchement;
private  String departementName;

    private Long departementId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPernom() {
        return pernom;
    }

    public void setPernom(String pernom) {
        this.pernom = pernom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getMatricule() {
        return matricule;
    }

    public void setMatricule(Integer matricule) {
        this.matricule = matricule;
    }

    public Integer getCin() {
        return cin;
    }

    public void setCin(Integer cin) {
        this.cin = cin;
    }

    public LocalDate getDateEmbauchement() {
        return dateEmbauchement;
    }

    public void setDateEmbauchement(LocalDate dateEmbauchement) {
        this.dateEmbauchement = dateEmbauchement;
    }

    public Long getDepartementId() {
        return departementId;
    }

    public void setDepartementId(Long departementId) {
        this.departementId = departementId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnseignantDTO)) {
            return false;
        }

        return id != null && id.equals(((EnseignantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EnseignantDTO [cin=" + cin + ", dateEmbauchement=" + dateEmbauchement + ", departementId="
                + departementId + ", departementName=" + departementName + ", id=" + id + ", mail=" + mail
                + ", matricule=" + matricule + ", nom=" + nom + ", pernom=" + pernom + "]";
    }

    public String getDepartementName() {
        return departementName;
    }

    public void setDepartementName(String departementName) {
        this.departementName = departementName;
    }
}
