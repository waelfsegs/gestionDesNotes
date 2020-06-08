package com.pfe.gestionnote.domain;

import javax.persistence.*;

@Entity
@Table(name = "etudiantbygroup")
public class EtudiantByGroup {
    @Id
    @Column(name = "matricule")
    public String matricule;

    @Column(name = "nom")
    public String nom;
    
    @Column(name = "prenom")
    public String prenom;

    @Column(name = " groupe_id")
    public Integer  groupe_id;
   
}