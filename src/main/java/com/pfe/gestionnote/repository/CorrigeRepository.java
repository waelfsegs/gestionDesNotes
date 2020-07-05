package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.Corrige;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Corrige entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CorrigeRepository extends JpaRepository<Corrige, Long>, JpaSpecificationExecutor<Corrige> {

}
