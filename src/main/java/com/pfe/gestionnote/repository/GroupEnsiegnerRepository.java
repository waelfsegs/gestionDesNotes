package com.pfe.gestionnote.repository;

import java.util.List;

import com.pfe.gestionnote.domain.GroupEnsiegner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface GroupEnsiegnerRepository extends JpaRepository<GroupEnsiegner, String> {
 
    @Query( value = "SELECT * FROM groupe_enseigner WHERE  ens_id=?1 and matiere_id=?2	",  nativeQuery = true)
    public List<GroupEnsiegner> getGroupEnseigner(Integer idens,Integer matiereid);

    
}