package com.pfe.gestionnote.domain;
import javax.persistence.*;
@Entity
@Table(name = "groupe_enseigner")
public class GroupEnsiegner {
    @Id
    @Column(name = "nomgroup")
    public String nomgroup;

    @Column(name = "id")
    public Integer id;
    
    @Column(name = "ens_id")
    public String ensid;

    @Column(name = "matiere_id")
    public Integer matiereid;
   
}