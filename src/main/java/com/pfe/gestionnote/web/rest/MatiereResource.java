package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.MatiereService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.MatiereDTO;
import com.pfe.gestionnote.service.dto.MatiereCriteria;
import com.pfe.gestionnote.service.MatiereQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Matiere}.
 */
@RestController
@RequestMapping("/api")
public class MatiereResource {

    private final Logger log = LoggerFactory.getLogger(MatiereResource.class);

    private static final String ENTITY_NAME = "matiere";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatiereService matiereService;

    private final MatiereQueryService matiereQueryService;

    public MatiereResource(MatiereService matiereService, MatiereQueryService matiereQueryService) {
        this.matiereService = matiereService;
        this.matiereQueryService = matiereQueryService;
    }

    /**
     * {@code POST  /matieres} : Create a new matiere.
     *
     * @param matiereDTO the matiereDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matiereDTO, or with status {@code 400 (Bad Request)} if the matiere has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matieres")
    public ResponseEntity<MatiereDTO> createMatiere(@RequestBody MatiereDTO matiereDTO) throws URISyntaxException {
        log.debug("REST request to save Matiere : {}", matiereDTO);
        if (matiereDTO.getId() != null) {
            throw new BadRequestAlertException("A new matiere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatiereDTO result = matiereService.save(matiereDTO);
        return ResponseEntity.created(new URI("/api/matieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matieres} : Updates an existing matiere.
     *
     * @param matiereDTO the matiereDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matiereDTO,
     * or with status {@code 400 (Bad Request)} if the matiereDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matiereDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matieres")
    public ResponseEntity<MatiereDTO> updateMatiere(@RequestBody MatiereDTO matiereDTO) throws URISyntaxException {
        log.debug("REST request to update Matiere : {}", matiereDTO);
        if (matiereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatiereDTO result = matiereService.save(matiereDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matiereDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /matieres} : get all the matieres.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matieres in body.
     */
    @GetMapping("/matieres")
    public ResponseEntity<List<MatiereDTO>> getAllMatieres(MatiereCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Matieres by criteria: {}", criteria);
        Page<MatiereDTO> page = matiereQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matieres/count} : count all the matieres.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/matieres/count")
    public ResponseEntity<Long> countMatieres(MatiereCriteria criteria) {
        log.debug("REST request to count Matieres by criteria: {}", criteria);
        return ResponseEntity.ok().body(matiereQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /matieres/:id} : get the "id" matiere.
     *
     * @param id the id of the matiereDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matiereDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matieres/{id}")
    public ResponseEntity<MatiereDTO> getMatiere(@PathVariable Long id) {
        log.debug("REST request to get Matiere : {}", id);
        Optional<MatiereDTO> matiereDTO = matiereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matiereDTO);
    }

    /**
     * {@code DELETE  /matieres/:id} : delete the "id" matiere.
     *
     * @param id the id of the matiereDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matieres/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        log.debug("REST request to delete Matiere : {}", id);

        matiereService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
