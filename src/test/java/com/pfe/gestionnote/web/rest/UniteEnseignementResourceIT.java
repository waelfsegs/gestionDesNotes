package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.UniteEnseignement;
import com.pfe.gestionnote.repository.UniteEnseignementRepository;
import com.pfe.gestionnote.service.UniteEnseignementService;
import com.pfe.gestionnote.service.dto.UniteEnseignementDTO;
import com.pfe.gestionnote.service.mapper.UniteEnseignementMapper;
import com.pfe.gestionnote.service.dto.UniteEnseignementCriteria;
import com.pfe.gestionnote.service.UniteEnseignementQueryService;

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
 * Integration tests for the {@link UniteEnseignementResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UniteEnseignementResourceIT {

    private static final String DEFAULT_NOM_UE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_UE = "BBBBBBBBBB";

    private static final String DEFAULT_DESGNATION_UE = "AAAAAAAAAA";
    private static final String UPDATED_DESGNATION_UE = "BBBBBBBBBB";

    private static final Integer DEFAULT_COEFFICIENT_UE = 1;
    private static final Integer UPDATED_COEFFICIENT_UE = 2;
    private static final Integer SMALLER_COEFFICIENT_UE = 1 - 1;

    @Autowired
    private UniteEnseignementRepository uniteEnseignementRepository;

    @Autowired
    private UniteEnseignementMapper uniteEnseignementMapper;

    @Autowired
    private UniteEnseignementService uniteEnseignementService;

    @Autowired
    private UniteEnseignementQueryService uniteEnseignementQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUniteEnseignementMockMvc;

    private UniteEnseignement uniteEnseignement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UniteEnseignement createEntity(EntityManager em) {
        UniteEnseignement uniteEnseignement = new UniteEnseignement()
            .nomUE(DEFAULT_NOM_UE)
            .desgnationUE(DEFAULT_DESGNATION_UE)
            .coefficientUE(DEFAULT_COEFFICIENT_UE);
        return uniteEnseignement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UniteEnseignement createUpdatedEntity(EntityManager em) {
        UniteEnseignement uniteEnseignement = new UniteEnseignement()
            .nomUE(UPDATED_NOM_UE)
            .desgnationUE(UPDATED_DESGNATION_UE)
            .coefficientUE(UPDATED_COEFFICIENT_UE);
        return uniteEnseignement;
    }

    @BeforeEach
    public void initTest() {
        uniteEnseignement = createEntity(em);
    }

    @Test
    @Transactional
    public void createUniteEnseignement() throws Exception {
        int databaseSizeBeforeCreate = uniteEnseignementRepository.findAll().size();
        // Create the UniteEnseignement
        UniteEnseignementDTO uniteEnseignementDTO = uniteEnseignementMapper.toDto(uniteEnseignement);
        restUniteEnseignementMockMvc.perform(post("/api/unite-enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uniteEnseignementDTO)))
            .andExpect(status().isCreated());

        // Validate the UniteEnseignement in the database
        List<UniteEnseignement> uniteEnseignementList = uniteEnseignementRepository.findAll();
        assertThat(uniteEnseignementList).hasSize(databaseSizeBeforeCreate + 1);
        UniteEnseignement testUniteEnseignement = uniteEnseignementList.get(uniteEnseignementList.size() - 1);
        assertThat(testUniteEnseignement.getNomUE()).isEqualTo(DEFAULT_NOM_UE);
        assertThat(testUniteEnseignement.getDesgnationUE()).isEqualTo(DEFAULT_DESGNATION_UE);
        assertThat(testUniteEnseignement.getCoefficientUE()).isEqualTo(DEFAULT_COEFFICIENT_UE);
    }

    @Test
    @Transactional
    public void createUniteEnseignementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uniteEnseignementRepository.findAll().size();

        // Create the UniteEnseignement with an existing ID
        uniteEnseignement.setId(1L);
        UniteEnseignementDTO uniteEnseignementDTO = uniteEnseignementMapper.toDto(uniteEnseignement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniteEnseignementMockMvc.perform(post("/api/unite-enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uniteEnseignementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UniteEnseignement in the database
        List<UniteEnseignement> uniteEnseignementList = uniteEnseignementRepository.findAll();
        assertThat(uniteEnseignementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUniteEnseignements() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList
        restUniteEnseignementMockMvc.perform(get("/api/unite-enseignements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uniteEnseignement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomUE").value(hasItem(DEFAULT_NOM_UE)))
            .andExpect(jsonPath("$.[*].desgnationUE").value(hasItem(DEFAULT_DESGNATION_UE)))
            .andExpect(jsonPath("$.[*].coefficientUE").value(hasItem(DEFAULT_COEFFICIENT_UE)));
    }
    
    @Test
    @Transactional
    public void getUniteEnseignement() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get the uniteEnseignement
        restUniteEnseignementMockMvc.perform(get("/api/unite-enseignements/{id}", uniteEnseignement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uniteEnseignement.getId().intValue()))
            .andExpect(jsonPath("$.nomUE").value(DEFAULT_NOM_UE))
            .andExpect(jsonPath("$.desgnationUE").value(DEFAULT_DESGNATION_UE))
            .andExpect(jsonPath("$.coefficientUE").value(DEFAULT_COEFFICIENT_UE));
    }


    @Test
    @Transactional
    public void getUniteEnseignementsByIdFiltering() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        Long id = uniteEnseignement.getId();

        defaultUniteEnseignementShouldBeFound("id.equals=" + id);
        defaultUniteEnseignementShouldNotBeFound("id.notEquals=" + id);

        defaultUniteEnseignementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUniteEnseignementShouldNotBeFound("id.greaterThan=" + id);

        defaultUniteEnseignementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUniteEnseignementShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUniteEnseignementsByNomUEIsEqualToSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where nomUE equals to DEFAULT_NOM_UE
        defaultUniteEnseignementShouldBeFound("nomUE.equals=" + DEFAULT_NOM_UE);

        // Get all the uniteEnseignementList where nomUE equals to UPDATED_NOM_UE
        defaultUniteEnseignementShouldNotBeFound("nomUE.equals=" + UPDATED_NOM_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByNomUEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where nomUE not equals to DEFAULT_NOM_UE
        defaultUniteEnseignementShouldNotBeFound("nomUE.notEquals=" + DEFAULT_NOM_UE);

        // Get all the uniteEnseignementList where nomUE not equals to UPDATED_NOM_UE
        defaultUniteEnseignementShouldBeFound("nomUE.notEquals=" + UPDATED_NOM_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByNomUEIsInShouldWork() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where nomUE in DEFAULT_NOM_UE or UPDATED_NOM_UE
        defaultUniteEnseignementShouldBeFound("nomUE.in=" + DEFAULT_NOM_UE + "," + UPDATED_NOM_UE);

        // Get all the uniteEnseignementList where nomUE equals to UPDATED_NOM_UE
        defaultUniteEnseignementShouldNotBeFound("nomUE.in=" + UPDATED_NOM_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByNomUEIsNullOrNotNull() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where nomUE is not null
        defaultUniteEnseignementShouldBeFound("nomUE.specified=true");

        // Get all the uniteEnseignementList where nomUE is null
        defaultUniteEnseignementShouldNotBeFound("nomUE.specified=false");
    }
                @Test
    @Transactional
    public void getAllUniteEnseignementsByNomUEContainsSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where nomUE contains DEFAULT_NOM_UE
        defaultUniteEnseignementShouldBeFound("nomUE.contains=" + DEFAULT_NOM_UE);

        // Get all the uniteEnseignementList where nomUE contains UPDATED_NOM_UE
        defaultUniteEnseignementShouldNotBeFound("nomUE.contains=" + UPDATED_NOM_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByNomUENotContainsSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where nomUE does not contain DEFAULT_NOM_UE
        defaultUniteEnseignementShouldNotBeFound("nomUE.doesNotContain=" + DEFAULT_NOM_UE);

        // Get all the uniteEnseignementList where nomUE does not contain UPDATED_NOM_UE
        defaultUniteEnseignementShouldBeFound("nomUE.doesNotContain=" + UPDATED_NOM_UE);
    }


    @Test
    @Transactional
    public void getAllUniteEnseignementsByDesgnationUEIsEqualToSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where desgnationUE equals to DEFAULT_DESGNATION_UE
        defaultUniteEnseignementShouldBeFound("desgnationUE.equals=" + DEFAULT_DESGNATION_UE);

        // Get all the uniteEnseignementList where desgnationUE equals to UPDATED_DESGNATION_UE
        defaultUniteEnseignementShouldNotBeFound("desgnationUE.equals=" + UPDATED_DESGNATION_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByDesgnationUEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where desgnationUE not equals to DEFAULT_DESGNATION_UE
        defaultUniteEnseignementShouldNotBeFound("desgnationUE.notEquals=" + DEFAULT_DESGNATION_UE);

        // Get all the uniteEnseignementList where desgnationUE not equals to UPDATED_DESGNATION_UE
        defaultUniteEnseignementShouldBeFound("desgnationUE.notEquals=" + UPDATED_DESGNATION_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByDesgnationUEIsInShouldWork() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where desgnationUE in DEFAULT_DESGNATION_UE or UPDATED_DESGNATION_UE
        defaultUniteEnseignementShouldBeFound("desgnationUE.in=" + DEFAULT_DESGNATION_UE + "," + UPDATED_DESGNATION_UE);

        // Get all the uniteEnseignementList where desgnationUE equals to UPDATED_DESGNATION_UE
        defaultUniteEnseignementShouldNotBeFound("desgnationUE.in=" + UPDATED_DESGNATION_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByDesgnationUEIsNullOrNotNull() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where desgnationUE is not null
        defaultUniteEnseignementShouldBeFound("desgnationUE.specified=true");

        // Get all the uniteEnseignementList where desgnationUE is null
        defaultUniteEnseignementShouldNotBeFound("desgnationUE.specified=false");
    }
                @Test
    @Transactional
    public void getAllUniteEnseignementsByDesgnationUEContainsSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where desgnationUE contains DEFAULT_DESGNATION_UE
        defaultUniteEnseignementShouldBeFound("desgnationUE.contains=" + DEFAULT_DESGNATION_UE);

        // Get all the uniteEnseignementList where desgnationUE contains UPDATED_DESGNATION_UE
        defaultUniteEnseignementShouldNotBeFound("desgnationUE.contains=" + UPDATED_DESGNATION_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByDesgnationUENotContainsSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where desgnationUE does not contain DEFAULT_DESGNATION_UE
        defaultUniteEnseignementShouldNotBeFound("desgnationUE.doesNotContain=" + DEFAULT_DESGNATION_UE);

        // Get all the uniteEnseignementList where desgnationUE does not contain UPDATED_DESGNATION_UE
        defaultUniteEnseignementShouldBeFound("desgnationUE.doesNotContain=" + UPDATED_DESGNATION_UE);
    }


    @Test
    @Transactional
    public void getAllUniteEnseignementsByCoefficientUEIsEqualToSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where coefficientUE equals to DEFAULT_COEFFICIENT_UE
        defaultUniteEnseignementShouldBeFound("coefficientUE.equals=" + DEFAULT_COEFFICIENT_UE);

        // Get all the uniteEnseignementList where coefficientUE equals to UPDATED_COEFFICIENT_UE
        defaultUniteEnseignementShouldNotBeFound("coefficientUE.equals=" + UPDATED_COEFFICIENT_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByCoefficientUEIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where coefficientUE not equals to DEFAULT_COEFFICIENT_UE
        defaultUniteEnseignementShouldNotBeFound("coefficientUE.notEquals=" + DEFAULT_COEFFICIENT_UE);

        // Get all the uniteEnseignementList where coefficientUE not equals to UPDATED_COEFFICIENT_UE
        defaultUniteEnseignementShouldBeFound("coefficientUE.notEquals=" + UPDATED_COEFFICIENT_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByCoefficientUEIsInShouldWork() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where coefficientUE in DEFAULT_COEFFICIENT_UE or UPDATED_COEFFICIENT_UE
        defaultUniteEnseignementShouldBeFound("coefficientUE.in=" + DEFAULT_COEFFICIENT_UE + "," + UPDATED_COEFFICIENT_UE);

        // Get all the uniteEnseignementList where coefficientUE equals to UPDATED_COEFFICIENT_UE
        defaultUniteEnseignementShouldNotBeFound("coefficientUE.in=" + UPDATED_COEFFICIENT_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByCoefficientUEIsNullOrNotNull() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where coefficientUE is not null
        defaultUniteEnseignementShouldBeFound("coefficientUE.specified=true");

        // Get all the uniteEnseignementList where coefficientUE is null
        defaultUniteEnseignementShouldNotBeFound("coefficientUE.specified=false");
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByCoefficientUEIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where coefficientUE is greater than or equal to DEFAULT_COEFFICIENT_UE
        defaultUniteEnseignementShouldBeFound("coefficientUE.greaterThanOrEqual=" + DEFAULT_COEFFICIENT_UE);

        // Get all the uniteEnseignementList where coefficientUE is greater than or equal to UPDATED_COEFFICIENT_UE
        defaultUniteEnseignementShouldNotBeFound("coefficientUE.greaterThanOrEqual=" + UPDATED_COEFFICIENT_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByCoefficientUEIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where coefficientUE is less than or equal to DEFAULT_COEFFICIENT_UE
        defaultUniteEnseignementShouldBeFound("coefficientUE.lessThanOrEqual=" + DEFAULT_COEFFICIENT_UE);

        // Get all the uniteEnseignementList where coefficientUE is less than or equal to SMALLER_COEFFICIENT_UE
        defaultUniteEnseignementShouldNotBeFound("coefficientUE.lessThanOrEqual=" + SMALLER_COEFFICIENT_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByCoefficientUEIsLessThanSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where coefficientUE is less than DEFAULT_COEFFICIENT_UE
        defaultUniteEnseignementShouldNotBeFound("coefficientUE.lessThan=" + DEFAULT_COEFFICIENT_UE);

        // Get all the uniteEnseignementList where coefficientUE is less than UPDATED_COEFFICIENT_UE
        defaultUniteEnseignementShouldBeFound("coefficientUE.lessThan=" + UPDATED_COEFFICIENT_UE);
    }

    @Test
    @Transactional
    public void getAllUniteEnseignementsByCoefficientUEIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        // Get all the uniteEnseignementList where coefficientUE is greater than DEFAULT_COEFFICIENT_UE
        defaultUniteEnseignementShouldNotBeFound("coefficientUE.greaterThan=" + DEFAULT_COEFFICIENT_UE);

        // Get all the uniteEnseignementList where coefficientUE is greater than SMALLER_COEFFICIENT_UE
        defaultUniteEnseignementShouldBeFound("coefficientUE.greaterThan=" + SMALLER_COEFFICIENT_UE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUniteEnseignementShouldBeFound(String filter) throws Exception {
        restUniteEnseignementMockMvc.perform(get("/api/unite-enseignements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uniteEnseignement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomUE").value(hasItem(DEFAULT_NOM_UE)))
            .andExpect(jsonPath("$.[*].desgnationUE").value(hasItem(DEFAULT_DESGNATION_UE)))
            .andExpect(jsonPath("$.[*].coefficientUE").value(hasItem(DEFAULT_COEFFICIENT_UE)));

        // Check, that the count call also returns 1
        restUniteEnseignementMockMvc.perform(get("/api/unite-enseignements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUniteEnseignementShouldNotBeFound(String filter) throws Exception {
        restUniteEnseignementMockMvc.perform(get("/api/unite-enseignements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUniteEnseignementMockMvc.perform(get("/api/unite-enseignements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUniteEnseignement() throws Exception {
        // Get the uniteEnseignement
        restUniteEnseignementMockMvc.perform(get("/api/unite-enseignements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUniteEnseignement() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        int databaseSizeBeforeUpdate = uniteEnseignementRepository.findAll().size();

        // Update the uniteEnseignement
        UniteEnseignement updatedUniteEnseignement = uniteEnseignementRepository.findById(uniteEnseignement.getId()).get();
        // Disconnect from session so that the updates on updatedUniteEnseignement are not directly saved in db
        em.detach(updatedUniteEnseignement);
        updatedUniteEnseignement
            .nomUE(UPDATED_NOM_UE)
            .desgnationUE(UPDATED_DESGNATION_UE)
            .coefficientUE(UPDATED_COEFFICIENT_UE);
        UniteEnseignementDTO uniteEnseignementDTO = uniteEnseignementMapper.toDto(updatedUniteEnseignement);

        restUniteEnseignementMockMvc.perform(put("/api/unite-enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uniteEnseignementDTO)))
            .andExpect(status().isOk());

        // Validate the UniteEnseignement in the database
        List<UniteEnseignement> uniteEnseignementList = uniteEnseignementRepository.findAll();
        assertThat(uniteEnseignementList).hasSize(databaseSizeBeforeUpdate);
        UniteEnseignement testUniteEnseignement = uniteEnseignementList.get(uniteEnseignementList.size() - 1);
        assertThat(testUniteEnseignement.getNomUE()).isEqualTo(UPDATED_NOM_UE);
        assertThat(testUniteEnseignement.getDesgnationUE()).isEqualTo(UPDATED_DESGNATION_UE);
        assertThat(testUniteEnseignement.getCoefficientUE()).isEqualTo(UPDATED_COEFFICIENT_UE);
    }

    @Test
    @Transactional
    public void updateNonExistingUniteEnseignement() throws Exception {
        int databaseSizeBeforeUpdate = uniteEnseignementRepository.findAll().size();

        // Create the UniteEnseignement
        UniteEnseignementDTO uniteEnseignementDTO = uniteEnseignementMapper.toDto(uniteEnseignement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniteEnseignementMockMvc.perform(put("/api/unite-enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uniteEnseignementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UniteEnseignement in the database
        List<UniteEnseignement> uniteEnseignementList = uniteEnseignementRepository.findAll();
        assertThat(uniteEnseignementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUniteEnseignement() throws Exception {
        // Initialize the database
        uniteEnseignementRepository.saveAndFlush(uniteEnseignement);

        int databaseSizeBeforeDelete = uniteEnseignementRepository.findAll().size();

        // Delete the uniteEnseignement
        restUniteEnseignementMockMvc.perform(delete("/api/unite-enseignements/{id}", uniteEnseignement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UniteEnseignement> uniteEnseignementList = uniteEnseignementRepository.findAll();
        assertThat(uniteEnseignementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
