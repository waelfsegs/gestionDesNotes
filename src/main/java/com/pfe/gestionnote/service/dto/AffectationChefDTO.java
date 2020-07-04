package com.pfe.gestionnote.service.dto;

import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link com.pfe.gestionnote.domain.AffectationChef} entity.
 */
public class AffectationChefDTO implements Serializable {
    
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;


    private Long departementId;

    private Long enseignantId;
   private String departementName;
   private String enseignantName;
   private String enseignantPrenom;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getDepartementId() {
        return departementId;
    }

    public void setDepartementId(Long departementId) {
        this.departementId = departementId;
    }

    public Long getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(Long enseignantId) {
        this.enseignantId = enseignantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AffectationChefDTO)) {
            return false;
        }

        return id != null && id.equals(((AffectationChefDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
  
    // prettier-ignore
    @Override
    public String toString() {
        return "AffectationChefDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", departementId=" + getDepartementId() +
            ", enseignantId=" + getEnseignantId() +
            ", departementName=" + getDepartementName() +
            ", enseignantName=" + getEnseignantName() +
            ", enseignantPrenom=" + getEnseignantPrenom() +
            "}";
    }

    public String getDepartementName() {
        return departementName;
    }

    public void setDepartementName(String departementName) {
        this.departementName = departementName;
    }

    public String getEnseignantName() {
        return enseignantName;
    }

    public void setEnseignantName(String enseignantName) {
        this.enseignantName = enseignantName;
    }

    public String getEnseignantPrenom() {
        return enseignantPrenom;
    }

    public void setEnseignantPrenom(String enseignantPrenom) {
        this.enseignantPrenom = enseignantPrenom;
    }
}
