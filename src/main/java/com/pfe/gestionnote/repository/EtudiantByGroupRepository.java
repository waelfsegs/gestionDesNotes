package com.pfe.gestionnote.repository;

import java.util.List;
import com.pfe.gestionnote.domain.EtudiantByGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface EtudiantByGroupRepository extends JpaRepository<EtudiantByGroup,Integer> {
    @Query( value = "SELECT * FROM etudiantbygroup WHERE groupe_id = ?1",  nativeQuery = true)
    public List<EtudiantByGroup> getEtudiantByGroup(Integer idgroup);

}