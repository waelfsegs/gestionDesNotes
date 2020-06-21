package com.pfe.gestionnote.repository;
import java.util.List;
import com.pfe.gestionnote.domain.MatiereByEnseignement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatiereByEnseignementRepository extends JpaRepository<MatiereByEnseignement, String> {
    @Query( value = "SELECT * FROM matierebyenseignement WHERE 	ensg_id = ?1",  nativeQuery = true)
    public List<MatiereByEnseignement> getmatierebyenseignement(Integer idEnseignent);
}