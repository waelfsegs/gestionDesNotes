package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Etudiant;
import com.pfe.gestionnote.repository.EtudiantRepository;
import com.pfe.gestionnote.service.EtudiantService;
import com.pfe.gestionnote.service.dto.EtudiantDTO;
import com.pfe.gestionnote.service.mapper.EtudiantMapper;
import com.pfe.gestionnote.service.dto.EtudiantCriteria;
import com.pfe.gestionnote.service.EtudiantQueryService;

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
 * Integration tests for the {@link EtudiantResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EtudiantResourceIT {

    private static final Integer DEFAULT_CIN = 1;
    private static final Integer UPDATED_CIN = 2;
    private static final Integer SMALLER_CIN = 1 - 1;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_MATERICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATERICULE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private EtudiantMapper etudiantMapper;

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private EtudiantQueryService etudiantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEtudiantMockMvc;

    private Etudiant etudiant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etudiant createEntity(EntityManager em) {
        Etudiant etudiant = new Etudiant()
            .cin(DEFAULT_CIN)
            .nom(DEFAULT_NOM)
            .matericule(DEFAULT_MATERICULE)
            .prenom(DEFAULT_PRENOM);
        return etudiant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etudiant createUpdatedEntity(EntityManager em) {
        Etudiant etudiant = new Etudiant()
            .cin(UPDATED_CIN)
            .nom(UPDATED_NOM)
            .matericule(UPDATED_MATERICULE)
            .prenom(UPDATED_PRENOM);
        return etudiant;
    }

    @BeforeEach
    public void initTest() {
        etudiant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtudiant() throws Exception {
        int databaseSizeBeforeCreate = etudiantRepository.findAll().size();

        // Create the Etudiant
        EtudiantDTO etudiantDTO = etudiantMapper.toDto(etudiant);
        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etudiantDTO)))
            .andExpect(status().isCreated());

        // Validate the Etudiant in the database
        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeCreate + 1);
        Etudiant testEtudiant = etudiantList.get(etudiantList.size() - 1);
        assertThat(testEtudiant.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testEtudiant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEtudiant.getMatericule()).isEqualTo(DEFAULT_MATERICULE);
        assertThat(testEtudiant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
    }

    @Test
    @Transactional
    public void createEtudiantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etudiantRepository.findAll().size();

        // Create the Etudiant with an existing ID
        etudiant.setId(1L);
        EtudiantDTO etudiantDTO = etudiantMapper.toDto(etudiant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtudiantMockMvc.perform(post("/api/etudiants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etudiantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Etudiant in the database
        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtudiants() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList
        restEtudiantMockMvc.perform(get("/api/etudiants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etudiant.getId().intValue())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].matericule").value(hasItem(DEFAULT_MATERICULE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)));
    }
    
    @Test
    @Transactional
    public void getEtudiant() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get the etudiant
        restEtudiantMockMvc.perform(get("/api/etudiants/{id}", etudiant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etudiant.getId().intValue()))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.matericule").value(DEFAULT_MATERICULE))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM));
    }


    @Test
    @Transactional
    public void getEtudiantsByIdFiltering() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        Long id = etudiant.getId();

        defaultEtudiantShouldBeFound("id.equals=" + id);
        defaultEtudiantShouldNotBeFound("id.notEquals=" + id);

        defaultEtudiantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEtudiantShouldNotBeFound("id.greaterThan=" + id);

        defaultEtudiantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEtudiantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEtudiantsByCinIsEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where cin equals to DEFAULT_CIN
        defaultEtudiantShouldBeFound("cin.equals=" + DEFAULT_CIN);

        // Get all the etudiantList where cin equals to UPDATED_CIN
        defaultEtudiantShouldNotBeFound("cin.equals=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByCinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where cin not equals to DEFAULT_CIN
        defaultEtudiantShouldNotBeFound("cin.notEquals=" + DEFAULT_CIN);

        // Get all the etudiantList where cin not equals to UPDATED_CIN
        defaultEtudiantShouldBeFound("cin.notEquals=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByCinIsInShouldWork() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where cin in DEFAULT_CIN or UPDATED_CIN
        defaultEtudiantShouldBeFound("cin.in=" + DEFAULT_CIN + "," + UPDATED_CIN);

        // Get all the etudiantList where cin equals to UPDATED_CIN
        defaultEtudiantShouldNotBeFound("cin.in=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByCinIsNullOrNotNull() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where cin is not null
        defaultEtudiantShouldBeFound("cin.specified=true");

        // Get all the etudiantList where cin is null
        defaultEtudiantShouldNotBeFound("cin.specified=false");
    }

    @Test
    @Transactional
    public void getAllEtudiantsByCinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where cin is greater than or equal to DEFAULT_CIN
        defaultEtudiantShouldBeFound("cin.greaterThanOrEqual=" + DEFAULT_CIN);

        // Get all the etudiantList where cin is greater than or equal to UPDATED_CIN
        defaultEtudiantShouldNotBeFound("cin.greaterThanOrEqual=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByCinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where cin is less than or equal to DEFAULT_CIN
        defaultEtudiantShouldBeFound("cin.lessThanOrEqual=" + DEFAULT_CIN);

        // Get all the etudiantList where cin is less than or equal to SMALLER_CIN
        defaultEtudiantShouldNotBeFound("cin.lessThanOrEqual=" + SMALLER_CIN);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByCinIsLessThanSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where cin is less than DEFAULT_CIN
        defaultEtudiantShouldNotBeFound("cin.lessThan=" + DEFAULT_CIN);

        // Get all the etudiantList where cin is less than UPDATED_CIN
        defaultEtudiantShouldBeFound("cin.lessThan=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByCinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where cin is greater than DEFAULT_CIN
        defaultEtudiantShouldNotBeFound("cin.greaterThan=" + DEFAULT_CIN);

        // Get all the etudiantList where cin is greater than SMALLER_CIN
        defaultEtudiantShouldBeFound("cin.greaterThan=" + SMALLER_CIN);
    }


    @Test
    @Transactional
    public void getAllEtudiantsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where nom equals to DEFAULT_NOM
        defaultEtudiantShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the etudiantList where nom equals to UPDATED_NOM
        defaultEtudiantShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where nom not equals to DEFAULT_NOM
        defaultEtudiantShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the etudiantList where nom not equals to UPDATED_NOM
        defaultEtudiantShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultEtudiantShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the etudiantList where nom equals to UPDATED_NOM
        defaultEtudiantShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where nom is not null
        defaultEtudiantShouldBeFound("nom.specified=true");

        // Get all the etudiantList where nom is null
        defaultEtudiantShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllEtudiantsByNomContainsSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where nom contains DEFAULT_NOM
        defaultEtudiantShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the etudiantList where nom contains UPDATED_NOM
        defaultEtudiantShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where nom does not contain DEFAULT_NOM
        defaultEtudiantShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the etudiantList where nom does not contain UPDATED_NOM
        defaultEtudiantShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllEtudiantsByMatericuleIsEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matericule equals to DEFAULT_MATERICULE
        defaultEtudiantShouldBeFound("matericule.equals=" + DEFAULT_MATERICULE);

        // Get all the etudiantList where matericule equals to UPDATED_MATERICULE
        defaultEtudiantShouldNotBeFound("matericule.equals=" + UPDATED_MATERICULE);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByMatericuleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matericule not equals to DEFAULT_MATERICULE
        defaultEtudiantShouldNotBeFound("matericule.notEquals=" + DEFAULT_MATERICULE);

        // Get all the etudiantList where matericule not equals to UPDATED_MATERICULE
        defaultEtudiantShouldBeFound("matericule.notEquals=" + UPDATED_MATERICULE);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByMatericuleIsInShouldWork() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matericule in DEFAULT_MATERICULE or UPDATED_MATERICULE
        defaultEtudiantShouldBeFound("matericule.in=" + DEFAULT_MATERICULE + "," + UPDATED_MATERICULE);

        // Get all the etudiantList where matericule equals to UPDATED_MATERICULE
        defaultEtudiantShouldNotBeFound("matericule.in=" + UPDATED_MATERICULE);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByMatericuleIsNullOrNotNull() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matericule is not null
        defaultEtudiantShouldBeFound("matericule.specified=true");

        // Get all the etudiantList where matericule is null
        defaultEtudiantShouldNotBeFound("matericule.specified=false");
    }
                @Test
    @Transactional
    public void getAllEtudiantsByMatericuleContainsSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matericule contains DEFAULT_MATERICULE
        defaultEtudiantShouldBeFound("matericule.contains=" + DEFAULT_MATERICULE);

        // Get all the etudiantList where matericule contains UPDATED_MATERICULE
        defaultEtudiantShouldNotBeFound("matericule.contains=" + UPDATED_MATERICULE);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByMatericuleNotContainsSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matericule does not contain DEFAULT_MATERICULE
        defaultEtudiantShouldNotBeFound("matericule.doesNotContain=" + DEFAULT_MATERICULE);

        // Get all the etudiantList where matericule does not contain UPDATED_MATERICULE
        defaultEtudiantShouldBeFound("matericule.doesNotContain=" + UPDATED_MATERICULE);
    }


    @Test
    @Transactional
    public void getAllEtudiantsByPrenomIsEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where prenom equals to DEFAULT_PRENOM
        defaultEtudiantShouldBeFound("prenom.equals=" + DEFAULT_PRENOM);

        // Get all the etudiantList where prenom equals to UPDATED_PRENOM
        defaultEtudiantShouldNotBeFound("prenom.equals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByPrenomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where prenom not equals to DEFAULT_PRENOM
        defaultEtudiantShouldNotBeFound("prenom.notEquals=" + DEFAULT_PRENOM);

        // Get all the etudiantList where prenom not equals to UPDATED_PRENOM
        defaultEtudiantShouldBeFound("prenom.notEquals=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByPrenomIsInShouldWork() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where prenom in DEFAULT_PRENOM or UPDATED_PRENOM
        defaultEtudiantShouldBeFound("prenom.in=" + DEFAULT_PRENOM + "," + UPDATED_PRENOM);

        // Get all the etudiantList where prenom equals to UPDATED_PRENOM
        defaultEtudiantShouldNotBeFound("prenom.in=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByPrenomIsNullOrNotNull() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where prenom is not null
        defaultEtudiantShouldBeFound("prenom.specified=true");

        // Get all the etudiantList where prenom is null
        defaultEtudiantShouldNotBeFound("prenom.specified=false");
    }
                @Test
    @Transactional
    public void getAllEtudiantsByPrenomContainsSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where prenom contains DEFAULT_PRENOM
        defaultEtudiantShouldBeFound("prenom.contains=" + DEFAULT_PRENOM);

        // Get all the etudiantList where prenom contains UPDATED_PRENOM
        defaultEtudiantShouldNotBeFound("prenom.contains=" + UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByPrenomNotContainsSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where prenom does not contain DEFAULT_PRENOM
        defaultEtudiantShouldNotBeFound("prenom.doesNotContain=" + DEFAULT_PRENOM);

        // Get all the etudiantList where prenom does not contain UPDATED_PRENOM
        defaultEtudiantShouldBeFound("prenom.doesNotContain=" + UPDATED_PRENOM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEtudiantShouldBeFound(String filter) throws Exception {
        restEtudiantMockMvc.perform(get("/api/etudiants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etudiant.getId().intValue())))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].matericule").value(hasItem(DEFAULT_MATERICULE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)));

        // Check, that the count call also returns 1
        restEtudiantMockMvc.perform(get("/api/etudiants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEtudiantShouldNotBeFound(String filter) throws Exception {
        restEtudiantMockMvc.perform(get("/api/etudiants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEtudiantMockMvc.perform(get("/api/etudiants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEtudiant() throws Exception {
        // Get the etudiant
        restEtudiantMockMvc.perform(get("/api/etudiants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtudiant() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        int databaseSizeBeforeUpdate = etudiantRepository.findAll().size();

        // Update the etudiant
        Etudiant updatedEtudiant = etudiantRepository.findById(etudiant.getId()).get();
        // Disconnect from session so that the updates on updatedEtudiant are not directly saved in db
        em.detach(updatedEtudiant);
        updatedEtudiant
            .cin(UPDATED_CIN)
            .nom(UPDATED_NOM)
            .matericule(UPDATED_MATERICULE)
            .prenom(UPDATED_PRENOM);
        EtudiantDTO etudiantDTO = etudiantMapper.toDto(updatedEtudiant);

        restEtudiantMockMvc.perform(put("/api/etudiants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etudiantDTO)))
            .andExpect(status().isOk());

        // Validate the Etudiant in the database
        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeUpdate);
        Etudiant testEtudiant = etudiantList.get(etudiantList.size() - 1);
        assertThat(testEtudiant.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testEtudiant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEtudiant.getMatericule()).isEqualTo(UPDATED_MATERICULE);
        assertThat(testEtudiant.getPrenom()).isEqualTo(UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void updateNonExistingEtudiant() throws Exception {
        int databaseSizeBeforeUpdate = etudiantRepository.findAll().size();

        // Create the Etudiant
        EtudiantDTO etudiantDTO = etudiantMapper.toDto(etudiant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtudiantMockMvc.perform(put("/api/etudiants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etudiantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Etudiant in the database
        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtudiant() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        int databaseSizeBeforeDelete = etudiantRepository.findAll().size();

        // Delete the etudiant
        restEtudiantMockMvc.perform(delete("/api/etudiants/{id}", etudiant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Etudiant> etudiantList = etudiantRepository.findAll();
        assertThat(etudiantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
