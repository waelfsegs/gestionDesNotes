package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Enseignant;
import com.pfe.gestionnote.domain.Departement;
import com.pfe.gestionnote.repository.EnseignantRepository;
import com.pfe.gestionnote.service.EnseignantService;
import com.pfe.gestionnote.service.dto.EnseignantDTO;
import com.pfe.gestionnote.service.mapper.EnseignantMapper;
import com.pfe.gestionnote.service.dto.EnseignantCriteria;
import com.pfe.gestionnote.service.EnseignantQueryService;

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
 * Integration tests for the {@link EnseignantResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EnseignantResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PERNOM = "AAAAAAAAAA";
    private static final String UPDATED_PERNOM = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_MATRICULE = 1;
    private static final Integer UPDATED_MATRICULE = 2;
    private static final Integer SMALLER_MATRICULE = 1 - 1;

    private static final Integer DEFAULT_CIN = 1;
    private static final Integer UPDATED_CIN = 2;
    private static final Integer SMALLER_CIN = 1 - 1;

    private static final LocalDate DEFAULT_DATE_EMBAUCHEMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EMBAUCHEMENT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_EMBAUCHEMENT = LocalDate.ofEpochDay(-1L);

    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private EnseignantMapper enseignantMapper;

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private EnseignantQueryService enseignantQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnseignantMockMvc;

    private Enseignant enseignant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enseignant createEntity(EntityManager em) {
        Enseignant enseignant = new Enseignant()
            .nom(DEFAULT_NOM)
            .pernom(DEFAULT_PERNOM)
            .mail(DEFAULT_MAIL)
            .matricule(DEFAULT_MATRICULE)
            .cin(DEFAULT_CIN)
            .dateEmbauchement(DEFAULT_DATE_EMBAUCHEMENT);
        return enseignant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enseignant createUpdatedEntity(EntityManager em) {
        Enseignant enseignant = new Enseignant()
            .nom(UPDATED_NOM)
            .pernom(UPDATED_PERNOM)
            .mail(UPDATED_MAIL)
            .matricule(UPDATED_MATRICULE)
            .cin(UPDATED_CIN)
            .dateEmbauchement(UPDATED_DATE_EMBAUCHEMENT);
        return enseignant;
    }

    @BeforeEach
    public void initTest() {
        enseignant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnseignant() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();
        // Create the Enseignant
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);
        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isCreated());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeCreate + 1);
        Enseignant testEnseignant = enseignantList.get(enseignantList.size() - 1);
        assertThat(testEnseignant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEnseignant.getPernom()).isEqualTo(DEFAULT_PERNOM);
        assertThat(testEnseignant.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testEnseignant.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEnseignant.getCin()).isEqualTo(DEFAULT_CIN);
        assertThat(testEnseignant.getDateEmbauchement()).isEqualTo(DEFAULT_DATE_EMBAUCHEMENT);
    }

    @Test
    @Transactional
    public void createEnseignantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();

        // Create the Enseignant with an existing ID
        enseignant.setId(1L);
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnseignantMockMvc.perform(post("/api/enseignants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEnseignants() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList
        restEnseignantMockMvc.perform(get("/api/enseignants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enseignant.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].pernom").value(hasItem(DEFAULT_PERNOM)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].dateEmbauchement").value(hasItem(DEFAULT_DATE_EMBAUCHEMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", enseignant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enseignant.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.pernom").value(DEFAULT_PERNOM))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.cin").value(DEFAULT_CIN))
            .andExpect(jsonPath("$.dateEmbauchement").value(DEFAULT_DATE_EMBAUCHEMENT.toString()));
    }


    @Test
    @Transactional
    public void getEnseignantsByIdFiltering() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        Long id = enseignant.getId();

        defaultEnseignantShouldBeFound("id.equals=" + id);
        defaultEnseignantShouldNotBeFound("id.notEquals=" + id);

        defaultEnseignantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnseignantShouldNotBeFound("id.greaterThan=" + id);

        defaultEnseignantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnseignantShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom equals to DEFAULT_NOM
        defaultEnseignantShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the enseignantList where nom equals to UPDATED_NOM
        defaultEnseignantShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom not equals to DEFAULT_NOM
        defaultEnseignantShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the enseignantList where nom not equals to UPDATED_NOM
        defaultEnseignantShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByNomIsInShouldWork() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultEnseignantShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the enseignantList where nom equals to UPDATED_NOM
        defaultEnseignantShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom is not null
        defaultEnseignantShouldBeFound("nom.specified=true");

        // Get all the enseignantList where nom is null
        defaultEnseignantShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnseignantsByNomContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom contains DEFAULT_NOM
        defaultEnseignantShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the enseignantList where nom contains UPDATED_NOM
        defaultEnseignantShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByNomNotContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where nom does not contain DEFAULT_NOM
        defaultEnseignantShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the enseignantList where nom does not contain UPDATED_NOM
        defaultEnseignantShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByPernomIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where pernom equals to DEFAULT_PERNOM
        defaultEnseignantShouldBeFound("pernom.equals=" + DEFAULT_PERNOM);

        // Get all the enseignantList where pernom equals to UPDATED_PERNOM
        defaultEnseignantShouldNotBeFound("pernom.equals=" + UPDATED_PERNOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByPernomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where pernom not equals to DEFAULT_PERNOM
        defaultEnseignantShouldNotBeFound("pernom.notEquals=" + DEFAULT_PERNOM);

        // Get all the enseignantList where pernom not equals to UPDATED_PERNOM
        defaultEnseignantShouldBeFound("pernom.notEquals=" + UPDATED_PERNOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByPernomIsInShouldWork() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where pernom in DEFAULT_PERNOM or UPDATED_PERNOM
        defaultEnseignantShouldBeFound("pernom.in=" + DEFAULT_PERNOM + "," + UPDATED_PERNOM);

        // Get all the enseignantList where pernom equals to UPDATED_PERNOM
        defaultEnseignantShouldNotBeFound("pernom.in=" + UPDATED_PERNOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByPernomIsNullOrNotNull() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where pernom is not null
        defaultEnseignantShouldBeFound("pernom.specified=true");

        // Get all the enseignantList where pernom is null
        defaultEnseignantShouldNotBeFound("pernom.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnseignantsByPernomContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where pernom contains DEFAULT_PERNOM
        defaultEnseignantShouldBeFound("pernom.contains=" + DEFAULT_PERNOM);

        // Get all the enseignantList where pernom contains UPDATED_PERNOM
        defaultEnseignantShouldNotBeFound("pernom.contains=" + UPDATED_PERNOM);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByPernomNotContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where pernom does not contain DEFAULT_PERNOM
        defaultEnseignantShouldNotBeFound("pernom.doesNotContain=" + DEFAULT_PERNOM);

        // Get all the enseignantList where pernom does not contain UPDATED_PERNOM
        defaultEnseignantShouldBeFound("pernom.doesNotContain=" + UPDATED_PERNOM);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByMailIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where mail equals to DEFAULT_MAIL
        defaultEnseignantShouldBeFound("mail.equals=" + DEFAULT_MAIL);

        // Get all the enseignantList where mail equals to UPDATED_MAIL
        defaultEnseignantShouldNotBeFound("mail.equals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByMailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where mail not equals to DEFAULT_MAIL
        defaultEnseignantShouldNotBeFound("mail.notEquals=" + DEFAULT_MAIL);

        // Get all the enseignantList where mail not equals to UPDATED_MAIL
        defaultEnseignantShouldBeFound("mail.notEquals=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByMailIsInShouldWork() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where mail in DEFAULT_MAIL or UPDATED_MAIL
        defaultEnseignantShouldBeFound("mail.in=" + DEFAULT_MAIL + "," + UPDATED_MAIL);

        // Get all the enseignantList where mail equals to UPDATED_MAIL
        defaultEnseignantShouldNotBeFound("mail.in=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByMailIsNullOrNotNull() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where mail is not null
        defaultEnseignantShouldBeFound("mail.specified=true");

        // Get all the enseignantList where mail is null
        defaultEnseignantShouldNotBeFound("mail.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnseignantsByMailContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where mail contains DEFAULT_MAIL
        defaultEnseignantShouldBeFound("mail.contains=" + DEFAULT_MAIL);

        // Get all the enseignantList where mail contains UPDATED_MAIL
        defaultEnseignantShouldNotBeFound("mail.contains=" + UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByMailNotContainsSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where mail does not contain DEFAULT_MAIL
        defaultEnseignantShouldNotBeFound("mail.doesNotContain=" + DEFAULT_MAIL);

        // Get all the enseignantList where mail does not contain UPDATED_MAIL
        defaultEnseignantShouldBeFound("mail.doesNotContain=" + UPDATED_MAIL);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByMatriculeIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where matricule equals to DEFAULT_MATRICULE
        defaultEnseignantShouldBeFound("matricule.equals=" + DEFAULT_MATRICULE);

        // Get all the enseignantList where matricule equals to UPDATED_MATRICULE
        defaultEnseignantShouldNotBeFound("matricule.equals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByMatriculeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where matricule not equals to DEFAULT_MATRICULE
        defaultEnseignantShouldNotBeFound("matricule.notEquals=" + DEFAULT_MATRICULE);

        // Get all the enseignantList where matricule not equals to UPDATED_MATRICULE
        defaultEnseignantShouldBeFound("matricule.notEquals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByMatriculeIsInShouldWork() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where matricule in DEFAULT_MATRICULE or UPDATED_MATRICULE
        defaultEnseignantShouldBeFound("matricule.in=" + DEFAULT_MATRICULE + "," + UPDATED_MATRICULE);

        // Get all the enseignantList where matricule equals to UPDATED_MATRICULE
        defaultEnseignantShouldNotBeFound("matricule.in=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByMatriculeIsNullOrNotNull() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where matricule is not null
        defaultEnseignantShouldBeFound("matricule.specified=true");

        // Get all the enseignantList where matricule is null
        defaultEnseignantShouldNotBeFound("matricule.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnseignantsByMatriculeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where matricule is greater than or equal to DEFAULT_MATRICULE
        defaultEnseignantShouldBeFound("matricule.greaterThanOrEqual=" + DEFAULT_MATRICULE);

        // Get all the enseignantList where matricule is greater than or equal to UPDATED_MATRICULE
        defaultEnseignantShouldNotBeFound("matricule.greaterThanOrEqual=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByMatriculeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where matricule is less than or equal to DEFAULT_MATRICULE
        defaultEnseignantShouldBeFound("matricule.lessThanOrEqual=" + DEFAULT_MATRICULE);

        // Get all the enseignantList where matricule is less than or equal to SMALLER_MATRICULE
        defaultEnseignantShouldNotBeFound("matricule.lessThanOrEqual=" + SMALLER_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByMatriculeIsLessThanSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where matricule is less than DEFAULT_MATRICULE
        defaultEnseignantShouldNotBeFound("matricule.lessThan=" + DEFAULT_MATRICULE);

        // Get all the enseignantList where matricule is less than UPDATED_MATRICULE
        defaultEnseignantShouldBeFound("matricule.lessThan=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByMatriculeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where matricule is greater than DEFAULT_MATRICULE
        defaultEnseignantShouldNotBeFound("matricule.greaterThan=" + DEFAULT_MATRICULE);

        // Get all the enseignantList where matricule is greater than SMALLER_MATRICULE
        defaultEnseignantShouldBeFound("matricule.greaterThan=" + SMALLER_MATRICULE);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByCinIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where cin equals to DEFAULT_CIN
        defaultEnseignantShouldBeFound("cin.equals=" + DEFAULT_CIN);

        // Get all the enseignantList where cin equals to UPDATED_CIN
        defaultEnseignantShouldNotBeFound("cin.equals=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByCinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where cin not equals to DEFAULT_CIN
        defaultEnseignantShouldNotBeFound("cin.notEquals=" + DEFAULT_CIN);

        // Get all the enseignantList where cin not equals to UPDATED_CIN
        defaultEnseignantShouldBeFound("cin.notEquals=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByCinIsInShouldWork() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where cin in DEFAULT_CIN or UPDATED_CIN
        defaultEnseignantShouldBeFound("cin.in=" + DEFAULT_CIN + "," + UPDATED_CIN);

        // Get all the enseignantList where cin equals to UPDATED_CIN
        defaultEnseignantShouldNotBeFound("cin.in=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByCinIsNullOrNotNull() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where cin is not null
        defaultEnseignantShouldBeFound("cin.specified=true");

        // Get all the enseignantList where cin is null
        defaultEnseignantShouldNotBeFound("cin.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnseignantsByCinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where cin is greater than or equal to DEFAULT_CIN
        defaultEnseignantShouldBeFound("cin.greaterThanOrEqual=" + DEFAULT_CIN);

        // Get all the enseignantList where cin is greater than or equal to UPDATED_CIN
        defaultEnseignantShouldNotBeFound("cin.greaterThanOrEqual=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByCinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where cin is less than or equal to DEFAULT_CIN
        defaultEnseignantShouldBeFound("cin.lessThanOrEqual=" + DEFAULT_CIN);

        // Get all the enseignantList where cin is less than or equal to SMALLER_CIN
        defaultEnseignantShouldNotBeFound("cin.lessThanOrEqual=" + SMALLER_CIN);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByCinIsLessThanSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where cin is less than DEFAULT_CIN
        defaultEnseignantShouldNotBeFound("cin.lessThan=" + DEFAULT_CIN);

        // Get all the enseignantList where cin is less than UPDATED_CIN
        defaultEnseignantShouldBeFound("cin.lessThan=" + UPDATED_CIN);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByCinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where cin is greater than DEFAULT_CIN
        defaultEnseignantShouldNotBeFound("cin.greaterThan=" + DEFAULT_CIN);

        // Get all the enseignantList where cin is greater than SMALLER_CIN
        defaultEnseignantShouldBeFound("cin.greaterThan=" + SMALLER_CIN);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByDateEmbauchementIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where dateEmbauchement equals to DEFAULT_DATE_EMBAUCHEMENT
        defaultEnseignantShouldBeFound("dateEmbauchement.equals=" + DEFAULT_DATE_EMBAUCHEMENT);

        // Get all the enseignantList where dateEmbauchement equals to UPDATED_DATE_EMBAUCHEMENT
        defaultEnseignantShouldNotBeFound("dateEmbauchement.equals=" + UPDATED_DATE_EMBAUCHEMENT);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByDateEmbauchementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where dateEmbauchement not equals to DEFAULT_DATE_EMBAUCHEMENT
        defaultEnseignantShouldNotBeFound("dateEmbauchement.notEquals=" + DEFAULT_DATE_EMBAUCHEMENT);

        // Get all the enseignantList where dateEmbauchement not equals to UPDATED_DATE_EMBAUCHEMENT
        defaultEnseignantShouldBeFound("dateEmbauchement.notEquals=" + UPDATED_DATE_EMBAUCHEMENT);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByDateEmbauchementIsInShouldWork() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where dateEmbauchement in DEFAULT_DATE_EMBAUCHEMENT or UPDATED_DATE_EMBAUCHEMENT
        defaultEnseignantShouldBeFound("dateEmbauchement.in=" + DEFAULT_DATE_EMBAUCHEMENT + "," + UPDATED_DATE_EMBAUCHEMENT);

        // Get all the enseignantList where dateEmbauchement equals to UPDATED_DATE_EMBAUCHEMENT
        defaultEnseignantShouldNotBeFound("dateEmbauchement.in=" + UPDATED_DATE_EMBAUCHEMENT);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByDateEmbauchementIsNullOrNotNull() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where dateEmbauchement is not null
        defaultEnseignantShouldBeFound("dateEmbauchement.specified=true");

        // Get all the enseignantList where dateEmbauchement is null
        defaultEnseignantShouldNotBeFound("dateEmbauchement.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnseignantsByDateEmbauchementIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where dateEmbauchement is greater than or equal to DEFAULT_DATE_EMBAUCHEMENT
        defaultEnseignantShouldBeFound("dateEmbauchement.greaterThanOrEqual=" + DEFAULT_DATE_EMBAUCHEMENT);

        // Get all the enseignantList where dateEmbauchement is greater than or equal to UPDATED_DATE_EMBAUCHEMENT
        defaultEnseignantShouldNotBeFound("dateEmbauchement.greaterThanOrEqual=" + UPDATED_DATE_EMBAUCHEMENT);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByDateEmbauchementIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where dateEmbauchement is less than or equal to DEFAULT_DATE_EMBAUCHEMENT
        defaultEnseignantShouldBeFound("dateEmbauchement.lessThanOrEqual=" + DEFAULT_DATE_EMBAUCHEMENT);

        // Get all the enseignantList where dateEmbauchement is less than or equal to SMALLER_DATE_EMBAUCHEMENT
        defaultEnseignantShouldNotBeFound("dateEmbauchement.lessThanOrEqual=" + SMALLER_DATE_EMBAUCHEMENT);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByDateEmbauchementIsLessThanSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where dateEmbauchement is less than DEFAULT_DATE_EMBAUCHEMENT
        defaultEnseignantShouldNotBeFound("dateEmbauchement.lessThan=" + DEFAULT_DATE_EMBAUCHEMENT);

        // Get all the enseignantList where dateEmbauchement is less than UPDATED_DATE_EMBAUCHEMENT
        defaultEnseignantShouldBeFound("dateEmbauchement.lessThan=" + UPDATED_DATE_EMBAUCHEMENT);
    }

    @Test
    @Transactional
    public void getAllEnseignantsByDateEmbauchementIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignantList where dateEmbauchement is greater than DEFAULT_DATE_EMBAUCHEMENT
        defaultEnseignantShouldNotBeFound("dateEmbauchement.greaterThan=" + DEFAULT_DATE_EMBAUCHEMENT);

        // Get all the enseignantList where dateEmbauchement is greater than SMALLER_DATE_EMBAUCHEMENT
        defaultEnseignantShouldBeFound("dateEmbauchement.greaterThan=" + SMALLER_DATE_EMBAUCHEMENT);
    }


    @Test
    @Transactional
    public void getAllEnseignantsByDepartementIsEqualToSomething() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);
        Departement departement = DepartementResourceIT.createEntity(em);
        em.persist(departement);
        em.flush();
        enseignant.setDepartement(departement);
        enseignantRepository.saveAndFlush(enseignant);
        Long departementId = departement.getId();

        // Get all the enseignantList where departement equals to departementId
        defaultEnseignantShouldBeFound("departementId.equals=" + departementId);

        // Get all the enseignantList where departement equals to departementId + 1
        defaultEnseignantShouldNotBeFound("departementId.equals=" + (departementId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnseignantShouldBeFound(String filter) throws Exception {
        restEnseignantMockMvc.perform(get("/api/enseignants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enseignant.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].pernom").value(hasItem(DEFAULT_PERNOM)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].cin").value(hasItem(DEFAULT_CIN)))
            .andExpect(jsonPath("$.[*].dateEmbauchement").value(hasItem(DEFAULT_DATE_EMBAUCHEMENT.toString())));

        // Check, that the count call also returns 1
        restEnseignantMockMvc.perform(get("/api/enseignants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnseignantShouldNotBeFound(String filter) throws Exception {
        restEnseignantMockMvc.perform(get("/api/enseignants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnseignantMockMvc.perform(get("/api/enseignants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEnseignant() throws Exception {
        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Update the enseignant
        Enseignant updatedEnseignant = enseignantRepository.findById(enseignant.getId()).get();
        // Disconnect from session so that the updates on updatedEnseignant are not directly saved in db
        em.detach(updatedEnseignant);
        updatedEnseignant
            .nom(UPDATED_NOM)
            .pernom(UPDATED_PERNOM)
            .mail(UPDATED_MAIL)
            .matricule(UPDATED_MATRICULE)
            .cin(UPDATED_CIN)
            .dateEmbauchement(UPDATED_DATE_EMBAUCHEMENT);
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(updatedEnseignant);

        restEnseignantMockMvc.perform(put("/api/enseignants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isOk());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeUpdate);
        Enseignant testEnseignant = enseignantList.get(enseignantList.size() - 1);
        assertThat(testEnseignant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEnseignant.getPernom()).isEqualTo(UPDATED_PERNOM);
        assertThat(testEnseignant.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testEnseignant.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEnseignant.getCin()).isEqualTo(UPDATED_CIN);
        assertThat(testEnseignant.getDateEmbauchement()).isEqualTo(UPDATED_DATE_EMBAUCHEMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingEnseignant() throws Exception {
        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Create the Enseignant
        EnseignantDTO enseignantDTO = enseignantMapper.toDto(enseignant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnseignantMockMvc.perform(put("/api/enseignants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enseignantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enseignant in the database
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        int databaseSizeBeforeDelete = enseignantRepository.findAll().size();

        // Delete the enseignant
        restEnseignantMockMvc.perform(delete("/api/enseignants/{id}", enseignant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enseignant> enseignantList = enseignantRepository.findAll();
        assertThat(enseignantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
