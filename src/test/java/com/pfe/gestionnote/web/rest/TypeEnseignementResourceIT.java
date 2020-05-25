package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.TypeEnseignement;
import com.pfe.gestionnote.repository.TypeEnseignementRepository;
import com.pfe.gestionnote.service.TypeEnseignementService;
import com.pfe.gestionnote.service.dto.TypeEnseignementDTO;
import com.pfe.gestionnote.service.mapper.TypeEnseignementMapper;
import com.pfe.gestionnote.service.dto.TypeEnseignementCriteria;
import com.pfe.gestionnote.service.TypeEnseignementQueryService;

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
 * Integration tests for the {@link TypeEnseignementResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeEnseignementResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private TypeEnseignementRepository typeEnseignementRepository;

    @Autowired
    private TypeEnseignementMapper typeEnseignementMapper;

    @Autowired
    private TypeEnseignementService typeEnseignementService;

    @Autowired
    private TypeEnseignementQueryService typeEnseignementQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeEnseignementMockMvc;

    private TypeEnseignement typeEnseignement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeEnseignement createEntity(EntityManager em) {
        TypeEnseignement typeEnseignement = new TypeEnseignement()
            .type(DEFAULT_TYPE);
        return typeEnseignement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeEnseignement createUpdatedEntity(EntityManager em) {
        TypeEnseignement typeEnseignement = new TypeEnseignement()
            .type(UPDATED_TYPE);
        return typeEnseignement;
    }

    @BeforeEach
    public void initTest() {
        typeEnseignement = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeEnseignement() throws Exception {
        int databaseSizeBeforeCreate = typeEnseignementRepository.findAll().size();
        // Create the TypeEnseignement
        TypeEnseignementDTO typeEnseignementDTO = typeEnseignementMapper.toDto(typeEnseignement);
        restTypeEnseignementMockMvc.perform(post("/api/type-enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeEnseignementDTO)))
            .andExpect(status().isCreated());

        // Validate the TypeEnseignement in the database
        List<TypeEnseignement> typeEnseignementList = typeEnseignementRepository.findAll();
        assertThat(typeEnseignementList).hasSize(databaseSizeBeforeCreate + 1);
        TypeEnseignement testTypeEnseignement = typeEnseignementList.get(typeEnseignementList.size() - 1);
        assertThat(testTypeEnseignement.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createTypeEnseignementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeEnseignementRepository.findAll().size();

        // Create the TypeEnseignement with an existing ID
        typeEnseignement.setId(1L);
        TypeEnseignementDTO typeEnseignementDTO = typeEnseignementMapper.toDto(typeEnseignement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeEnseignementMockMvc.perform(post("/api/type-enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeEnseignementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeEnseignement in the database
        List<TypeEnseignement> typeEnseignementList = typeEnseignementRepository.findAll();
        assertThat(typeEnseignementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeEnseignements() throws Exception {
        // Initialize the database
        typeEnseignementRepository.saveAndFlush(typeEnseignement);

        // Get all the typeEnseignementList
        restTypeEnseignementMockMvc.perform(get("/api/type-enseignements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeEnseignement.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getTypeEnseignement() throws Exception {
        // Initialize the database
        typeEnseignementRepository.saveAndFlush(typeEnseignement);

        // Get the typeEnseignement
        restTypeEnseignementMockMvc.perform(get("/api/type-enseignements/{id}", typeEnseignement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeEnseignement.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }


    @Test
    @Transactional
    public void getTypeEnseignementsByIdFiltering() throws Exception {
        // Initialize the database
        typeEnseignementRepository.saveAndFlush(typeEnseignement);

        Long id = typeEnseignement.getId();

        defaultTypeEnseignementShouldBeFound("id.equals=" + id);
        defaultTypeEnseignementShouldNotBeFound("id.notEquals=" + id);

        defaultTypeEnseignementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTypeEnseignementShouldNotBeFound("id.greaterThan=" + id);

        defaultTypeEnseignementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTypeEnseignementShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTypeEnseignementsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        typeEnseignementRepository.saveAndFlush(typeEnseignement);

        // Get all the typeEnseignementList where type equals to DEFAULT_TYPE
        defaultTypeEnseignementShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the typeEnseignementList where type equals to UPDATED_TYPE
        defaultTypeEnseignementShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypeEnseignementsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        typeEnseignementRepository.saveAndFlush(typeEnseignement);

        // Get all the typeEnseignementList where type not equals to DEFAULT_TYPE
        defaultTypeEnseignementShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the typeEnseignementList where type not equals to UPDATED_TYPE
        defaultTypeEnseignementShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypeEnseignementsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        typeEnseignementRepository.saveAndFlush(typeEnseignement);

        // Get all the typeEnseignementList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultTypeEnseignementShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the typeEnseignementList where type equals to UPDATED_TYPE
        defaultTypeEnseignementShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypeEnseignementsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        typeEnseignementRepository.saveAndFlush(typeEnseignement);

        // Get all the typeEnseignementList where type is not null
        defaultTypeEnseignementShouldBeFound("type.specified=true");

        // Get all the typeEnseignementList where type is null
        defaultTypeEnseignementShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllTypeEnseignementsByTypeContainsSomething() throws Exception {
        // Initialize the database
        typeEnseignementRepository.saveAndFlush(typeEnseignement);

        // Get all the typeEnseignementList where type contains DEFAULT_TYPE
        defaultTypeEnseignementShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the typeEnseignementList where type contains UPDATED_TYPE
        defaultTypeEnseignementShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllTypeEnseignementsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        typeEnseignementRepository.saveAndFlush(typeEnseignement);

        // Get all the typeEnseignementList where type does not contain DEFAULT_TYPE
        defaultTypeEnseignementShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the typeEnseignementList where type does not contain UPDATED_TYPE
        defaultTypeEnseignementShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTypeEnseignementShouldBeFound(String filter) throws Exception {
        restTypeEnseignementMockMvc.perform(get("/api/type-enseignements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeEnseignement.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restTypeEnseignementMockMvc.perform(get("/api/type-enseignements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTypeEnseignementShouldNotBeFound(String filter) throws Exception {
        restTypeEnseignementMockMvc.perform(get("/api/type-enseignements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTypeEnseignementMockMvc.perform(get("/api/type-enseignements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTypeEnseignement() throws Exception {
        // Get the typeEnseignement
        restTypeEnseignementMockMvc.perform(get("/api/type-enseignements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeEnseignement() throws Exception {
        // Initialize the database
        typeEnseignementRepository.saveAndFlush(typeEnseignement);

        int databaseSizeBeforeUpdate = typeEnseignementRepository.findAll().size();

        // Update the typeEnseignement
        TypeEnseignement updatedTypeEnseignement = typeEnseignementRepository.findById(typeEnseignement.getId()).get();
        // Disconnect from session so that the updates on updatedTypeEnseignement are not directly saved in db
        em.detach(updatedTypeEnseignement);
        updatedTypeEnseignement
            .type(UPDATED_TYPE);
        TypeEnseignementDTO typeEnseignementDTO = typeEnseignementMapper.toDto(updatedTypeEnseignement);

        restTypeEnseignementMockMvc.perform(put("/api/type-enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeEnseignementDTO)))
            .andExpect(status().isOk());

        // Validate the TypeEnseignement in the database
        List<TypeEnseignement> typeEnseignementList = typeEnseignementRepository.findAll();
        assertThat(typeEnseignementList).hasSize(databaseSizeBeforeUpdate);
        TypeEnseignement testTypeEnseignement = typeEnseignementList.get(typeEnseignementList.size() - 1);
        assertThat(testTypeEnseignement.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeEnseignement() throws Exception {
        int databaseSizeBeforeUpdate = typeEnseignementRepository.findAll().size();

        // Create the TypeEnseignement
        TypeEnseignementDTO typeEnseignementDTO = typeEnseignementMapper.toDto(typeEnseignement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeEnseignementMockMvc.perform(put("/api/type-enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeEnseignementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TypeEnseignement in the database
        List<TypeEnseignement> typeEnseignementList = typeEnseignementRepository.findAll();
        assertThat(typeEnseignementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeEnseignement() throws Exception {
        // Initialize the database
        typeEnseignementRepository.saveAndFlush(typeEnseignement);

        int databaseSizeBeforeDelete = typeEnseignementRepository.findAll().size();

        // Delete the typeEnseignement
        restTypeEnseignementMockMvc.perform(delete("/api/type-enseignements/{id}", typeEnseignement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeEnseignement> typeEnseignementList = typeEnseignementRepository.findAll();
        assertThat(typeEnseignementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
