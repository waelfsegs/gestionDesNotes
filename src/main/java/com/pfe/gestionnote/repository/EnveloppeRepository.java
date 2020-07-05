package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.Enveloppe;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Enveloppe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnveloppeRepository extends JpaRepository<Enveloppe, Long>, JpaSpecificationExecutor<Enveloppe> {

}
