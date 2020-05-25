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
import java.time.LocalDate;
import java.time.ZoneId;
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

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_TEL = 1;
    private static final Integer UPDATED_TEL = 2;
    private static final Integer SMALLER_TEL = 1 - 1;

    private static final LocalDate DEFAULT_DATE_NAIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAIS = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_NAIS = LocalDate.ofEpochDay(-1L);

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
            .matricule(DEFAULT_MATRICULE)
            .prenom(DEFAULT_PRENOM)
            .tel(DEFAULT_TEL)
            .dateNais(DEFAULT_DATE_NAIS);
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
            .matricule(UPDATED_MATRICULE)
            .prenom(UPDATED_PRENOM)
            .tel(UPDATED_TEL)
            .dateNais(UPDATED_DATE_NAIS);
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
        assertThat(testEtudiant.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEtudiant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testEtudiant.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testEtudiant.getDateNais()).isEqualTo(DEFAULT_DATE_NAIS);
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
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].dateNais").value(hasItem(DEFAULT_DATE_NAIS.toString())));
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
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.dateNais").value(DEFAULT_DATE_NAIS.toString()));
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
    public void getAllEtudiantsByMatriculeIsEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matricule equals to DEFAULT_MATRICULE
        defaultEtudiantShouldBeFound("matricule.equals=" + DEFAULT_MATRICULE);

        // Get all the etudiantList where matricule equals to UPDATED_MATRICULE
        defaultEtudiantShouldNotBeFound("matricule.equals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByMatriculeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matricule not equals to DEFAULT_MATRICULE
        defaultEtudiantShouldNotBeFound("matricule.notEquals=" + DEFAULT_MATRICULE);

        // Get all the etudiantList where matricule not equals to UPDATED_MATRICULE
        defaultEtudiantShouldBeFound("matricule.notEquals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByMatriculeIsInShouldWork() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matricule in DEFAULT_MATRICULE or UPDATED_MATRICULE
        defaultEtudiantShouldBeFound("matricule.in=" + DEFAULT_MATRICULE + "," + UPDATED_MATRICULE);

        // Get all the etudiantList where matricule equals to UPDATED_MATRICULE
        defaultEtudiantShouldNotBeFound("matricule.in=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByMatriculeIsNullOrNotNull() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matricule is not null
        defaultEtudiantShouldBeFound("matricule.specified=true");

        // Get all the etudiantList where matricule is null
        defaultEtudiantShouldNotBeFound("matricule.specified=false");
    }
                @Test
    @Transactional
    public void getAllEtudiantsByMatriculeContainsSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matricule contains DEFAULT_MATRICULE
        defaultEtudiantShouldBeFound("matricule.contains=" + DEFAULT_MATRICULE);

        // Get all the etudiantList where matricule contains UPDATED_MATRICULE
        defaultEtudiantShouldNotBeFound("matricule.contains=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByMatriculeNotContainsSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where matricule does not contain DEFAULT_MATRICULE
        defaultEtudiantShouldNotBeFound("matricule.doesNotContain=" + DEFAULT_MATRICULE);

        // Get all the etudiantList where matricule does not contain UPDATED_MATRICULE
        defaultEtudiantShouldBeFound("matricule.doesNotContain=" + UPDATED_MATRICULE);
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


    @Test
    @Transactional
    public void getAllEtudiantsByTelIsEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where tel equals to DEFAULT_TEL
        defaultEtudiantShouldBeFound("tel.equals=" + DEFAULT_TEL);

        // Get all the etudiantList where tel equals to UPDATED_TEL
        defaultEtudiantShouldNotBeFound("tel.equals=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByTelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where tel not equals to DEFAULT_TEL
        defaultEtudiantShouldNotBeFound("tel.notEquals=" + DEFAULT_TEL);

        // Get all the etudiantList where tel not equals to UPDATED_TEL
        defaultEtudiantShouldBeFound("tel.notEquals=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByTelIsInShouldWork() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where tel in DEFAULT_TEL or UPDATED_TEL
        defaultEtudiantShouldBeFound("tel.in=" + DEFAULT_TEL + "," + UPDATED_TEL);

        // Get all the etudiantList where tel equals to UPDATED_TEL
        defaultEtudiantShouldNotBeFound("tel.in=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByTelIsNullOrNotNull() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where tel is not null
        defaultEtudiantShouldBeFound("tel.specified=true");

        // Get all the etudiantList where tel is null
        defaultEtudiantShouldNotBeFound("tel.specified=false");
    }

    @Test
    @Transactional
    public void getAllEtudiantsByTelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where tel is greater than or equal to DEFAULT_TEL
        defaultEtudiantShouldBeFound("tel.greaterThanOrEqual=" + DEFAULT_TEL);

        // Get all the etudiantList where tel is greater than or equal to UPDATED_TEL
        defaultEtudiantShouldNotBeFound("tel.greaterThanOrEqual=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByTelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where tel is less than or equal to DEFAULT_TEL
        defaultEtudiantShouldBeFound("tel.lessThanOrEqual=" + DEFAULT_TEL);

        // Get all the etudiantList where tel is less than or equal to SMALLER_TEL
        defaultEtudiantShouldNotBeFound("tel.lessThanOrEqual=" + SMALLER_TEL);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByTelIsLessThanSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where tel is less than DEFAULT_TEL
        defaultEtudiantShouldNotBeFound("tel.lessThan=" + DEFAULT_TEL);

        // Get all the etudiantList where tel is less than UPDATED_TEL
        defaultEtudiantShouldBeFound("tel.lessThan=" + UPDATED_TEL);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByTelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where tel is greater than DEFAULT_TEL
        defaultEtudiantShouldNotBeFound("tel.greaterThan=" + DEFAULT_TEL);

        // Get all the etudiantList where tel is greater than SMALLER_TEL
        defaultEtudiantShouldBeFound("tel.greaterThan=" + SMALLER_TEL);
    }


    @Test
    @Transactional
    public void getAllEtudiantsByDateNaisIsEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where dateNais equals to DEFAULT_DATE_NAIS
        defaultEtudiantShouldBeFound("dateNais.equals=" + DEFAULT_DATE_NAIS);

        // Get all the etudiantList where dateNais equals to UPDATED_DATE_NAIS
        defaultEtudiantShouldNotBeFound("dateNais.equals=" + UPDATED_DATE_NAIS);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByDateNaisIsNotEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where dateNais not equals to DEFAULT_DATE_NAIS
        defaultEtudiantShouldNotBeFound("dateNais.notEquals=" + DEFAULT_DATE_NAIS);

        // Get all the etudiantList where dateNais not equals to UPDATED_DATE_NAIS
        defaultEtudiantShouldBeFound("dateNais.notEquals=" + UPDATED_DATE_NAIS);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByDateNaisIsInShouldWork() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where dateNais in DEFAULT_DATE_NAIS or UPDATED_DATE_NAIS
        defaultEtudiantShouldBeFound("dateNais.in=" + DEFAULT_DATE_NAIS + "," + UPDATED_DATE_NAIS);

        // Get all the etudiantList where dateNais equals to UPDATED_DATE_NAIS
        defaultEtudiantShouldNotBeFound("dateNais.in=" + UPDATED_DATE_NAIS);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByDateNaisIsNullOrNotNull() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where dateNais is not null
        defaultEtudiantShouldBeFound("dateNais.specified=true");

        // Get all the etudiantList where dateNais is null
        defaultEtudiantShouldNotBeFound("dateNais.specified=false");
    }

    @Test
    @Transactional
    public void getAllEtudiantsByDateNaisIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where dateNais is greater than or equal to DEFAULT_DATE_NAIS
        defaultEtudiantShouldBeFound("dateNais.greaterThanOrEqual=" + DEFAULT_DATE_NAIS);

        // Get all the etudiantList where dateNais is greater than or equal to UPDATED_DATE_NAIS
        defaultEtudiantShouldNotBeFound("dateNais.greaterThanOrEqual=" + UPDATED_DATE_NAIS);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByDateNaisIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where dateNais is less than or equal to DEFAULT_DATE_NAIS
        defaultEtudiantShouldBeFound("dateNais.lessThanOrEqual=" + DEFAULT_DATE_NAIS);

        // Get all the etudiantList where dateNais is less than or equal to SMALLER_DATE_NAIS
        defaultEtudiantShouldNotBeFound("dateNais.lessThanOrEqual=" + SMALLER_DATE_NAIS);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByDateNaisIsLessThanSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where dateNais is less than DEFAULT_DATE_NAIS
        defaultEtudiantShouldNotBeFound("dateNais.lessThan=" + DEFAULT_DATE_NAIS);

        // Get all the etudiantList where dateNais is less than UPDATED_DATE_NAIS
        defaultEtudiantShouldBeFound("dateNais.lessThan=" + UPDATED_DATE_NAIS);
    }

    @Test
    @Transactional
    public void getAllEtudiantsByDateNaisIsGreaterThanSomething() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiantList where dateNais is greater than DEFAULT_DATE_NAIS
        defaultEtudiantShouldNotBeFound("dateNais.greaterThan=" + DEFAULT_DATE_NAIS);

        // Get all the etudiantList where dateNais is greater than SMALLER_DATE_NAIS
        defaultEtudiantShouldBeFound("dateNais.greaterThan=" + SMALLER_DATE_NAIS);
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
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].dateNais").value(hasItem(DEFAULT_DATE_NAIS.toString())));

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
            .matricule(UPDATED_MATRICULE)
            .prenom(UPDATED_PRENOM)
            .tel(UPDATED_TEL)
            .dateNais(UPDATED_DATE_NAIS);
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
        assertThat(testEtudiant.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEtudiant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testEtudiant.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testEtudiant.getDateNais()).isEqualTo(UPDATED_DATE_NAIS);
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
