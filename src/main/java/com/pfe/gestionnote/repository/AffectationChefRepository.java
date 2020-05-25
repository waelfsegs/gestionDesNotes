package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.AffectationChef;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AffectationChef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffectationChefRepository extends JpaRepository<AffectationChef, Long>, JpaSpecificationExecutor<AffectationChef> {
}
