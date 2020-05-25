package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Resultat;
import com.pfe.gestionnote.domain.Matiere;
import com.pfe.gestionnote.domain.Inscription;
import com.pfe.gestionnote.repository.ResultatRepository;
import com.pfe.gestionnote.service.ResultatService;
import com.pfe.gestionnote.service.dto.ResultatDTO;
import com.pfe.gestionnote.service.mapper.ResultatMapper;
import com.pfe.gestionnote.service.dto.ResultatCriteria;
import com.pfe.gestionnote.service.ResultatQueryService;

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
 * Integration tests for the {@link ResultatResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResultatResourceIT {

    private static final Double DEFAULT_NOTECC_1 = 1D;
    private static final Double UPDATED_NOTECC_1 = 2D;
    private static final Double SMALLER_NOTECC_1 = 1D - 1D;

    private static final Double DEFAULT_NOTECC_2 = 1D;
    private static final Double UPDATED_NOTECC_2 = 2D;
    private static final Double SMALLER_NOTECC_2 = 1D - 1D;

    private static final Double DEFAULT_NOTEEXMEN = 1D;
    private static final Double UPDATED_NOTEEXMEN = 2D;
    private static final Double SMALLER_NOTEEXMEN = 1D - 1D;

    @Autowired
    private ResultatRepository resultatRepository;

    @Autowired
    private ResultatMapper resultatMapper;

    @Autowired
    private ResultatService resultatService;

    @Autowired
    private ResultatQueryService resultatQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultatMockMvc;

    private Resultat resultat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultat createEntity(EntityManager em) {
        Resultat resultat = new Resultat()
            .notecc1(DEFAULT_NOTECC_1)
            .notecc2(DEFAULT_NOTECC_2)
            .noteexmen(DEFAULT_NOTEEXMEN);
        return resultat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resultat createUpdatedEntity(EntityManager em) {
        Resultat resultat = new Resultat()
            .notecc1(UPDATED_NOTECC_1)
            .notecc2(UPDATED_NOTECC_2)
            .noteexmen(UPDATED_NOTEEXMEN);
        return resultat;
    }

    @BeforeEach
    public void initTest() {
        resultat = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultat() throws Exception {
        int databaseSizeBeforeCreate = resultatRepository.findAll().size();
        // Create the Resultat
        ResultatDTO resultatDTO = resultatMapper.toDto(resultat);
        restResultatMockMvc.perform(post("/api/resultats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultatDTO)))
            .andExpect(status().isCreated());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeCreate + 1);
        Resultat testResultat = resultatList.get(resultatList.size() - 1);
        assertThat(testResultat.getNotecc1()).isEqualTo(DEFAULT_NOTECC_1);
        assertThat(testResultat.getNotecc2()).isEqualTo(DEFAULT_NOTECC_2);
        assertThat(testResultat.getNoteexmen()).isEqualTo(DEFAULT_NOTEEXMEN);
    }

    @Test
    @Transactional
    public void createResultatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultatRepository.findAll().size();

        // Create the Resultat with an existing ID
        resultat.setId(1L);
        ResultatDTO resultatDTO = resultatMapper.toDto(resultat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultatMockMvc.perform(post("/api/resultats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllResultats() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList
        restResultatMockMvc.perform(get("/api/resultats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].notecc1").value(hasItem(DEFAULT_NOTECC_1.doubleValue())))
            .andExpect(jsonPath("$.[*].notecc2").value(hasItem(DEFAULT_NOTECC_2.doubleValue())))
            .andExpect(jsonPath("$.[*].noteexmen").value(hasItem(DEFAULT_NOTEEXMEN.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getResultat() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get the resultat
        restResultatMockMvc.perform(get("/api/resultats/{id}", resultat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultat.getId().intValue()))
            .andExpect(jsonPath("$.notecc1").value(DEFAULT_NOTECC_1.doubleValue()))
            .andExpect(jsonPath("$.notecc2").value(DEFAULT_NOTECC_2.doubleValue()))
            .andExpect(jsonPath("$.noteexmen").value(DEFAULT_NOTEEXMEN.doubleValue()));
    }


    @Test
    @Transactional
    public void getResultatsByIdFiltering() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        Long id = resultat.getId();

        defaultResultatShouldBeFound("id.equals=" + id);
        defaultResultatShouldNotBeFound("id.notEquals=" + id);

        defaultResultatShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResultatShouldNotBeFound("id.greaterThan=" + id);

        defaultResultatShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResultatShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllResultatsByNotecc1IsEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc1 equals to DEFAULT_NOTECC_1
        defaultResultatShouldBeFound("notecc1.equals=" + DEFAULT_NOTECC_1);

        // Get all the resultatList where notecc1 equals to UPDATED_NOTECC_1
        defaultResultatShouldNotBeFound("notecc1.equals=" + UPDATED_NOTECC_1);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc1 not equals to DEFAULT_NOTECC_1
        defaultResultatShouldNotBeFound("notecc1.notEquals=" + DEFAULT_NOTECC_1);

        // Get all the resultatList where notecc1 not equals to UPDATED_NOTECC_1
        defaultResultatShouldBeFound("notecc1.notEquals=" + UPDATED_NOTECC_1);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc1IsInShouldWork() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc1 in DEFAULT_NOTECC_1 or UPDATED_NOTECC_1
        defaultResultatShouldBeFound("notecc1.in=" + DEFAULT_NOTECC_1 + "," + UPDATED_NOTECC_1);

        // Get all the resultatList where notecc1 equals to UPDATED_NOTECC_1
        defaultResultatShouldNotBeFound("notecc1.in=" + UPDATED_NOTECC_1);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc1IsNullOrNotNull() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc1 is not null
        defaultResultatShouldBeFound("notecc1.specified=true");

        // Get all the resultatList where notecc1 is null
        defaultResultatShouldNotBeFound("notecc1.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc1 is greater than or equal to DEFAULT_NOTECC_1
        defaultResultatShouldBeFound("notecc1.greaterThanOrEqual=" + DEFAULT_NOTECC_1);

        // Get all the resultatList where notecc1 is greater than or equal to UPDATED_NOTECC_1
        defaultResultatShouldNotBeFound("notecc1.greaterThanOrEqual=" + UPDATED_NOTECC_1);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc1 is less than or equal to DEFAULT_NOTECC_1
        defaultResultatShouldBeFound("notecc1.lessThanOrEqual=" + DEFAULT_NOTECC_1);

        // Get all the resultatList where notecc1 is less than or equal to SMALLER_NOTECC_1
        defaultResultatShouldNotBeFound("notecc1.lessThanOrEqual=" + SMALLER_NOTECC_1);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc1IsLessThanSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc1 is less than DEFAULT_NOTECC_1
        defaultResultatShouldNotBeFound("notecc1.lessThan=" + DEFAULT_NOTECC_1);

        // Get all the resultatList where notecc1 is less than UPDATED_NOTECC_1
        defaultResultatShouldBeFound("notecc1.lessThan=" + UPDATED_NOTECC_1);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc1 is greater than DEFAULT_NOTECC_1
        defaultResultatShouldNotBeFound("notecc1.greaterThan=" + DEFAULT_NOTECC_1);

        // Get all the resultatList where notecc1 is greater than SMALLER_NOTECC_1
        defaultResultatShouldBeFound("notecc1.greaterThan=" + SMALLER_NOTECC_1);
    }


    @Test
    @Transactional
    public void getAllResultatsByNotecc2IsEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc2 equals to DEFAULT_NOTECC_2
        defaultResultatShouldBeFound("notecc2.equals=" + DEFAULT_NOTECC_2);

        // Get all the resultatList where notecc2 equals to UPDATED_NOTECC_2
        defaultResultatShouldNotBeFound("notecc2.equals=" + UPDATED_NOTECC_2);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc2 not equals to DEFAULT_NOTECC_2
        defaultResultatShouldNotBeFound("notecc2.notEquals=" + DEFAULT_NOTECC_2);

        // Get all the resultatList where notecc2 not equals to UPDATED_NOTECC_2
        defaultResultatShouldBeFound("notecc2.notEquals=" + UPDATED_NOTECC_2);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc2IsInShouldWork() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc2 in DEFAULT_NOTECC_2 or UPDATED_NOTECC_2
        defaultResultatShouldBeFound("notecc2.in=" + DEFAULT_NOTECC_2 + "," + UPDATED_NOTECC_2);

        // Get all the resultatList where notecc2 equals to UPDATED_NOTECC_2
        defaultResultatShouldNotBeFound("notecc2.in=" + UPDATED_NOTECC_2);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc2IsNullOrNotNull() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc2 is not null
        defaultResultatShouldBeFound("notecc2.specified=true");

        // Get all the resultatList where notecc2 is null
        defaultResultatShouldNotBeFound("notecc2.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc2 is greater than or equal to DEFAULT_NOTECC_2
        defaultResultatShouldBeFound("notecc2.greaterThanOrEqual=" + DEFAULT_NOTECC_2);

        // Get all the resultatList where notecc2 is greater than or equal to UPDATED_NOTECC_2
        defaultResultatShouldNotBeFound("notecc2.greaterThanOrEqual=" + UPDATED_NOTECC_2);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc2 is less than or equal to DEFAULT_NOTECC_2
        defaultResultatShouldBeFound("notecc2.lessThanOrEqual=" + DEFAULT_NOTECC_2);

        // Get all the resultatList where notecc2 is less than or equal to SMALLER_NOTECC_2
        defaultResultatShouldNotBeFound("notecc2.lessThanOrEqual=" + SMALLER_NOTECC_2);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc2IsLessThanSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc2 is less than DEFAULT_NOTECC_2
        defaultResultatShouldNotBeFound("notecc2.lessThan=" + DEFAULT_NOTECC_2);

        // Get all the resultatList where notecc2 is less than UPDATED_NOTECC_2
        defaultResultatShouldBeFound("notecc2.lessThan=" + UPDATED_NOTECC_2);
    }

    @Test
    @Transactional
    public void getAllResultatsByNotecc2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where notecc2 is greater than DEFAULT_NOTECC_2
        defaultResultatShouldNotBeFound("notecc2.greaterThan=" + DEFAULT_NOTECC_2);

        // Get all the resultatList where notecc2 is greater than SMALLER_NOTECC_2
        defaultResultatShouldBeFound("notecc2.greaterThan=" + SMALLER_NOTECC_2);
    }


    @Test
    @Transactional
    public void getAllResultatsByNoteexmenIsEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where noteexmen equals to DEFAULT_NOTEEXMEN
        defaultResultatShouldBeFound("noteexmen.equals=" + DEFAULT_NOTEEXMEN);

        // Get all the resultatList where noteexmen equals to UPDATED_NOTEEXMEN
        defaultResultatShouldNotBeFound("noteexmen.equals=" + UPDATED_NOTEEXMEN);
    }

    @Test
    @Transactional
    public void getAllResultatsByNoteexmenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where noteexmen not equals to DEFAULT_NOTEEXMEN
        defaultResultatShouldNotBeFound("noteexmen.notEquals=" + DEFAULT_NOTEEXMEN);

        // Get all the resultatList where noteexmen not equals to UPDATED_NOTEEXMEN
        defaultResultatShouldBeFound("noteexmen.notEquals=" + UPDATED_NOTEEXMEN);
    }

    @Test
    @Transactional
    public void getAllResultatsByNoteexmenIsInShouldWork() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where noteexmen in DEFAULT_NOTEEXMEN or UPDATED_NOTEEXMEN
        defaultResultatShouldBeFound("noteexmen.in=" + DEFAULT_NOTEEXMEN + "," + UPDATED_NOTEEXMEN);

        // Get all the resultatList where noteexmen equals to UPDATED_NOTEEXMEN
        defaultResultatShouldNotBeFound("noteexmen.in=" + UPDATED_NOTEEXMEN);
    }

    @Test
    @Transactional
    public void getAllResultatsByNoteexmenIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where noteexmen is not null
        defaultResultatShouldBeFound("noteexmen.specified=true");

        // Get all the resultatList where noteexmen is null
        defaultResultatShouldNotBeFound("noteexmen.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultatsByNoteexmenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where noteexmen is greater than or equal to DEFAULT_NOTEEXMEN
        defaultResultatShouldBeFound("noteexmen.greaterThanOrEqual=" + DEFAULT_NOTEEXMEN);

        // Get all the resultatList where noteexmen is greater than or equal to UPDATED_NOTEEXMEN
        defaultResultatShouldNotBeFound("noteexmen.greaterThanOrEqual=" + UPDATED_NOTEEXMEN);
    }

    @Test
    @Transactional
    public void getAllResultatsByNoteexmenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where noteexmen is less than or equal to DEFAULT_NOTEEXMEN
        defaultResultatShouldBeFound("noteexmen.lessThanOrEqual=" + DEFAULT_NOTEEXMEN);

        // Get all the resultatList where noteexmen is less than or equal to SMALLER_NOTEEXMEN
        defaultResultatShouldNotBeFound("noteexmen.lessThanOrEqual=" + SMALLER_NOTEEXMEN);
    }

    @Test
    @Transactional
    public void getAllResultatsByNoteexmenIsLessThanSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where noteexmen is less than DEFAULT_NOTEEXMEN
        defaultResultatShouldNotBeFound("noteexmen.lessThan=" + DEFAULT_NOTEEXMEN);

        // Get all the resultatList where noteexmen is less than UPDATED_NOTEEXMEN
        defaultResultatShouldBeFound("noteexmen.lessThan=" + UPDATED_NOTEEXMEN);
    }

    @Test
    @Transactional
    public void getAllResultatsByNoteexmenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        // Get all the resultatList where noteexmen is greater than DEFAULT_NOTEEXMEN
        defaultResultatShouldNotBeFound("noteexmen.greaterThan=" + DEFAULT_NOTEEXMEN);

        // Get all the resultatList where noteexmen is greater than SMALLER_NOTEEXMEN
        defaultResultatShouldBeFound("noteexmen.greaterThan=" + SMALLER_NOTEEXMEN);
    }


    @Test
    @Transactional
    public void getAllResultatsByMatiereIsEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);
        Matiere matiere = MatiereResourceIT.createEntity(em);
        em.persist(matiere);
        em.flush();
        resultat.setMatiere(matiere);
        resultatRepository.saveAndFlush(resultat);
        Long matiereId = matiere.getId();

        // Get all the resultatList where matiere equals to matiereId
        defaultResultatShouldBeFound("matiereId.equals=" + matiereId);

        // Get all the resultatList where matiere equals to matiereId + 1
        defaultResultatShouldNotBeFound("matiereId.equals=" + (matiereId + 1));
    }


    @Test
    @Transactional
    public void getAllResultatsByInscriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);
        Inscription inscription = InscriptionResourceIT.createEntity(em);
        em.persist(inscription);
        em.flush();
        resultat.setInscription(inscription);
        resultatRepository.saveAndFlush(resultat);
        Long inscriptionId = inscription.getId();

        // Get all the resultatList where inscription equals to inscriptionId
        defaultResultatShouldBeFound("inscriptionId.equals=" + inscriptionId);

        // Get all the resultatList where inscription equals to inscriptionId + 1
        defaultResultatShouldNotBeFound("inscriptionId.equals=" + (inscriptionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResultatShouldBeFound(String filter) throws Exception {
        restResultatMockMvc.perform(get("/api/resultats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultat.getId().intValue())))
            .andExpect(jsonPath("$.[*].notecc1").value(hasItem(DEFAULT_NOTECC_1.doubleValue())))
            .andExpect(jsonPath("$.[*].notecc2").value(hasItem(DEFAULT_NOTECC_2.doubleValue())))
            .andExpect(jsonPath("$.[*].noteexmen").value(hasItem(DEFAULT_NOTEEXMEN.doubleValue())));

        // Check, that the count call also returns 1
        restResultatMockMvc.perform(get("/api/resultats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResultatShouldNotBeFound(String filter) throws Exception {
        restResultatMockMvc.perform(get("/api/resultats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResultatMockMvc.perform(get("/api/resultats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingResultat() throws Exception {
        // Get the resultat
        restResultatMockMvc.perform(get("/api/resultats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultat() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        int databaseSizeBeforeUpdate = resultatRepository.findAll().size();

        // Update the resultat
        Resultat updatedResultat = resultatRepository.findById(resultat.getId()).get();
        // Disconnect from session so that the updates on updatedResultat are not directly saved in db
        em.detach(updatedResultat);
        updatedResultat
            .notecc1(UPDATED_NOTECC_1)
            .notecc2(UPDATED_NOTECC_2)
            .noteexmen(UPDATED_NOTEEXMEN);
        ResultatDTO resultatDTO = resultatMapper.toDto(updatedResultat);

        restResultatMockMvc.perform(put("/api/resultats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultatDTO)))
            .andExpect(status().isOk());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeUpdate);
        Resultat testResultat = resultatList.get(resultatList.size() - 1);
        assertThat(testResultat.getNotecc1()).isEqualTo(UPDATED_NOTECC_1);
        assertThat(testResultat.getNotecc2()).isEqualTo(UPDATED_NOTECC_2);
        assertThat(testResultat.getNoteexmen()).isEqualTo(UPDATED_NOTEEXMEN);
    }

    @Test
    @Transactional
    public void updateNonExistingResultat() throws Exception {
        int databaseSizeBeforeUpdate = resultatRepository.findAll().size();

        // Create the Resultat
        ResultatDTO resultatDTO = resultatMapper.toDto(resultat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultatMockMvc.perform(put("/api/resultats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Resultat in the database
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultat() throws Exception {
        // Initialize the database
        resultatRepository.saveAndFlush(resultat);

        int databaseSizeBeforeDelete = resultatRepository.findAll().size();

        // Delete the resultat
        restResultatMockMvc.perform(delete("/api/resultats/{id}", resultat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resultat> resultatList = resultatRepository.findAll();
        assertThat(resultatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
