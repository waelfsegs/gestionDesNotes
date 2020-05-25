package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.TypeEnseignement;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeEnseignement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeEnseignementRepository extends JpaRepository<TypeEnseignement, Long>, JpaSpecificationExecutor<TypeEnseignement> {
}
