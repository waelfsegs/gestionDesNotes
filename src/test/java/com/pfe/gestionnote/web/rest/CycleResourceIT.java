package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Cycle;
import com.pfe.gestionnote.repository.CycleRepository;
import com.pfe.gestionnote.service.CycleService;
import com.pfe.gestionnote.service.dto.CycleDTO;
import com.pfe.gestionnote.service.mapper.CycleMapper;
import com.pfe.gestionnote.service.dto.CycleCriteria;
import com.pfe.gestionnote.service.CycleQueryService;

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
 * Integration tests for the {@link CycleResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CycleResourceIT {

    private static final String DEFAULT_NOMCYCLE = "AAAAAAAAAA";
    private static final String UPDATED_NOMCYCLE = "BBBBBBBBBB";

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private CycleMapper cycleMapper;

    @Autowired
    private CycleService cycleService;

    @Autowired
    private CycleQueryService cycleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCycleMockMvc;

    private Cycle cycle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cycle createEntity(EntityManager em) {
        Cycle cycle = new Cycle()
            .nomcycle(DEFAULT_NOMCYCLE);
        return cycle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cycle createUpdatedEntity(EntityManager em) {
        Cycle cycle = new Cycle()
            .nomcycle(UPDATED_NOMCYCLE);
        return cycle;
    }

    @BeforeEach
    public void initTest() {
        cycle = createEntity(em);
    }

    @Test
    @Transactional
    public void createCycle() throws Exception {
        int databaseSizeBeforeCreate = cycleRepository.findAll().size();
        // Create the Cycle
        CycleDTO cycleDTO = cycleMapper.toDto(cycle);
        restCycleMockMvc.perform(post("/api/cycles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cycleDTO)))
            .andExpect(status().isCreated());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeCreate + 1);
        Cycle testCycle = cycleList.get(cycleList.size() - 1);
        assertThat(testCycle.getNomcycle()).isEqualTo(DEFAULT_NOMCYCLE);
    }

    @Test
    @Transactional
    public void createCycleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cycleRepository.findAll().size();

        // Create the Cycle with an existing ID
        cycle.setId(1L);
        CycleDTO cycleDTO = cycleMapper.toDto(cycle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCycleMockMvc.perform(post("/api/cycles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cycleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCycles() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get all the cycleList
        restCycleMockMvc.perform(get("/api/cycles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cycle.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomcycle").value(hasItem(DEFAULT_NOMCYCLE)));
    }
    
    @Test
    @Transactional
    public void getCycle() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get the cycle
        restCycleMockMvc.perform(get("/api/cycles/{id}", cycle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cycle.getId().intValue()))
            .andExpect(jsonPath("$.nomcycle").value(DEFAULT_NOMCYCLE));
    }


    @Test
    @Transactional
    public void getCyclesByIdFiltering() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        Long id = cycle.getId();

        defaultCycleShouldBeFound("id.equals=" + id);
        defaultCycleShouldNotBeFound("id.notEquals=" + id);

        defaultCycleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCycleShouldNotBeFound("id.greaterThan=" + id);

        defaultCycleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCycleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCyclesByNomcycleIsEqualToSomething() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get all the cycleList where nomcycle equals to DEFAULT_NOMCYCLE
        defaultCycleShouldBeFound("nomcycle.equals=" + DEFAULT_NOMCYCLE);

        // Get all the cycleList where nomcycle equals to UPDATED_NOMCYCLE
        defaultCycleShouldNotBeFound("nomcycle.equals=" + UPDATED_NOMCYCLE);
    }

    @Test
    @Transactional
    public void getAllCyclesByNomcycleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get all the cycleList where nomcycle not equals to DEFAULT_NOMCYCLE
        defaultCycleShouldNotBeFound("nomcycle.notEquals=" + DEFAULT_NOMCYCLE);

        // Get all the cycleList where nomcycle not equals to UPDATED_NOMCYCLE
        defaultCycleShouldBeFound("nomcycle.notEquals=" + UPDATED_NOMCYCLE);
    }

    @Test
    @Transactional
    public void getAllCyclesByNomcycleIsInShouldWork() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get all the cycleList where nomcycle in DEFAULT_NOMCYCLE or UPDATED_NOMCYCLE
        defaultCycleShouldBeFound("nomcycle.in=" + DEFAULT_NOMCYCLE + "," + UPDATED_NOMCYCLE);

        // Get all the cycleList where nomcycle equals to UPDATED_NOMCYCLE
        defaultCycleShouldNotBeFound("nomcycle.in=" + UPDATED_NOMCYCLE);
    }

    @Test
    @Transactional
    public void getAllCyclesByNomcycleIsNullOrNotNull() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get all the cycleList where nomcycle is not null
        defaultCycleShouldBeFound("nomcycle.specified=true");

        // Get all the cycleList where nomcycle is null
        defaultCycleShouldNotBeFound("nomcycle.specified=false");
    }
                @Test
    @Transactional
    public void getAllCyclesByNomcycleContainsSomething() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get all the cycleList where nomcycle contains DEFAULT_NOMCYCLE
        defaultCycleShouldBeFound("nomcycle.contains=" + DEFAULT_NOMCYCLE);

        // Get all the cycleList where nomcycle contains UPDATED_NOMCYCLE
        defaultCycleShouldNotBeFound("nomcycle.contains=" + UPDATED_NOMCYCLE);
    }

    @Test
    @Transactional
    public void getAllCyclesByNomcycleNotContainsSomething() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        // Get all the cycleList where nomcycle does not contain DEFAULT_NOMCYCLE
        defaultCycleShouldNotBeFound("nomcycle.doesNotContain=" + DEFAULT_NOMCYCLE);

        // Get all the cycleList where nomcycle does not contain UPDATED_NOMCYCLE
        defaultCycleShouldBeFound("nomcycle.doesNotContain=" + UPDATED_NOMCYCLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCycleShouldBeFound(String filter) throws Exception {
        restCycleMockMvc.perform(get("/api/cycles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cycle.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomcycle").value(hasItem(DEFAULT_NOMCYCLE)));

        // Check, that the count call also returns 1
        restCycleMockMvc.perform(get("/api/cycles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCycleShouldNotBeFound(String filter) throws Exception {
        restCycleMockMvc.perform(get("/api/cycles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCycleMockMvc.perform(get("/api/cycles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCycle() throws Exception {
        // Get the cycle
        restCycleMockMvc.perform(get("/api/cycles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCycle() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        int databaseSizeBeforeUpdate = cycleRepository.findAll().size();

        // Update the cycle
        Cycle updatedCycle = cycleRepository.findById(cycle.getId()).get();
        // Disconnect from session so that the updates on updatedCycle are not directly saved in db
        em.detach(updatedCycle);
        updatedCycle
            .nomcycle(UPDATED_NOMCYCLE);
        CycleDTO cycleDTO = cycleMapper.toDto(updatedCycle);

        restCycleMockMvc.perform(put("/api/cycles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cycleDTO)))
            .andExpect(status().isOk());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeUpdate);
        Cycle testCycle = cycleList.get(cycleList.size() - 1);
        assertThat(testCycle.getNomcycle()).isEqualTo(UPDATED_NOMCYCLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCycle() throws Exception {
        int databaseSizeBeforeUpdate = cycleRepository.findAll().size();

        // Create the Cycle
        CycleDTO cycleDTO = cycleMapper.toDto(cycle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCycleMockMvc.perform(put("/api/cycles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cycleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cycle in the database
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCycle() throws Exception {
        // Initialize the database
        cycleRepository.saveAndFlush(cycle);

        int databaseSizeBeforeDelete = cycleRepository.findAll().size();

        // Delete the cycle
        restCycleMockMvc.perform(delete("/api/cycles/{id}", cycle.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cycle> cycleList = cycleRepository.findAll();
        assertThat(cycleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
