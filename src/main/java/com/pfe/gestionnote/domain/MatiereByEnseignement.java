package com.pfe.gestionnote.domain;
import javax.persistence.*;

@Entity
@Table(name ="matierebyenseignement")
public class MatiereByEnseignement {
    @Id
    @Column(name = "nom")
    public String nomMatiere;

    @Column(name = "id")
    public Integer id;

    @Column(name = "designation")
    public String designation;
    
    @Column(name = "groupe_id")
    public String groupe_id;

    @Column(name = "ensg_id")
    public Integer ensgId; 
}