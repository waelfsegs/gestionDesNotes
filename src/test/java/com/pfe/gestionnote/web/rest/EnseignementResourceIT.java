package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Enseignement;
import com.pfe.gestionnote.domain.Matiere;
import com.pfe.gestionnote.domain.Enseignant;
import com.pfe.gestionnote.domain.Groupe;
import com.pfe.gestionnote.domain.TypeEnseignement;
import com.pfe.gestionnote.repository.EnseignementRepository;
import com.pfe.gestionnote.service.EnseignementService;
import com.pfe.gestionnote.service.dto.EnseignementDTO;
import com.pfe.gestionnote.service.mapper.EnseignementMapper;
import com.pfe.gestionnote.service.dto.EnseignementCriteria;
import com.pfe.gestionnote.service.EnseignementQueryService;

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
 * Integration tests for the {@link EnseignementResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnseignementResourceIT {

    @Autowired
    private EnseignementRepository enseignementRepository;

    @Autowired
    private EnseignementMapper enseignementMapper;

    @Autowired
    private EnseignementService enseignementService;

    @Autowired
    private EnseignementQueryService enseignementQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnseignementMockMvc;

    private Enseignement enseignement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enseignement createEntity(EntityManager em) {
        Enseignement enseignement = new Enseignement();
        return enseignement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enseignement createUpdatedEntity(EntityManager em) {
        Enseignement enseignement = new Enseignement();
        return enseignement;
    }

    @BeforeEach
    public void initTest() {
        enseignement = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnseignement() throws Exception {
        int databaseSizeBeforeCreate = enseignementRepository.findAll().size();
        // Create the Enseignement
        EnseignementDTO enseignementDTO = enseignementMapper.toDto(enseignement);
        restEnseignementMockMvc.perform(post("/api/enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignementDTO)))
            .andExpect(status().isCreated());

        // Validate the Enseignement in the database
        List<Enseignement> enseignementList = enseignementRepository.findAll();
        assertThat(enseignementList).hasSize(databaseSizeBeforeCreate + 1);
        Enseignement testEnseignement = enseignementList.get(enseignementList.size() - 1);
    }

    @Test
    @Transactional
    public void createEnseignementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enseignementRepository.findAll().size();

        // Create the Enseignement with an existing ID
        enseignement.setId(1L);
        EnseignementDTO enseignementDTO = enseignementMapper.toDto(enseignement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnseignementMockMvc.perform(post("/api/enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enseignement in the database
        List<Enseignement> enseignementList = enseignementRepository.findAll();
        assertThat(enseignementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEnseignements() throws Exception {
        // Initialize the database
        enseignementRepository.saveAndFlush(enseignement);

        // Get all the enseignementList
        restEnseignementMockMvc.perform(get("/api/enseignements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enseignement.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getEnseignement() throws Exception {
        // Initialize the database
        enseignementRepository.saveAndFlush(enseignement);

        // Get the enseignement
        restEnseignementMockMvc.perform(get("/api/enseignements/{id}", enseignement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enseignement.getId().intValue()));
    }


    @Test
    @Transactional
    public void getEnseignementsByIdFiltering() throws Exception {
        // Initialize the database
        enseignementRepository.saveAndFlush(enseignement);

        Long id = enseignement.getId();

        defaultEnseignementShouldBeFound("id.equals=" + id);
        defaultEnseignementShouldNotBeFound("id.notEquals=" + id);

        defaultEnseignementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnseignementShouldNotBeFound("id.greaterThan=" + id);

        defaultEnseignementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnseignementShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEnseignementsByMatiereIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignementRepository.saveAndFlush(enseignement);
        Matiere matiere = MatiereResourceIT.createEntity(em);
        em.persist(matiere);
        em.flush();
        enseignement.setMatiere(matiere);
        enseignementRepository.saveAndFlush(enseignement);
        Long matiereId = matiere.getId();

        // Get all the enseignementList where matiere equals to matiereId
        defaultEnseignementShouldBeFound("matiereId.equals=" + matiereId);

        // Get all the enseignementList where matiere equals to matiereId + 1
        defaultEnseignementShouldNotBeFound("matiereId.equals=" + (matiereId + 1));
    }


    @Test
    @Transactional
    public void getAllEnseignementsByEnseignantIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignementRepository.saveAndFlush(enseignement);
        Enseignant enseignant = EnseignantResourceIT.createEntity(em);
        em.persist(enseignant);
        em.flush();
        enseignement.setEnseignant(enseignant);
        enseignementRepository.saveAndFlush(enseignement);
        Long enseignantId = enseignant.getId();

        // Get all the enseignementList where enseignant equals to enseignantId
        defaultEnseignementShouldBeFound("enseignantId.equals=" + enseignantId);

        // Get all the enseignementList where enseignant equals to enseignantId + 1
        defaultEnseignementShouldNotBeFound("enseignantId.equals=" + (enseignantId + 1));
    }


    @Test
    @Transactional
    public void getAllEnseignementsByGroupeIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignementRepository.saveAndFlush(enseignement);
        Groupe groupe = GroupeResourceIT.createEntity(em);
        em.persist(groupe);
        em.flush();
        enseignement.setGroupe(groupe);
        enseignementRepository.saveAndFlush(enseignement);
        Long groupeId = groupe.getId();

        // Get all the enseignementList where groupe equals to groupeId
        defaultEnseignementShouldBeFound("groupeId.equals=" + groupeId);

        // Get all the enseignementList where groupe equals to groupeId + 1
        defaultEnseignementShouldNotBeFound("groupeId.equals=" + (groupeId + 1));
    }


    @Test
    @Transactional
    public void getAllEnseignementsByTypeEnseignementIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignementRepository.saveAndFlush(enseignement);
        TypeEnseignement typeEnseignement = TypeEnseignementResourceIT.createEntity(em);
        em.persist(typeEnseignement);
        em.flush();
        enseignement.setTypeEnseignement(typeEnseignement);
        enseignementRepository.saveAndFlush(enseignement);
        Long typeEnseignementId = typeEnseignement.getId();

        // Get all the enseignementList where typeEnseignement equals to typeEnseignementId
        defaultEnseignementShouldBeFound("typeEnseignementId.equals=" + typeEnseignementId);

        // Get all the enseignementList where typeEnseignement equals to typeEnseignementId + 1
        defaultEnseignementShouldNotBeFound("typeEnseignementId.equals=" + (typeEnseignementId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnseignementShouldBeFound(String filter) throws Exception {
        restEnseignementMockMvc.perform(get("/api/enseignements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enseignement.getId().intValue())));

        // Check, that the count call also returns 1
        restEnseignementMockMvc.perform(get("/api/enseignements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnseignementShouldNotBeFound(String filter) throws Exception {
        restEnseignementMockMvc.perform(get("/api/enseignements?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnseignementMockMvc.perform(get("/api/enseignements/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEnseignement() throws Exception {
        // Get the enseignement
        restEnseignementMockMvc.perform(get("/api/enseignements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnseignement() throws Exception {
        // Initialize the database
        enseignementRepository.saveAndFlush(enseignement);

        int databaseSizeBeforeUpdate = enseignementRepository.findAll().size();

        // Update the enseignement
        Enseignement updatedEnseignement = enseignementRepository.findById(enseignement.getId()).get();
        // Disconnect from session so that the updates on updatedEnseignement are not directly saved in db
        em.detach(updatedEnseignement);
        EnseignementDTO enseignementDTO = enseignementMapper.toDto(updatedEnseignement);

        restEnseignementMockMvc.perform(put("/api/enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignementDTO)))
            .andExpect(status().isOk());

        // Validate the Enseignement in the database
        List<Enseignement> enseignementList = enseignementRepository.findAll();
        assertThat(enseignementList).hasSize(databaseSizeBeforeUpdate);
        Enseignement testEnseignement = enseignementList.get(enseignementList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingEnseignement() throws Exception {
        int databaseSizeBeforeUpdate = enseignementRepository.findAll().size();

        // Create the Enseignement
        EnseignementDTO enseignementDTO = enseignementMapper.toDto(enseignement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnseignementMockMvc.perform(put("/api/enseignements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enseignement in the database
        List<Enseignement> enseignementList = enseignementRepository.findAll();
        assertThat(enseignementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnseignement() throws Exception {
        // Initialize the database
        enseignementRepository.saveAndFlush(enseignement);

        int databaseSizeBeforeDelete = enseignementRepository.findAll().size();

        // Delete the enseignement
        restEnseignementMockMvc.perform(delete("/api/enseignements/{id}", enseignement.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enseignement> enseignementList = enseignementRepository.findAll();
        assertThat(enseignementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
