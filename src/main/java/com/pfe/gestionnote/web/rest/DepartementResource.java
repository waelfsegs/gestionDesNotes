package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.DepartementService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.DepartementDTO;
import com.pfe.gestionnote.service.dto.DepartementCriteria;
import com.pfe.gestionnote.service.DepartementQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Departement}.
 */
@RestController
@RequestMapping("/api")
public class DepartementResource {

    private final Logger log = LoggerFactory.getLogger(DepartementResource.class);

    private static final String ENTITY_NAME = "departement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartementService departementService;

    private final DepartementQueryService departementQueryService;

    public DepartementResource(DepartementService departementService, DepartementQueryService departementQueryService) {
        this.departementService = departementService;
        this.departementQueryService = departementQueryService;
    }

    /**
     * {@code POST  /departements} : Create a new departement.
     *
     * @param departementDTO the departementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departementDTO, or with status {@code 400 (Bad Request)} if the departement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/departements")
    public ResponseEntity<DepartementDTO> createDepartement(@RequestBody DepartementDTO departementDTO) throws URISyntaxException {
        log.debug("REST request to save Departement : {}", departementDTO);
        if (departementDTO.getId() != null) {
            throw new BadRequestAlertException("A new departement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartementDTO result = departementService.save(departementDTO);
        return ResponseEntity.created(new URI("/api/departements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /departements} : Updates an existing departement.
     *
     * @param departementDTO the departementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departementDTO,
     * or with status {@code 400 (Bad Request)} if the departementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/departements")
    public ResponseEntity<DepartementDTO> updateDepartement(@RequestBody DepartementDTO departementDTO) throws URISyntaxException {
        log.debug("REST request to update Departement : {}", departementDTO);
        if (departementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepartementDTO result = departementService.save(departementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /departements} : get all the departements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departements in body.
     */
    @GetMapping("/departements")
    public ResponseEntity<List<DepartementDTO>> getAllDepartements(DepartementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Departements by criteria: {}", criteria);
        Page<DepartementDTO> page = departementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /departements/count} : count all the departements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/departements/count")
    public ResponseEntity<Long> countDepartements(DepartementCriteria criteria) {
        log.debug("REST request to count Departements by criteria: {}", criteria);
        return ResponseEntity.ok().body(departementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /departements/:id} : get the "id" departement.
     *
     * @param id the id of the departementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/departements/{id}")
    public ResponseEntity<DepartementDTO> getDepartement(@PathVariable Long id) {
        log.debug("REST request to get Departement : {}", id);
        Optional<DepartementDTO> departementDTO = departementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departementDTO);
    }

    /**
     * {@code DELETE  /departements/:id} : delete the "id" departement.
     *
     * @param id the id of the departementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/departements/{id}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable Long id) {
        log.debug("REST request to delete Departement : {}", id);

        departementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
