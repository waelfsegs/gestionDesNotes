package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Departement;
import com.pfe.gestionnote.repository.DepartementRepository;
import com.pfe.gestionnote.service.DepartementService;
import com.pfe.gestionnote.service.dto.DepartementDTO;
import com.pfe.gestionnote.service.mapper.DepartementMapper;
import com.pfe.gestionnote.service.dto.DepartementCriteria;
import com.pfe.gestionnote.service.DepartementQueryService;

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
 * Integration tests for the {@link DepartementResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DepartementResourceIT {

    private static final String DEFAULT_NOM_DEP = "AAAAAAAAAA";
    private static final String UPDATED_NOM_DEP = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNIATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNIATION = "BBBBBBBBBB";

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private DepartementMapper departementMapper;

    @Autowired
    private DepartementService departementService;

    @Autowired
    private DepartementQueryService departementQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartementMockMvc;

    private Departement departement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departement createEntity(EntityManager em) {
        Departement departement = new Departement()
            .nomDep(DEFAULT_NOM_DEP)
            .designiation(DEFAULT_DESIGNIATION);
        return departement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departement createUpdatedEntity(EntityManager em) {
        Departement departement = new Departement()
            .nomDep(UPDATED_NOM_DEP)
            .designiation(UPDATED_DESIGNIATION);
        return departement;
    }

    @BeforeEach
    public void initTest() {
        departement = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartement() throws Exception {
        int databaseSizeBeforeCreate = departementRepository.findAll().size();
        // Create the Departement
        DepartementDTO departementDTO = departementMapper.toDto(departement);
        restDepartementMockMvc.perform(post("/api/departements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departementDTO)))
            .andExpect(status().isCreated());

        // Validate the Departement in the database
        List<Departement> departementList = departementRepository.findAll();
        assertThat(departementList).hasSize(databaseSizeBeforeCreate + 1);
        Departement testDepartement = departementList.get(departementList.size() - 1);
        assertThat(testDepartement.getNomDep()).isEqualTo(DEFAULT_NOM_DEP);
        assertThat(testDepartement.getDesigniation()).isEqualTo(DEFAULT_DESIGNIATION);
    }

    @Test
    @Transactional
    public void createDepartementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departementRepository.findAll().size();

        // Create the Departement with an existing ID
        departement.setId(1L);
        DepartementDTO departementDTO = departementMapper.toDto(departement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartementMockMvc.perform(post("/api/departements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Departement in the database
        List<Departement> departementList = departementRepository.findAll();
        assertThat(departementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDepartements() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList
        restDepartementMockMvc.perform(get("/api/departements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomDep").value(hasItem(DEFAULT_NOM_DEP)))
            .andExpect(jsonPath("$.[*].designiation").value(hasItem(DEFAULT_DESIGNIATION)));
    }
    
    @Test
    @Transactional
    public void getDepartement() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get the departement
        restDepartementMockMvc.perform(get("/api/departements/{id}", departement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departement.getId().intValue()))
            .andExpect(jsonPath("$.nomDep").value(DEFAULT_NOM_DEP))
            .andExpect(jsonPath("$.designiation").value(DEFAULT_DESIGNIATION));
    }


    @Test
    @Transactional
    public void getDepartementsByIdFiltering() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        Long id = departement.getId();

        defaultDepartementShouldBeFound("id.equals=" + id);
        defaultDepartementShouldNotBeFound("id.notEquals=" + id);

        defaultDepartementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDepartementShouldNotBeFound("id.greaterThan=" + id);

        defaultDepartementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDepartementShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDepartementsByNomDepIsEqualToSomething() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where nomDep equals to DEFAULT_NOM_DEP
        defaultDepartementShouldBeFound("nomDep.equals=" + DEFAULT_NOM_DEP);

        // Get all the departementList where nomDep equals to UPDATED_NOM_DEP
        defaultDepartementShouldNotBeFound("nomDep.equals=" + UPDATED_NOM_DEP);
    }

    @Test
    @Transactional
    public void getAllDepartementsByNomDepIsNotEqualToSomething() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where nomDep not equals to DEFAULT_NOM_DEP
        defaultDepartementShouldNotBeFound("nomDep.notEquals=" + DEFAULT_NOM_DEP);

        // Get all the departementList where nomDep not equals to UPDATED_NOM_DEP
        defaultDepartementShouldBeFound("nomDep.notEquals=" + UPDATED_NOM_DEP);
    }

    @Test
    @Transactional
    public void getAllDepartementsByNomDepIsInShouldWork() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where nomDep in DEFAULT_NOM_DEP or UPDATED_NOM_DEP
        defaultDepartementShouldBeFound("nomDep.in=" + DEFAULT_NOM_DEP + "," + UPDATED_NOM_DEP);

        // Get all the departementList where nomDep equals to UPDATED_NOM_DEP
        defaultDepartementShouldNotBeFound("nomDep.in=" + UPDATED_NOM_DEP);
    }

    @Test
    @Transactional
    public void getAllDepartementsByNomDepIsNullOrNotNull() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where nomDep is not null
        defaultDepartementShouldBeFound("nomDep.specified=true");

        // Get all the departementList where nomDep is null
        defaultDepartementShouldNotBeFound("nomDep.specified=false");
    }
                @Test
    @Transactional
    public void getAllDepartementsByNomDepContainsSomething() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where nomDep contains DEFAULT_NOM_DEP
        defaultDepartementShouldBeFound("nomDep.contains=" + DEFAULT_NOM_DEP);

        // Get all the departementList where nomDep contains UPDATED_NOM_DEP
        defaultDepartementShouldNotBeFound("nomDep.contains=" + UPDATED_NOM_DEP);
    }

    @Test
    @Transactional
    public void getAllDepartementsByNomDepNotContainsSomething() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where nomDep does not contain DEFAULT_NOM_DEP
        defaultDepartementShouldNotBeFound("nomDep.doesNotContain=" + DEFAULT_NOM_DEP);

        // Get all the departementList where nomDep does not contain UPDATED_NOM_DEP
        defaultDepartementShouldBeFound("nomDep.doesNotContain=" + UPDATED_NOM_DEP);
    }


    @Test
    @Transactional
    public void getAllDepartementsByDesigniationIsEqualToSomething() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where designiation equals to DEFAULT_DESIGNIATION
        defaultDepartementShouldBeFound("designiation.equals=" + DEFAULT_DESIGNIATION);

        // Get all the departementList where designiation equals to UPDATED_DESIGNIATION
        defaultDepartementShouldNotBeFound("designiation.equals=" + UPDATED_DESIGNIATION);
    }

    @Test
    @Transactional
    public void getAllDepartementsByDesigniationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where designiation not equals to DEFAULT_DESIGNIATION
        defaultDepartementShouldNotBeFound("designiation.notEquals=" + DEFAULT_DESIGNIATION);

        // Get all the departementList where designiation not equals to UPDATED_DESIGNIATION
        defaultDepartementShouldBeFound("designiation.notEquals=" + UPDATED_DESIGNIATION);
    }

    @Test
    @Transactional
    public void getAllDepartementsByDesigniationIsInShouldWork() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where designiation in DEFAULT_DESIGNIATION or UPDATED_DESIGNIATION
        defaultDepartementShouldBeFound("designiation.in=" + DEFAULT_DESIGNIATION + "," + UPDATED_DESIGNIATION);

        // Get all the departementList where designiation equals to UPDATED_DESIGNIATION
        defaultDepartementShouldNotBeFound("designiation.in=" + UPDATED_DESIGNIATION);
    }

    @Test
    @Transactional
    public void getAllDepartementsByDesigniationIsNullOrNotNull() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where designiation is not null
        defaultDepartementShouldBeFound("designiation.specified=true");

        // Get all the departementList where designiation is null
        defaultDepartementShouldNotBeFound("designiation.specified=false");
    }
                @Test
    @Transactional
    public void getAllDepartementsByDesigniationContainsSomething() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where designiation contains DEFAULT_DESIGNIATION
        defaultDepartementShouldBeFound("designiation.contains=" + DEFAULT_DESIGNIATION);

        // Get all the departementList where designiation contains UPDATED_DESIGNIATION
        defaultDepartementShouldNotBeFound("designiation.contains=" + UPDATED_DESIGNIATION);
    }

    @Test
    @Transactional
    public void getAllDepartementsByDesigniationNotContainsSomething() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        // Get all the departementList where designiation does not contain DEFAULT_DESIGNIATION
        defaultDepartementShouldNotBeFound("designiation.doesNotContain=" + DEFAULT_DESIGNIATION);

        // Get all the departementList where designiation does not contain UPDATED_DESIGNIATION
        defaultDepartementShouldBeFound("designiation.doesNotContain=" + UPDATED_DESIGNIATION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepartementShouldBeFound(String filter) throws Exception {
        restDepartementMockMvc.perform(get("/api/departements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departement.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomDep").value(hasItem(DEFAULT_NOM_DEP)))
            .andExpect(jsonPath("$.[*].designiation").value(hasItem(DEFAULT_DESIGNIATION)));

        // Check, that the count call also returns 1
        restDepartementMockMvc.perform(get("/api/departements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepartementShouldNotBeFound(String filter) throws Exception {
        restDepartementMockMvc.perform(get("/api/departements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepartementMockMvc.perform(get("/api/departements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDepartement() throws Exception {
        // Get the departement
        restDepartementMockMvc.perform(get("/api/departements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartement() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        int databaseSizeBeforeUpdate = departementRepository.findAll().size();

        // Update the departement
        Departement updatedDepartement = departementRepository.findById(departement.getId()).get();
        // Disconnect from session so that the updates on updatedDepartement are not directly saved in db
        em.detach(updatedDepartement);
        updatedDepartement
            .nomDep(UPDATED_NOM_DEP)
            .designiation(UPDATED_DESIGNIATION);
        DepartementDTO departementDTO = departementMapper.toDto(updatedDepartement);

        restDepartementMockMvc.perform(put("/api/departements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departementDTO)))
            .andExpect(status().isOk());

        // Validate the Departement in the database
        List<Departement> departementList = departementRepository.findAll();
        assertThat(departementList).hasSize(databaseSizeBeforeUpdate);
        Departement testDepartement = departementList.get(departementList.size() - 1);
        assertThat(testDepartement.getNomDep()).isEqualTo(UPDATED_NOM_DEP);
        assertThat(testDepartement.getDesigniation()).isEqualTo(UPDATED_DESIGNIATION);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartement() throws Exception {
        int databaseSizeBeforeUpdate = departementRepository.findAll().size();

        // Create the Departement
        DepartementDTO departementDTO = departementMapper.toDto(departement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartementMockMvc.perform(put("/api/departements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Departement in the database
        List<Departement> departementList = departementRepository.findAll();
        assertThat(departementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartement() throws Exception {
        // Initialize the database
        departementRepository.saveAndFlush(departement);

        int databaseSizeBeforeDelete = departementRepository.findAll().size();

        // Delete the departement
        restDepartementMockMvc.perform(delete("/api/departements/{id}", departement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Departement> departementList = departementRepository.findAll();
        assertThat(departementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
