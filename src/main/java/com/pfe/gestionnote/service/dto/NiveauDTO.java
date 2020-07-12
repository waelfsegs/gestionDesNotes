package com.pfe.gestionnote.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.Niveau} entity.
 */
public class NiveauDTO implements Serializable {
    
    private Long id;

    private String niveau;
    private String nomcycle;
   
	

    private Long cycleId;
    private Long specialiteId;
    private String specialiteNom;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Long getCycleId() {
        return cycleId;
    }

    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
    }
    public Long getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(Long specialiteId) {
        this.specialiteId = specialiteId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NiveauDTO)) {
            return false;
        }

        return id != null && id.equals(((NiveauDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

  

  

  

    public String getNomcycle() {
        return nomcycle;
    }

    public void setNomcycle(String nomcycle) {
        this.nomcycle = nomcycle;
    }

  

   

    @Override
    public String toString() {
        return "NiveauDTO [cycleId=" + cycleId + ", id=" + id + ", niveau=" + niveau + ", nomcycle=" + nomcycle
                + ", specialiteINom=" + specialiteNom + ", specialiteId=" + specialiteId + "]";
    }

    public String getSpecialiteNom() {
        return specialiteNom;
    }

    public void setSpecialiteNom(String specialiteNom) {
        this.specialiteNom = specialiteNom;
    }
}
