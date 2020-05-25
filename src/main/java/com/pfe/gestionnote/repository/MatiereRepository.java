package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.Matiere;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Matiere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long>, JpaSpecificationExecutor<Matiere> {
}
