package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.Spicialitematiere;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Spicialitematiere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpicialitematiereRepository extends JpaRepository<Spicialitematiere, Long>, JpaSpecificationExecutor<Spicialitematiere> {

}
