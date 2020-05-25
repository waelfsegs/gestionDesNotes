package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Inscription;
import com.pfe.gestionnote.domain.Etudiant;
import com.pfe.gestionnote.domain.Classe;
import com.pfe.gestionnote.domain.Groupe;
import com.pfe.gestionnote.domain.Semstre;
import com.pfe.gestionnote.repository.InscriptionRepository;
import com.pfe.gestionnote.service.InscriptionService;
import com.pfe.gestionnote.service.dto.InscriptionDTO;
import com.pfe.gestionnote.service.mapper.InscriptionMapper;
import com.pfe.gestionnote.service.dto.InscriptionCriteria;
import com.pfe.gestionnote.service.InscriptionQueryService;

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
 * Integration tests for the {@link InscriptionResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InscriptionResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_ANNEE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNEE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_ANNEE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private InscriptionMapper inscriptionMapper;

    @Autowired
    private InscriptionService inscriptionService;

    @Autowired
    private InscriptionQueryService inscriptionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInscriptionMockMvc;

    private Inscription inscription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscription createEntity(EntityManager em) {
        Inscription inscription = new Inscription()
            .date(DEFAULT_DATE)
            .annee(DEFAULT_ANNEE);
        return inscription;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inscription createUpdatedEntity(EntityManager em) {
        Inscription inscription = new Inscription()
            .date(UPDATED_DATE)
            .annee(UPDATED_ANNEE);
        return inscription;
    }

    @BeforeEach
    public void initTest() {
        inscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createInscription() throws Exception {
        int databaseSizeBeforeCreate = inscriptionRepository.findAll().size();
        // Create the Inscription
        InscriptionDTO inscriptionDTO = inscriptionMapper.toDto(inscription);
        restInscriptionMockMvc.perform(post("/api/inscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inscriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Inscription testInscription = inscriptionList.get(inscriptionList.size() - 1);
        assertThat(testInscription.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testInscription.getAnnee()).isEqualTo(DEFAULT_ANNEE);
    }

    @Test
    @Transactional
    public void createInscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inscriptionRepository.findAll().size();

        // Create the Inscription with an existing ID
        inscription.setId(1L);
        InscriptionDTO inscriptionDTO = inscriptionMapper.toDto(inscription);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscriptionMockMvc.perform(post("/api/inscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInscriptions() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList
        restInscriptionMockMvc.perform(get("/api/inscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE.toString())));
    }
    
    @Test
    @Transactional
    public void getInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get the inscription
        restInscriptionMockMvc.perform(get("/api/inscriptions/{id}", inscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inscription.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE.toString()));
    }


    @Test
    @Transactional
    public void getInscriptionsByIdFiltering() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        Long id = inscription.getId();

        defaultInscriptionShouldBeFound("id.equals=" + id);
        defaultInscriptionShouldNotBeFound("id.notEquals=" + id);

        defaultInscriptionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInscriptionShouldNotBeFound("id.greaterThan=" + id);

        defaultInscriptionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInscriptionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInscriptionsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where date equals to DEFAULT_DATE
        defaultInscriptionShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the inscriptionList where date equals to UPDATED_DATE
        defaultInscriptionShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where date not equals to DEFAULT_DATE
        defaultInscriptionShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the inscriptionList where date not equals to UPDATED_DATE
        defaultInscriptionShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where date in DEFAULT_DATE or UPDATED_DATE
        defaultInscriptionShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the inscriptionList where date equals to UPDATED_DATE
        defaultInscriptionShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where date is not null
        defaultInscriptionShouldBeFound("date.specified=true");

        // Get all the inscriptionList where date is null
        defaultInscriptionShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllInscriptionsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where date is greater than or equal to DEFAULT_DATE
        defaultInscriptionShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the inscriptionList where date is greater than or equal to UPDATED_DATE
        defaultInscriptionShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where date is less than or equal to DEFAULT_DATE
        defaultInscriptionShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the inscriptionList where date is less than or equal to SMALLER_DATE
        defaultInscriptionShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where date is less than DEFAULT_DATE
        defaultInscriptionShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the inscriptionList where date is less than UPDATED_DATE
        defaultInscriptionShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where date is greater than DEFAULT_DATE
        defaultInscriptionShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the inscriptionList where date is greater than SMALLER_DATE
        defaultInscriptionShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllInscriptionsByAnneeIsEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where annee equals to DEFAULT_ANNEE
        defaultInscriptionShouldBeFound("annee.equals=" + DEFAULT_ANNEE);

        // Get all the inscriptionList where annee equals to UPDATED_ANNEE
        defaultInscriptionShouldNotBeFound("annee.equals=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByAnneeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where annee not equals to DEFAULT_ANNEE
        defaultInscriptionShouldNotBeFound("annee.notEquals=" + DEFAULT_ANNEE);

        // Get all the inscriptionList where annee not equals to UPDATED_ANNEE
        defaultInscriptionShouldBeFound("annee.notEquals=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByAnneeIsInShouldWork() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where annee in DEFAULT_ANNEE or UPDATED_ANNEE
        defaultInscriptionShouldBeFound("annee.in=" + DEFAULT_ANNEE + "," + UPDATED_ANNEE);

        // Get all the inscriptionList where annee equals to UPDATED_ANNEE
        defaultInscriptionShouldNotBeFound("annee.in=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByAnneeIsNullOrNotNull() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where annee is not null
        defaultInscriptionShouldBeFound("annee.specified=true");

        // Get all the inscriptionList where annee is null
        defaultInscriptionShouldNotBeFound("annee.specified=false");
    }

    @Test
    @Transactional
    public void getAllInscriptionsByAnneeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where annee is greater than or equal to DEFAULT_ANNEE
        defaultInscriptionShouldBeFound("annee.greaterThanOrEqual=" + DEFAULT_ANNEE);

        // Get all the inscriptionList where annee is greater than or equal to UPDATED_ANNEE
        defaultInscriptionShouldNotBeFound("annee.greaterThanOrEqual=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByAnneeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where annee is less than or equal to DEFAULT_ANNEE
        defaultInscriptionShouldBeFound("annee.lessThanOrEqual=" + DEFAULT_ANNEE);

        // Get all the inscriptionList where annee is less than or equal to SMALLER_ANNEE
        defaultInscriptionShouldNotBeFound("annee.lessThanOrEqual=" + SMALLER_ANNEE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByAnneeIsLessThanSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where annee is less than DEFAULT_ANNEE
        defaultInscriptionShouldNotBeFound("annee.lessThan=" + DEFAULT_ANNEE);

        // Get all the inscriptionList where annee is less than UPDATED_ANNEE
        defaultInscriptionShouldBeFound("annee.lessThan=" + UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void getAllInscriptionsByAnneeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        // Get all the inscriptionList where annee is greater than DEFAULT_ANNEE
        defaultInscriptionShouldNotBeFound("annee.greaterThan=" + DEFAULT_ANNEE);

        // Get all the inscriptionList where annee is greater than SMALLER_ANNEE
        defaultInscriptionShouldBeFound("annee.greaterThan=" + SMALLER_ANNEE);
    }


    @Test
    @Transactional
    public void getAllInscriptionsByEtudiantIsEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);
        Etudiant etudiant = EtudiantResourceIT.createEntity(em);
        em.persist(etudiant);
        em.flush();
        inscription.setEtudiant(etudiant);
        inscriptionRepository.saveAndFlush(inscription);
        Long etudiantId = etudiant.getId();

        // Get all the inscriptionList where etudiant equals to etudiantId
        defaultInscriptionShouldBeFound("etudiantId.equals=" + etudiantId);

        // Get all the inscriptionList where etudiant equals to etudiantId + 1
        defaultInscriptionShouldNotBeFound("etudiantId.equals=" + (etudiantId + 1));
    }


    @Test
    @Transactional
    public void getAllInscriptionsByClasseIsEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);
        Classe classe = ClasseResourceIT.createEntity(em);
        em.persist(classe);
        em.flush();
        inscription.setClasse(classe);
        inscriptionRepository.saveAndFlush(inscription);
        Long classeId = classe.getId();

        // Get all the inscriptionList where classe equals to classeId
        defaultInscriptionShouldBeFound("classeId.equals=" + classeId);

        // Get all the inscriptionList where classe equals to classeId + 1
        defaultInscriptionShouldNotBeFound("classeId.equals=" + (classeId + 1));
    }


    @Test
    @Transactional
    public void getAllInscriptionsByGroupeIsEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);
        Groupe groupe = GroupeResourceIT.createEntity(em);
        em.persist(groupe);
        em.flush();
        inscription.setGroupe(groupe);
        inscriptionRepository.saveAndFlush(inscription);
        Long groupeId = groupe.getId();

        // Get all the inscriptionList where groupe equals to groupeId
        defaultInscriptionShouldBeFound("groupeId.equals=" + groupeId);

        // Get all the inscriptionList where groupe equals to groupeId + 1
        defaultInscriptionShouldNotBeFound("groupeId.equals=" + (groupeId + 1));
    }


    @Test
    @Transactional
    public void getAllInscriptionsBySemstreIsEqualToSomething() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);
        Semstre semstre = SemstreResourceIT.createEntity(em);
        em.persist(semstre);
        em.flush();
        inscription.setSemstre(semstre);
        inscriptionRepository.saveAndFlush(inscription);
        Long semstreId = semstre.getId();

        // Get all the inscriptionList where semstre equals to semstreId
        defaultInscriptionShouldBeFound("semstreId.equals=" + semstreId);

        // Get all the inscriptionList where semstre equals to semstreId + 1
        defaultInscriptionShouldNotBeFound("semstreId.equals=" + (semstreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInscriptionShouldBeFound(String filter) throws Exception {
        restInscriptionMockMvc.perform(get("/api/inscriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE.toString())));

        // Check, that the count call also returns 1
        restInscriptionMockMvc.perform(get("/api/inscriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInscriptionShouldNotBeFound(String filter) throws Exception {
        restInscriptionMockMvc.perform(get("/api/inscriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInscriptionMockMvc.perform(get("/api/inscriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInscription() throws Exception {
        // Get the inscription
        restInscriptionMockMvc.perform(get("/api/inscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();

        // Update the inscription
        Inscription updatedInscription = inscriptionRepository.findById(inscription.getId()).get();
        // Disconnect from session so that the updates on updatedInscription are not directly saved in db
        em.detach(updatedInscription);
        updatedInscription
            .date(UPDATED_DATE)
            .annee(UPDATED_ANNEE);
        InscriptionDTO inscriptionDTO = inscriptionMapper.toDto(updatedInscription);

        restInscriptionMockMvc.perform(put("/api/inscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inscriptionDTO)))
            .andExpect(status().isOk());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
        Inscription testInscription = inscriptionList.get(inscriptionList.size() - 1);
        assertThat(testInscription.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testInscription.getAnnee()).isEqualTo(UPDATED_ANNEE);
    }

    @Test
    @Transactional
    public void updateNonExistingInscription() throws Exception {
        int databaseSizeBeforeUpdate = inscriptionRepository.findAll().size();

        // Create the Inscription
        InscriptionDTO inscriptionDTO = inscriptionMapper.toDto(inscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInscriptionMockMvc.perform(put("/api/inscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inscription in the database
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInscription() throws Exception {
        // Initialize the database
        inscriptionRepository.saveAndFlush(inscription);

        int databaseSizeBeforeDelete = inscriptionRepository.findAll().size();

        // Delete the inscription
        restInscriptionMockMvc.perform(delete("/api/inscriptions/{id}", inscription.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inscription> inscriptionList = inscriptionRepository.findAll();
        assertThat(inscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
