package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.TypeEnseignementService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.TypeEnseignementDTO;
import com.pfe.gestionnote.service.dto.TypeEnseignementCriteria;
import com.pfe.gestionnote.service.TypeEnseignementQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.TypeEnseignement}.
 */
@RestController
@RequestMapping("/api")
public class TypeEnseignementResource {

    private final Logger log = LoggerFactory.getLogger(TypeEnseignementResource.class);

    private static final String ENTITY_NAME = "typeEnseignement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeEnseignementService typeEnseignementService;

    private final TypeEnseignementQueryService typeEnseignementQueryService;

    public TypeEnseignementResource(TypeEnseignementService typeEnseignementService, TypeEnseignementQueryService typeEnseignementQueryService) {
        this.typeEnseignementService = typeEnseignementService;
        this.typeEnseignementQueryService = typeEnseignementQueryService;
    }

    /**
     * {@code POST  /type-enseignements} : Create a new typeEnseignement.
     *
     * @param typeEnseignementDTO the typeEnseignementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeEnseignementDTO, or with status {@code 400 (Bad Request)} if the typeEnseignement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-enseignements")
    public ResponseEntity<TypeEnseignementDTO> createTypeEnseignement(@RequestBody TypeEnseignementDTO typeEnseignementDTO) throws URISyntaxException {
        log.debug("REST request to save TypeEnseignement : {}", typeEnseignementDTO);
        if (typeEnseignementDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeEnseignement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeEnseignementDTO result = typeEnseignementService.save(typeEnseignementDTO);
        return ResponseEntity.created(new URI("/api/type-enseignements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-enseignements} : Updates an existing typeEnseignement.
     *
     * @param typeEnseignementDTO the typeEnseignementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeEnseignementDTO,
     * or with status {@code 400 (Bad Request)} if the typeEnseignementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeEnseignementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-enseignements")
    public ResponseEntity<TypeEnseignementDTO> updateTypeEnseignement(@RequestBody TypeEnseignementDTO typeEnseignementDTO) throws URISyntaxException {
        log.debug("REST request to update TypeEnseignement : {}", typeEnseignementDTO);
        if (typeEnseignementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeEnseignementDTO result = typeEnseignementService.save(typeEnseignementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeEnseignementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-enseignements} : get all the typeEnseignements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeEnseignements in body.
     */
    @GetMapping("/type-enseignements")
    public ResponseEntity<List<TypeEnseignementDTO>> getAllTypeEnseignements(TypeEnseignementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TypeEnseignements by criteria: {}", criteria);
        Page<TypeEnseignementDTO> page = typeEnseignementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-enseignements/count} : count all the typeEnseignements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/type-enseignements/count")
    public ResponseEntity<Long> countTypeEnseignements(TypeEnseignementCriteria criteria) {
        log.debug("REST request to count TypeEnseignements by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeEnseignementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /type-enseignements/:id} : get the "id" typeEnseignement.
     *
     * @param id the id of the typeEnseignementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeEnseignementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-enseignements/{id}")
    public ResponseEntity<TypeEnseignementDTO> getTypeEnseignement(@PathVariable Long id) {
        log.debug("REST request to get TypeEnseignement : {}", id);
        Optional<TypeEnseignementDTO> typeEnseignementDTO = typeEnseignementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeEnseignementDTO);
    }

    /**
     * {@code DELETE  /type-enseignements/:id} : delete the "id" typeEnseignement.
     *
     * @param id the id of the typeEnseignementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-enseignements/{id}")
    public ResponseEntity<Void> deleteTypeEnseignement(@PathVariable Long id) {
        log.debug("REST request to delete TypeEnseignement : {}", id);

        typeEnseignementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
