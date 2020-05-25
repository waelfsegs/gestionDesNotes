package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.Regime;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Regime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegimeRepository extends JpaRepository<Regime, Long>, JpaSpecificationExecutor<Regime> {
}
