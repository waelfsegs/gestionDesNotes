package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.EtudiantService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.EtudiantDTO;
import com.pfe.gestionnote.service.dto.EtudiantCriteria;
import com.pfe.gestionnote.service.EtudiantQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pfe.gestionnote.domain.Etudiant}.
 */
@RestController
@RequestMapping("/api")
public class EtudiantResource {

    private final Logger log = LoggerFactory.getLogger(EtudiantResource.class);

    private static final String ENTITY_NAME = "etudiant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtudiantService etudiantService;

    private final EtudiantQueryService etudiantQueryService;

    public EtudiantResource(EtudiantService etudiantService, EtudiantQueryService etudiantQueryService) {
        this.etudiantService = etudiantService;
        this.etudiantQueryService = etudiantQueryService;
    }

    /**
     * {@code POST  /etudiants} : Create a new etudiant.
     *
     * @param etudiantDTO the etudiantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etudiantDTO, or with status {@code 400 (Bad Request)} if the etudiant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etudiants")
    public ResponseEntity<EtudiantDTO> createEtudiant(@RequestBody EtudiantDTO etudiantDTO) throws URISyntaxException {
        log.debug("REST request to save Etudiant : {}", etudiantDTO);
        if (etudiantDTO.getId() != null) {
            throw new BadRequestAlertException("A new etudiant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtudiantDTO result = etudiantService.save(etudiantDTO);
        return ResponseEntity.created(new URI("/api/etudiants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etudiants} : Updates an existing etudiant.
     *
     * @param etudiantDTO the etudiantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etudiantDTO,
     * or with status {@code 400 (Bad Request)} if the etudiantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etudiantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etudiants")
    public ResponseEntity<EtudiantDTO> updateEtudiant(@RequestBody EtudiantDTO etudiantDTO) throws URISyntaxException {
        log.debug("REST request to update Etudiant : {}", etudiantDTO);
        if (etudiantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EtudiantDTO result = etudiantService.save(etudiantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etudiantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /etudiants} : get all the etudiants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etudiants in body.
     */
    @GetMapping("/etudiants")
    public ResponseEntity<List<EtudiantDTO>> getAllEtudiants(EtudiantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Etudiants by criteria: {}", criteria);
        Page<EtudiantDTO> page = etudiantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /etudiants/count} : count all the etudiants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/etudiants/count")
    public ResponseEntity<Long> countEtudiants(EtudiantCriteria criteria) {
        log.debug("REST request to count Etudiants by criteria: {}", criteria);
        return ResponseEntity.ok().body(etudiantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /etudiants/:id} : get the "id" etudiant.
     *
     * @param id the id of the etudiantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etudiantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etudiants/{id}")
    public ResponseEntity<EtudiantDTO> getEtudiant(@PathVariable Long id) {
        log.debug("REST request to get Etudiant : {}", id);
        Optional<EtudiantDTO> etudiantDTO = etudiantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etudiantDTO);
    }

    /**
     * {@code DELETE  /etudiants/:id} : delete the "id" etudiant.
     *
     * @param id the id of the etudiantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etudiants/{id}")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        log.debug("REST request to delete Etudiant : {}", id);

        etudiantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
