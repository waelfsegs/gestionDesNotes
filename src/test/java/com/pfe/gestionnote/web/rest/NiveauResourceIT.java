package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Niveau;
import com.pfe.gestionnote.domain.Cycle;
import com.pfe.gestionnote.repository.NiveauRepository;
import com.pfe.gestionnote.service.NiveauService;
import com.pfe.gestionnote.service.dto.NiveauDTO;
import com.pfe.gestionnote.service.mapper.NiveauMapper;
import com.pfe.gestionnote.service.dto.NiveauCriteria;
import com.pfe.gestionnote.service.NiveauQueryService;

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
 * Integration tests for the {@link NiveauResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NiveauResourceIT {

    private static final String DEFAULT_NIVEAU = "AAAAAAAAAA";
    private static final String UPDATED_NIVEAU = "BBBBBBBBBB";

    @Autowired
    private NiveauRepository niveauRepository;

    @Autowired
    private NiveauMapper niveauMapper;

    @Autowired
    private NiveauService niveauService;

    @Autowired
    private NiveauQueryService niveauQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNiveauMockMvc;

    private Niveau niveau;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Niveau createEntity(EntityManager em) {
        Niveau niveau = new Niveau()
            .niveau(DEFAULT_NIVEAU);
        return niveau;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Niveau createUpdatedEntity(EntityManager em) {
        Niveau niveau = new Niveau()
            .niveau(UPDATED_NIVEAU);
        return niveau;
    }

    @BeforeEach
    public void initTest() {
        niveau = createEntity(em);
    }

    @Test
    @Transactional
    public void createNiveau() throws Exception {
        int databaseSizeBeforeCreate = niveauRepository.findAll().size();
        // Create the Niveau
        NiveauDTO niveauDTO = niveauMapper.toDto(niveau);
        restNiveauMockMvc.perform(post("/api/niveaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isCreated());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeCreate + 1);
        Niveau testNiveau = niveauList.get(niveauList.size() - 1);
        assertThat(testNiveau.getNiveau()).isEqualTo(DEFAULT_NIVEAU);
    }

    @Test
    @Transactional
    public void createNiveauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = niveauRepository.findAll().size();

        // Create the Niveau with an existing ID
        niveau.setId(1L);
        NiveauDTO niveauDTO = niveauMapper.toDto(niveau);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNiveauMockMvc.perform(post("/api/niveaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNiveaus() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList
        restNiveauMockMvc.perform(get("/api/niveaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niveau.getId().intValue())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU)));
    }
    
    @Test
    @Transactional
    public void getNiveau() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get the niveau
        restNiveauMockMvc.perform(get("/api/niveaus/{id}", niveau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(niveau.getId().intValue()))
            .andExpect(jsonPath("$.niveau").value(DEFAULT_NIVEAU));
    }


    @Test
    @Transactional
    public void getNiveausByIdFiltering() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        Long id = niveau.getId();

        defaultNiveauShouldBeFound("id.equals=" + id);
        defaultNiveauShouldNotBeFound("id.notEquals=" + id);

        defaultNiveauShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNiveauShouldNotBeFound("id.greaterThan=" + id);

        defaultNiveauShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNiveauShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNiveausByNiveauIsEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where niveau equals to DEFAULT_NIVEAU
        defaultNiveauShouldBeFound("niveau.equals=" + DEFAULT_NIVEAU);

        // Get all the niveauList where niveau equals to UPDATED_NIVEAU
        defaultNiveauShouldNotBeFound("niveau.equals=" + UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    public void getAllNiveausByNiveauIsNotEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where niveau not equals to DEFAULT_NIVEAU
        defaultNiveauShouldNotBeFound("niveau.notEquals=" + DEFAULT_NIVEAU);

        // Get all the niveauList where niveau not equals to UPDATED_NIVEAU
        defaultNiveauShouldBeFound("niveau.notEquals=" + UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    public void getAllNiveausByNiveauIsInShouldWork() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where niveau in DEFAULT_NIVEAU or UPDATED_NIVEAU
        defaultNiveauShouldBeFound("niveau.in=" + DEFAULT_NIVEAU + "," + UPDATED_NIVEAU);

        // Get all the niveauList where niveau equals to UPDATED_NIVEAU
        defaultNiveauShouldNotBeFound("niveau.in=" + UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    public void getAllNiveausByNiveauIsNullOrNotNull() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where niveau is not null
        defaultNiveauShouldBeFound("niveau.specified=true");

        // Get all the niveauList where niveau is null
        defaultNiveauShouldNotBeFound("niveau.specified=false");
    }
                @Test
    @Transactional
    public void getAllNiveausByNiveauContainsSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where niveau contains DEFAULT_NIVEAU
        defaultNiveauShouldBeFound("niveau.contains=" + DEFAULT_NIVEAU);

        // Get all the niveauList where niveau contains UPDATED_NIVEAU
        defaultNiveauShouldNotBeFound("niveau.contains=" + UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    public void getAllNiveausByNiveauNotContainsSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where niveau does not contain DEFAULT_NIVEAU
        defaultNiveauShouldNotBeFound("niveau.doesNotContain=" + DEFAULT_NIVEAU);

        // Get all the niveauList where niveau does not contain UPDATED_NIVEAU
        defaultNiveauShouldBeFound("niveau.doesNotContain=" + UPDATED_NIVEAU);
    }


    @Test
    @Transactional
    public void getAllNiveausByCycleIsEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);
        Cycle cycle = CycleResourceIT.createEntity(em);
        em.persist(cycle);
        em.flush();
        niveau.setCycle(cycle);
        niveauRepository.saveAndFlush(niveau);
        Long cycleId = cycle.getId();

        // Get all the niveauList where cycle equals to cycleId
        defaultNiveauShouldBeFound("cycleId.equals=" + cycleId);

        // Get all the niveauList where cycle equals to cycleId + 1
        defaultNiveauShouldNotBeFound("cycleId.equals=" + (cycleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNiveauShouldBeFound(String filter) throws Exception {
        restNiveauMockMvc.perform(get("/api/niveaus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niveau.getId().intValue())))
            .andExpect(jsonPath("$.[*].niveau").value(hasItem(DEFAULT_NIVEAU)));

        // Check, that the count call also returns 1
        restNiveauMockMvc.perform(get("/api/niveaus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNiveauShouldNotBeFound(String filter) throws Exception {
        restNiveauMockMvc.perform(get("/api/niveaus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNiveauMockMvc.perform(get("/api/niveaus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNiveau() throws Exception {
        // Get the niveau
        restNiveauMockMvc.perform(get("/api/niveaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNiveau() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        int databaseSizeBeforeUpdate = niveauRepository.findAll().size();

        // Update the niveau
        Niveau updatedNiveau = niveauRepository.findById(niveau.getId()).get();
        // Disconnect from session so that the updates on updatedNiveau are not directly saved in db
        em.detach(updatedNiveau);
        updatedNiveau
            .niveau(UPDATED_NIVEAU);
        NiveauDTO niveauDTO = niveauMapper.toDto(updatedNiveau);

        restNiveauMockMvc.perform(put("/api/niveaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isOk());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeUpdate);
        Niveau testNiveau = niveauList.get(niveauList.size() - 1);
        assertThat(testNiveau.getNiveau()).isEqualTo(UPDATED_NIVEAU);
    }

    @Test
    @Transactional
    public void updateNonExistingNiveau() throws Exception {
        int databaseSizeBeforeUpdate = niveauRepository.findAll().size();

        // Create the Niveau
        NiveauDTO niveauDTO = niveauMapper.toDto(niveau);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNiveauMockMvc.perform(put("/api/niveaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNiveau() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        int databaseSizeBeforeDelete = niveauRepository.findAll().size();

        // Delete the niveau
        restNiveauMockMvc.perform(delete("/api/niveaus/{id}", niveau.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
