package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.UniteEnseignement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UniteEnseignement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniteEnseignementRepository extends JpaRepository<UniteEnseignement, Long>, JpaSpecificationExecutor<UniteEnseignement> {
}
