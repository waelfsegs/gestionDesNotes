package com.pfe.gestionnote.repository;

import com.pfe.gestionnote.domain.Semstre;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Semstre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SemstreRepository extends JpaRepository<Semstre, Long>, JpaSpecificationExecutor<Semstre> {
}
