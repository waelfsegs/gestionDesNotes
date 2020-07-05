package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.Enseignement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Enseignement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnseignementRepository extends JpaRepository<Enseignement, Long>, JpaSpecificationExecutor<Enseignement> {

}
