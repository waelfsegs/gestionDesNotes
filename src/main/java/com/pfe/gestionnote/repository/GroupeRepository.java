package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.Groupe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Groupe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long>, JpaSpecificationExecutor<Groupe> {
}
