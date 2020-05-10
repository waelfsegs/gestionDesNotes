package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.Etudiant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Etudiant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long>, JpaSpecificationExecutor<Etudiant> {
}
