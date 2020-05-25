package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.Cycle;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cycle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CycleRepository extends JpaRepository<Cycle, Long>, JpaSpecificationExecutor<Cycle> {
}
