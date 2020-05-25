package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Semstre;
import com.pfe.gestionnote.repository.SemstreRepository;
import com.pfe.gestionnote.service.SemstreService;
import com.pfe.gestionnote.service.dto.SemstreDTO;
import com.pfe.gestionnote.service.mapper.SemstreMapper;
import com.pfe.gestionnote.service.dto.SemstreCriteria;
import com.pfe.gestionnote.service.SemstreQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SemstreResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SemstreResourceIT {

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;
    private static final Integer SMALLER_ANNEE = 1 - 1;

    private static final Integer DEFAULT_NUM_SEMSTRE = 1;
    private static final Integer UPDATED_NUM_SEMSTRE = 2;
    private static final Integer SMALLER_NUM_SEMSTRE = 1 - 1;

    @Autowired
    private SemstreRepository semstreRepository;

    @Autowired
    private SemstreMapper semstreMapper;

    @Autowired
    private SemstreService semstreService;

    @Autowired
    private SemstreQueryService semstreQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSemstreMockMvc;

    private Semstre semstre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semstre createEntity(EntityManager em) {
        Semstre semstre = new Semstre()
            .annee(DEFAULT_ANNEE)
            .numSemstre(DEFAULT_NUM_SEMSTRE);
        return semstre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semstre createUpdatedEntity(EntityManager em) {
        Semstre semstre = new Semstre()
            .annee(UPDATED_ANNEE)
            .numSemstre(UPDATED_NUM_SEMSTRE);
        return semstre;
    }

    @BeforeEach
    public void initTest() {
        semstre = createEntity(em);
    }

    @Test
    @Transactional
    public void createSemstre() throws Exception {
        int databaseSizeBeforeCreate = semstreRepository.findAll().size();
        // Create the Semstre
        SemstreDTO semstreDTO = semstreMapper.toDto(semstre);
        restSemstreMockMvc.perform(post("/api/semstres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semstreDTO)))
            .andExpect(status().isCreated());

        // Validate the Semstre in the database
        List<Semstre> semstreList = semstreRepository.findAll();
        assertThat(semstreList).hasSize(databaseSizeBeforeCreate + 1);
        Semstre testSemstre = semstreList.get(semstreList.size() - 1);
        assertThat(testSemstre.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testSemstre.getNumSemstre()).isEqualTo(DEFAULT_NUM_SEMSTRE);
    }

    @Test
    @Transactional
    public void createSemstreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = semstreRepository.findAll().size();

        // Create the Semstre with an existing ID
        semstre.setId(1L);
        SemstreDTO semstreDTO = semstreMapper.toDto(semstre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSemstreMockMvc.perform(post("/api/semstres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semstreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Semstre in the database
        List<Semstre> semstreList = semstreRepository.findAll();
        assertThat(semstreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSemstres() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList
        restSemstreMockMvc.perform(get("/api/semstres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semstre.getId().intValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].numSemstre").value(hasItem(DEFAULT_NUM_SEMSTRE)));
    }
    
    @Test
    @Transactional
    public void getSemstre() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get the semstre
        restSemstreMockMvc.perform(get("/api/semstres/{id}", semstre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(semstre.getId().intValue()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.numSemstre").value(DEFAULT_NUM_SEMSTRE));
    }


    @Test
    @Transactional
    public void getSemstresByIdFiltering() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        Long id = semstre.getId();

        defaultSemstreShouldBeFound("id.equals=" + id);
        defaultSemstreShouldNotBeFound("id.notEquals=" + id);

        defaultSemstreShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSemstreShouldNotBeFound("id.greaterThan=" + id);

        defaultSemstreShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSemstreShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSemstresByAnneeIsEqualToSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where annee equals to DEFAULT_ANNEE
        defaultSemstreShouldBeFound("annee.equals=" + DEFAULT_ANNEE);

        // Get all the semstreList where annee equals to UPDATED_ANNEE
        defaultSemstreShouldNotBeFound("annee.equals=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllSemstresByAnneeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where annee not equals to DEFAULT_ANNEE
        defaultSemstreShouldNotBeFound("annee.notEquals=" + DEFAULT_ANNEE);

        // Get all the semstreList where annee not equals to UPDATED_ANNEE
        defaultSemstreShouldBeFound("annee.notEquals=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllSemstresByAnneeIsInShouldWork() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where annee in DEFAULT_ANNEE or UPDATED_ANNEE
        defaultSemstreShouldBeFound("annee.in=" + DEFAULT_ANNEE + "," + UPDATED_ANNEE);

        // Get all the semstreList where annee equals to UPDATED_ANNEE
        defaultSemstreShouldNotBeFound("annee.in=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllSemstresByAnneeIsNullOrNotNull() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where annee is not null
        defaultSemstreShouldBeFound("annee.specified=true");

        // Get all the semstreList where annee is null
        defaultSemstreShouldNotBeFound("annee.specified=false");
    }

    @Test
    @Transactional
    public void getAllSemstresByAnneeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where annee is greater than or equal to DEFAULT_ANNEE
        defaultSemstreShouldBeFound("annee.greaterThanOrEqual=" + DEFAULT_ANNEE);

        // Get all the semstreList where annee is greater than or equal to UPDATED_ANNEE
        defaultSemstreShouldNotBeFound("annee.greaterThanOrEqual=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllSemstresByAnneeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where annee is less than or equal to DEFAULT_ANNEE
        defaultSemstreShouldBeFound("annee.lessThanOrEqual=" + DEFAULT_ANNEE);

        // Get all the semstreList where annee is less than or equal to SMALLER_ANNEE
        defaultSemstreShouldNotBeFound("annee.lessThanOrEqual=" + SMALLER_ANNEE);
    }

    @Test
    @Transactional
    public void getAllSemstresByAnneeIsLessThanSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where annee is less than DEFAULT_ANNEE
        defaultSemstreShouldNotBeFound("annee.lessThan=" + DEFAULT_ANNEE);

        // Get all the semstreList where annee is less than UPDATED_ANNEE
        defaultSemstreShouldBeFound("annee.lessThan=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllSemstresByAnneeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where annee is greater than DEFAULT_ANNEE
        defaultSemstreShouldNotBeFound("annee.greaterThan=" + DEFAULT_ANNEE);

        // Get all the semstreList where annee is greater than SMALLER_ANNEE
        defaultSemstreShouldBeFound("annee.greaterThan=" + SMALLER_ANNEE);
    }


    @Test
    @Transactional
    public void getAllSemstresByNumSemstreIsEqualToSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where numSemstre equals to DEFAULT_NUM_SEMSTRE
        defaultSemstreShouldBeFound("numSemstre.equals=" + DEFAULT_NUM_SEMSTRE);

        // Get all the semstreList where numSemstre equals to UPDATED_NUM_SEMSTRE
        defaultSemstreShouldNotBeFound("numSemstre.equals=" + UPDATED_NUM_SEMSTRE);
    }

    @Test
    @Transactional
    public void getAllSemstresByNumSemstreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where numSemstre not equals to DEFAULT_NUM_SEMSTRE
        defaultSemstreShouldNotBeFound("numSemstre.notEquals=" + DEFAULT_NUM_SEMSTRE);

        // Get all the semstreList where numSemstre not equals to UPDATED_NUM_SEMSTRE
        defaultSemstreShouldBeFound("numSemstre.notEquals=" + UPDATED_NUM_SEMSTRE);
    }

    @Test
    @Transactional
    public void getAllSemstresByNumSemstreIsInShouldWork() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where numSemstre in DEFAULT_NUM_SEMSTRE or UPDATED_NUM_SEMSTRE
        defaultSemstreShouldBeFound("numSemstre.in=" + DEFAULT_NUM_SEMSTRE + "," + UPDATED_NUM_SEMSTRE);

        // Get all the semstreList where numSemstre equals to UPDATED_NUM_SEMSTRE
        defaultSemstreShouldNotBeFound("numSemstre.in=" + UPDATED_NUM_SEMSTRE);
    }

    @Test
    @Transactional
    public void getAllSemstresByNumSemstreIsNullOrNotNull() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where numSemstre is not null
        defaultSemstreShouldBeFound("numSemstre.specified=true");

        // Get all the semstreList where numSemstre is null
        defaultSemstreShouldNotBeFound("numSemstre.specified=false");
    }

    @Test
    @Transactional
    public void getAllSemstresByNumSemstreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where numSemstre is greater than or equal to DEFAULT_NUM_SEMSTRE
        defaultSemstreShouldBeFound("numSemstre.greaterThanOrEqual=" + DEFAULT_NUM_SEMSTRE);

        // Get all the semstreList where numSemstre is greater than or equal to UPDATED_NUM_SEMSTRE
        defaultSemstreShouldNotBeFound("numSemstre.greaterThanOrEqual=" + UPDATED_NUM_SEMSTRE);
    }

    @Test
    @Transactional
    public void getAllSemstresByNumSemstreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where numSemstre is less than or equal to DEFAULT_NUM_SEMSTRE
        defaultSemstreShouldBeFound("numSemstre.lessThanOrEqual=" + DEFAULT_NUM_SEMSTRE);

        // Get all the semstreList where numSemstre is less than or equal to SMALLER_NUM_SEMSTRE
        defaultSemstreShouldNotBeFound("numSemstre.lessThanOrEqual=" + SMALLER_NUM_SEMSTRE);
    }

    @Test
    @Transactional
    public void getAllSemstresByNumSemstreIsLessThanSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where numSemstre is less than DEFAULT_NUM_SEMSTRE
        defaultSemstreShouldNotBeFound("numSemstre.lessThan=" + DEFAULT_NUM_SEMSTRE);

        // Get all the semstreList where numSemstre is less than UPDATED_NUM_SEMSTRE
        defaultSemstreShouldBeFound("numSemstre.lessThan=" + UPDATED_NUM_SEMSTRE);
    }

    @Test
    @Transactional
    public void getAllSemstresByNumSemstreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        // Get all the semstreList where numSemstre is greater than DEFAULT_NUM_SEMSTRE
        defaultSemstreShouldNotBeFound("numSemstre.greaterThan=" + DEFAULT_NUM_SEMSTRE);

        // Get all the semstreList where numSemstre is greater than SMALLER_NUM_SEMSTRE
        defaultSemstreShouldBeFound("numSemstre.greaterThan=" + SMALLER_NUM_SEMSTRE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSemstreShouldBeFound(String filter) throws Exception {
        restSemstreMockMvc.perform(get("/api/semstres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semstre.getId().intValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].numSemstre").value(hasItem(DEFAULT_NUM_SEMSTRE)));

        // Check, that the count call also returns 1
        restSemstreMockMvc.perform(get("/api/semstres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSemstreShouldNotBeFound(String filter) throws Exception {
        restSemstreMockMvc.perform(get("/api/semstres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSemstreMockMvc.perform(get("/api/semstres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSemstre() throws Exception {
        // Get the semstre
        restSemstreMockMvc.perform(get("/api/semstres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSemstre() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        int databaseSizeBeforeUpdate = semstreRepository.findAll().size();

        // Update the semstre
        Semstre updatedSemstre = semstreRepository.findById(semstre.getId()).get();
        // Disconnect from session so that the updates on updatedSemstre are not directly saved in db
        em.detach(updatedSemstre);
        updatedSemstre
            .annee(UPDATED_ANNEE)
            .numSemstre(UPDATED_NUM_SEMSTRE);
        SemstreDTO semstreDTO = semstreMapper.toDto(updatedSemstre);

        restSemstreMockMvc.perform(put("/api/semstres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semstreDTO)))
            .andExpect(status().isOk());

        // Validate the Semstre in the database
        List<Semstre> semstreList = semstreRepository.findAll();
        assertThat(semstreList).hasSize(databaseSizeBeforeUpdate);
        Semstre testSemstre = semstreList.get(semstreList.size() - 1);
        assertThat(testSemstre.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSemstre.getNumSemstre()).isEqualTo(UPDATED_NUM_SEMSTRE);
    }

    @Test
    @Transactional
    public void updateNonExistingSemstre() throws Exception {
        int databaseSizeBeforeUpdate = semstreRepository.findAll().size();

        // Create the Semstre
        SemstreDTO semstreDTO = semstreMapper.toDto(semstre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSemstreMockMvc.perform(put("/api/semstres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semstreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Semstre in the database
        List<Semstre> semstreList = semstreRepository.findAll();
        assertThat(semstreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSemstre() throws Exception {
        // Initialize the database
        semstreRepository.saveAndFlush(semstre);

        int databaseSizeBeforeDelete = semstreRepository.findAll().size();

        // Delete the semstre
        restSemstreMockMvc.perform(delete("/api/semstres/{id}", semstre.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Semstre> semstreList = semstreRepository.findAll();
        assertThat(semstreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
