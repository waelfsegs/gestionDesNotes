package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.Resultat;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Resultat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long>, JpaSpecificationExecutor<Resultat> {
}
