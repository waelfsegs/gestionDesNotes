package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.AffectationChef;
import com.pfe.gestionnote.domain.Departement;
import com.pfe.gestionnote.domain.Enseignant;
import com.pfe.gestionnote.repository.AffectationChefRepository;
import com.pfe.gestionnote.service.AffectationChefService;
import com.pfe.gestionnote.service.dto.AffectationChefDTO;
import com.pfe.gestionnote.service.mapper.AffectationChefMapper;
import com.pfe.gestionnote.service.dto.AffectationChefCriteria;
import com.pfe.gestionnote.service.AffectationChefQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AffectationChefResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AffectationChefResourceIT {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_START_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_END_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private AffectationChefRepository affectationChefRepository;

    @Autowired
    private AffectationChefMapper affectationChefMapper;

    @Autowired
    private AffectationChefService affectationChefService;

    @Autowired
    private AffectationChefQueryService affectationChefQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAffectationChefMockMvc;

    private AffectationChef affectationChef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AffectationChef createEntity(EntityManager em) {
        AffectationChef affectationChef = new AffectationChef()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return affectationChef;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AffectationChef createUpdatedEntity(EntityManager em) {
        AffectationChef affectationChef = new AffectationChef()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return affectationChef;
    }

    @BeforeEach
    public void initTest() {
        affectationChef = createEntity(em);
    }

    @Test
    @Transactional
    public void createAffectationChef() throws Exception {
        int databaseSizeBeforeCreate = affectationChefRepository.findAll().size();
        // Create the AffectationChef
        AffectationChefDTO affectationChefDTO = affectationChefMapper.toDto(affectationChef);
        restAffectationChefMockMvc.perform(post("/api/affectation-chefs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationChefDTO)))
            .andExpect(status().isCreated());

        // Validate the AffectationChef in the database
        List<AffectationChef> affectationChefList = affectationChefRepository.findAll();
        assertThat(affectationChefList).hasSize(databaseSizeBeforeCreate + 1);
        AffectationChef testAffectationChef = affectationChefList.get(affectationChefList.size() - 1);
        assertThat(testAffectationChef.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testAffectationChef.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createAffectationChefWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = affectationChefRepository.findAll().size();

        // Create the AffectationChef with an existing ID
        affectationChef.setId(1L);
        AffectationChefDTO affectationChefDTO = affectationChefMapper.toDto(affectationChef);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffectationChefMockMvc.perform(post("/api/affectation-chefs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationChefDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AffectationChef in the database
        List<AffectationChef> affectationChefList = affectationChefRepository.findAll();
        assertThat(affectationChefList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAffectationChefs() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList
        restAffectationChefMockMvc.perform(get("/api/affectation-chefs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affectationChef.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAffectationChef() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get the affectationChef
        restAffectationChefMockMvc.perform(get("/api/affectation-chefs/{id}", affectationChef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(affectationChef.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }


    @Test
    @Transactional
    public void getAffectationChefsByIdFiltering() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        Long id = affectationChef.getId();

        defaultAffectationChefShouldBeFound("id.equals=" + id);
        defaultAffectationChefShouldNotBeFound("id.notEquals=" + id);

        defaultAffectationChefShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAffectationChefShouldNotBeFound("id.greaterThan=" + id);

        defaultAffectationChefShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAffectationChefShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAffectationChefsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where startDate equals to DEFAULT_START_DATE
        defaultAffectationChefShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the affectationChefList where startDate equals to UPDATED_START_DATE
        defaultAffectationChefShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where startDate not equals to DEFAULT_START_DATE
        defaultAffectationChefShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the affectationChefList where startDate not equals to UPDATED_START_DATE
        defaultAffectationChefShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultAffectationChefShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the affectationChefList where startDate equals to UPDATED_START_DATE
        defaultAffectationChefShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where startDate is not null
        defaultAffectationChefShouldBeFound("startDate.specified=true");

        // Get all the affectationChefList where startDate is null
        defaultAffectationChefShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where startDate is greater than or equal to DEFAULT_START_DATE
        defaultAffectationChefShouldBeFound("startDate.greaterThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the affectationChefList where startDate is greater than or equal to UPDATED_START_DATE
        defaultAffectationChefShouldNotBeFound("startDate.greaterThanOrEqual=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByStartDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where startDate is less than or equal to DEFAULT_START_DATE
        defaultAffectationChefShouldBeFound("startDate.lessThanOrEqual=" + DEFAULT_START_DATE);

        // Get all the affectationChefList where startDate is less than or equal to SMALLER_START_DATE
        defaultAffectationChefShouldNotBeFound("startDate.lessThanOrEqual=" + SMALLER_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where startDate is less than DEFAULT_START_DATE
        defaultAffectationChefShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the affectationChefList where startDate is less than UPDATED_START_DATE
        defaultAffectationChefShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByStartDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where startDate is greater than DEFAULT_START_DATE
        defaultAffectationChefShouldNotBeFound("startDate.greaterThan=" + DEFAULT_START_DATE);

        // Get all the affectationChefList where startDate is greater than SMALLER_START_DATE
        defaultAffectationChefShouldBeFound("startDate.greaterThan=" + SMALLER_START_DATE);
    }


    @Test
    @Transactional
    public void getAllAffectationChefsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where endDate equals to DEFAULT_END_DATE
        defaultAffectationChefShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the affectationChefList where endDate equals to UPDATED_END_DATE
        defaultAffectationChefShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where endDate not equals to DEFAULT_END_DATE
        defaultAffectationChefShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the affectationChefList where endDate not equals to UPDATED_END_DATE
        defaultAffectationChefShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultAffectationChefShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the affectationChefList where endDate equals to UPDATED_END_DATE
        defaultAffectationChefShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where endDate is not null
        defaultAffectationChefShouldBeFound("endDate.specified=true");

        // Get all the affectationChefList where endDate is null
        defaultAffectationChefShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where endDate is greater than or equal to DEFAULT_END_DATE
        defaultAffectationChefShouldBeFound("endDate.greaterThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the affectationChefList where endDate is greater than or equal to UPDATED_END_DATE
        defaultAffectationChefShouldNotBeFound("endDate.greaterThanOrEqual=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByEndDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where endDate is less than or equal to DEFAULT_END_DATE
        defaultAffectationChefShouldBeFound("endDate.lessThanOrEqual=" + DEFAULT_END_DATE);

        // Get all the affectationChefList where endDate is less than or equal to SMALLER_END_DATE
        defaultAffectationChefShouldNotBeFound("endDate.lessThanOrEqual=" + SMALLER_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where endDate is less than DEFAULT_END_DATE
        defaultAffectationChefShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the affectationChefList where endDate is less than UPDATED_END_DATE
        defaultAffectationChefShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAffectationChefsByEndDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        // Get all the affectationChefList where endDate is greater than DEFAULT_END_DATE
        defaultAffectationChefShouldNotBeFound("endDate.greaterThan=" + DEFAULT_END_DATE);

        // Get all the affectationChefList where endDate is greater than SMALLER_END_DATE
        defaultAffectationChefShouldBeFound("endDate.greaterThan=" + SMALLER_END_DATE);
    }


    @Test
    @Transactional
    public void getAllAffectationChefsByDepartementIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);
        Departement departement = DepartementResourceIT.createEntity(em);
        em.persist(departement);
        em.flush();
        affectationChef.setDepartement(departement);
        affectationChefRepository.saveAndFlush(affectationChef);
        Long departementId = departement.getId();

        // Get all the affectationChefList where departement equals to departementId
        defaultAffectationChefShouldBeFound("departementId.equals=" + departementId);

        // Get all the affectationChefList where departement equals to departementId + 1
        defaultAffectationChefShouldNotBeFound("departementId.equals=" + (departementId + 1));
    }


    @Test
    @Transactional
    public void getAllAffectationChefsByEnseignantIsEqualToSomething() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);
        Enseignant enseignant = EnseignantResourceIT.createEntity(em);
        em.persist(enseignant);
        em.flush();
        affectationChef.setEnseignant(enseignant);
        affectationChefRepository.saveAndFlush(affectationChef);
        Long enseignantId = enseignant.getId();

        // Get all the affectationChefList where enseignant equals to enseignantId
        defaultAffectationChefShouldBeFound("enseignantId.equals=" + enseignantId);

        // Get all the affectationChefList where enseignant equals to enseignantId + 1
        defaultAffectationChefShouldNotBeFound("enseignantId.equals=" + (enseignantId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAffectationChefShouldBeFound(String filter) throws Exception {
        restAffectationChefMockMvc.perform(get("/api/affectation-chefs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affectationChef.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));

        // Check, that the count call also returns 1
        restAffectationChefMockMvc.perform(get("/api/affectation-chefs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAffectationChefShouldNotBeFound(String filter) throws Exception {
        restAffectationChefMockMvc.perform(get("/api/affectation-chefs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAffectationChefMockMvc.perform(get("/api/affectation-chefs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAffectationChef() throws Exception {
        // Get the affectationChef
        restAffectationChefMockMvc.perform(get("/api/affectation-chefs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAffectationChef() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        int databaseSizeBeforeUpdate = affectationChefRepository.findAll().size();

        // Update the affectationChef
        AffectationChef updatedAffectationChef = affectationChefRepository.findById(affectationChef.getId()).get();
        // Disconnect from session so that the updates on updatedAffectationChef are not directly saved in db
        em.detach(updatedAffectationChef);
        updatedAffectationChef
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        AffectationChefDTO affectationChefDTO = affectationChefMapper.toDto(updatedAffectationChef);

        restAffectationChefMockMvc.perform(put("/api/affectation-chefs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationChefDTO)))
            .andExpect(status().isOk());

        // Validate the AffectationChef in the database
        List<AffectationChef> affectationChefList = affectationChefRepository.findAll();
        assertThat(affectationChefList).hasSize(databaseSizeBeforeUpdate);
        AffectationChef testAffectationChef = affectationChefList.get(affectationChefList.size() - 1);
        assertThat(testAffectationChef.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testAffectationChef.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAffectationChef() throws Exception {
        int databaseSizeBeforeUpdate = affectationChefRepository.findAll().size();

        // Create the AffectationChef
        AffectationChefDTO affectationChefDTO = affectationChefMapper.toDto(affectationChef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffectationChefMockMvc.perform(put("/api/affectation-chefs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(affectationChefDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AffectationChef in the database
        List<AffectationChef> affectationChefList = affectationChefRepository.findAll();
        assertThat(affectationChefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAffectationChef() throws Exception {
        // Initialize the database
        affectationChefRepository.saveAndFlush(affectationChef);

        int databaseSizeBeforeDelete = affectationChefRepository.findAll().size();

        // Delete the affectationChef
        restAffectationChefMockMvc.perform(delete("/api/affectation-chefs/{id}", affectationChef.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AffectationChef> affectationChefList = affectationChefRepository.findAll();
        assertThat(affectationChefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
