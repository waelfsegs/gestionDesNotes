package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Specialite;
import com.pfe.gestionnote.repository.SpecialiteRepository;
import com.pfe.gestionnote.service.SpecialiteService;
import com.pfe.gestionnote.service.dto.SpecialiteDTO;
import com.pfe.gestionnote.service.mapper.SpecialiteMapper;
import com.pfe.gestionnote.service.dto.SpecialiteCriteria;
import com.pfe.gestionnote.service.SpecialiteQueryService;

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
 * Integration tests for the {@link SpecialiteResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SpecialiteResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private SpecialiteRepository specialiteRepository;

    @Autowired
    private SpecialiteMapper specialiteMapper;

    @Autowired
    private SpecialiteService specialiteService;

    @Autowired
    private SpecialiteQueryService specialiteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecialiteMockMvc;

    private Specialite specialite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialite createEntity(EntityManager em) {
        Specialite specialite = new Specialite()
            .libelle(DEFAULT_LIBELLE);
        return specialite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialite createUpdatedEntity(EntityManager em) {
        Specialite specialite = new Specialite()
            .libelle(UPDATED_LIBELLE);
        return specialite;
    }

    @BeforeEach
    public void initTest() {
        specialite = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialite() throws Exception {
        int databaseSizeBeforeCreate = specialiteRepository.findAll().size();
        // Create the Specialite
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);
        restSpecialiteMockMvc.perform(post("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialiteDTO)))
            .andExpect(status().isCreated());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeCreate + 1);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createSpecialiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialiteRepository.findAll().size();

        // Create the Specialite with an existing ID
        specialite.setId(1L);
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialiteMockMvc.perform(post("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialiteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSpecialites() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList
        restSpecialiteMockMvc.perform(get("/api/specialites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getSpecialite() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get the specialite
        restSpecialiteMockMvc.perform(get("/api/specialites/{id}", specialite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specialite.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }


    @Test
    @Transactional
    public void getSpecialitesByIdFiltering() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        Long id = specialite.getId();

        defaultSpecialiteShouldBeFound("id.equals=" + id);
        defaultSpecialiteShouldNotBeFound("id.notEquals=" + id);

        defaultSpecialiteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSpecialiteShouldNotBeFound("id.greaterThan=" + id);

        defaultSpecialiteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSpecialiteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSpecialitesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelle equals to DEFAULT_LIBELLE
        defaultSpecialiteShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the specialiteList where libelle equals to UPDATED_LIBELLE
        defaultSpecialiteShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllSpecialitesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelle not equals to DEFAULT_LIBELLE
        defaultSpecialiteShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the specialiteList where libelle not equals to UPDATED_LIBELLE
        defaultSpecialiteShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllSpecialitesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultSpecialiteShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the specialiteList where libelle equals to UPDATED_LIBELLE
        defaultSpecialiteShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllSpecialitesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelle is not null
        defaultSpecialiteShouldBeFound("libelle.specified=true");

        // Get all the specialiteList where libelle is null
        defaultSpecialiteShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllSpecialitesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelle contains DEFAULT_LIBELLE
        defaultSpecialiteShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the specialiteList where libelle contains UPDATED_LIBELLE
        defaultSpecialiteShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllSpecialitesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelle does not contain DEFAULT_LIBELLE
        defaultSpecialiteShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the specialiteList where libelle does not contain UPDATED_LIBELLE
        defaultSpecialiteShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSpecialiteShouldBeFound(String filter) throws Exception {
        restSpecialiteMockMvc.perform(get("/api/specialites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restSpecialiteMockMvc.perform(get("/api/specialites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSpecialiteShouldNotBeFound(String filter) throws Exception {
        restSpecialiteMockMvc.perform(get("/api/specialites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSpecialiteMockMvc.perform(get("/api/specialites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSpecialite() throws Exception {
        // Get the specialite
        restSpecialiteMockMvc.perform(get("/api/specialites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialite() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();

        // Update the specialite
        Specialite updatedSpecialite = specialiteRepository.findById(specialite.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialite are not directly saved in db
        em.detach(updatedSpecialite);
        updatedSpecialite
            .libelle(UPDATED_LIBELLE);
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(updatedSpecialite);

        restSpecialiteMockMvc.perform(put("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialiteDTO)))
            .andExpect(status().isOk());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialite() throws Exception {
        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();

        // Create the Specialite
        SpecialiteDTO specialiteDTO = specialiteMapper.toDto(specialite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialiteMockMvc.perform(put("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialiteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpecialite() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        int databaseSizeBeforeDelete = specialiteRepository.findAll().size();

        // Delete the specialite
        restSpecialiteMockMvc.perform(delete("/api/specialites/{id}", specialite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
