package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Matiere;
import com.pfe.gestionnote.domain.Regime;
import com.pfe.gestionnote.repository.MatiereRepository;
import com.pfe.gestionnote.service.MatiereService;
import com.pfe.gestionnote.service.dto.MatiereDTO;
import com.pfe.gestionnote.service.mapper.MatiereMapper;
import com.pfe.gestionnote.service.dto.MatiereCriteria;
import com.pfe.gestionnote.service.MatiereQueryService;

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
 * Integration tests for the {@link MatiereResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MatiereResourceIT {

    private static final Double DEFAULT_COEFFICIENT_MATIERE = 1D;
    private static final Double UPDATED_COEFFICIENT_MATIERE = 2D;
    private static final Double SMALLER_COEFFICIENT_MATIERE = 1D - 1D;

    private static final Double DEFAULT_COEFFICIENT_TP = 1D;
    private static final Double UPDATED_COEFFICIENT_TP = 2D;
    private static final Double SMALLER_COEFFICIENT_TP = 1D - 1D;

    private static final Double DEFAULT_COEFFICIENT_DC = 1D;
    private static final Double UPDATED_COEFFICIENT_DC = 2D;
    private static final Double SMALLER_COEFFICIENT_DC = 1D - 1D;

    private static final Double DEFAULT_COEFFICIENT_EXEM = 1D;
    private static final Double UPDATED_COEFFICIENT_EXEM = 2D;
    private static final Double SMALLER_COEFFICIENT_EXEM = 1D - 1D;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private MatiereMapper matiereMapper;

    @Autowired
    private MatiereService matiereService;

    @Autowired
    private MatiereQueryService matiereQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatiereMockMvc;

    private Matiere matiere;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matiere createEntity(EntityManager em) {
        Matiere matiere = new Matiere()
            .coefficientMatiere(DEFAULT_COEFFICIENT_MATIERE)
            .coefficientTp(DEFAULT_COEFFICIENT_TP)
            .coefficientDc(DEFAULT_COEFFICIENT_DC)
            .coefficientExem(DEFAULT_COEFFICIENT_EXEM)
            .designation(DEFAULT_DESIGNATION)
            .nom(DEFAULT_NOM);
        return matiere;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Matiere createUpdatedEntity(EntityManager em) {
        Matiere matiere = new Matiere()
            .coefficientMatiere(UPDATED_COEFFICIENT_MATIERE)
            .coefficientTp(UPDATED_COEFFICIENT_TP)
            .coefficientDc(UPDATED_COEFFICIENT_DC)
            .coefficientExem(UPDATED_COEFFICIENT_EXEM)
            .designation(UPDATED_DESIGNATION)
            .nom(UPDATED_NOM);
        return matiere;
    }

    @BeforeEach
    public void initTest() {
        matiere = createEntity(em);
    }

    @Test
    @Transactional
    public void createMatiere() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();
        // Create the Matiere
        MatiereDTO matiereDTO = matiereMapper.toDto(matiere);
        restMatiereMockMvc.perform(post("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isCreated());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate + 1);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getCoefficientMatiere()).isEqualTo(DEFAULT_COEFFICIENT_MATIERE);
        assertThat(testMatiere.getCoefficientTp()).isEqualTo(DEFAULT_COEFFICIENT_TP);
        assertThat(testMatiere.getCoefficientDc()).isEqualTo(DEFAULT_COEFFICIENT_DC);
        assertThat(testMatiere.getCoefficientExem()).isEqualTo(DEFAULT_COEFFICIENT_EXEM);
        assertThat(testMatiere.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testMatiere.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createMatiereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = matiereRepository.findAll().size();

        // Create the Matiere with an existing ID
        matiere.setId(1L);
        MatiereDTO matiereDTO = matiereMapper.toDto(matiere);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatiereMockMvc.perform(post("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMatieres() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList
        restMatiereMockMvc.perform(get("/api/matieres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matiere.getId().intValue())))
            .andExpect(jsonPath("$.[*].coefficientMatiere").value(hasItem(DEFAULT_COEFFICIENT_MATIERE.doubleValue())))
            .andExpect(jsonPath("$.[*].coefficientTp").value(hasItem(DEFAULT_COEFFICIENT_TP.doubleValue())))
            .andExpect(jsonPath("$.[*].coefficientDc").value(hasItem(DEFAULT_COEFFICIENT_DC.doubleValue())))
            .andExpect(jsonPath("$.[*].coefficientExem").value(hasItem(DEFAULT_COEFFICIENT_EXEM.doubleValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
    
    @Test
    @Transactional
    public void getMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", matiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(matiere.getId().intValue()))
            .andExpect(jsonPath("$.coefficientMatiere").value(DEFAULT_COEFFICIENT_MATIERE.doubleValue()))
            .andExpect(jsonPath("$.coefficientTp").value(DEFAULT_COEFFICIENT_TP.doubleValue()))
            .andExpect(jsonPath("$.coefficientDc").value(DEFAULT_COEFFICIENT_DC.doubleValue()))
            .andExpect(jsonPath("$.coefficientExem").value(DEFAULT_COEFFICIENT_EXEM.doubleValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }


    @Test
    @Transactional
    public void getMatieresByIdFiltering() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        Long id = matiere.getId();

        defaultMatiereShouldBeFound("id.equals=" + id);
        defaultMatiereShouldNotBeFound("id.notEquals=" + id);

        defaultMatiereShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMatiereShouldNotBeFound("id.greaterThan=" + id);

        defaultMatiereShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMatiereShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMatieresByCoefficientMatiereIsEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientMatiere equals to DEFAULT_COEFFICIENT_MATIERE
        defaultMatiereShouldBeFound("coefficientMatiere.equals=" + DEFAULT_COEFFICIENT_MATIERE);

        // Get all the matiereList where coefficientMatiere equals to UPDATED_COEFFICIENT_MATIERE
        defaultMatiereShouldNotBeFound("coefficientMatiere.equals=" + UPDATED_COEFFICIENT_MATIERE);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientMatiereIsNotEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientMatiere not equals to DEFAULT_COEFFICIENT_MATIERE
        defaultMatiereShouldNotBeFound("coefficientMatiere.notEquals=" + DEFAULT_COEFFICIENT_MATIERE);

        // Get all the matiereList where coefficientMatiere not equals to UPDATED_COEFFICIENT_MATIERE
        defaultMatiereShouldBeFound("coefficientMatiere.notEquals=" + UPDATED_COEFFICIENT_MATIERE);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientMatiereIsInShouldWork() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientMatiere in DEFAULT_COEFFICIENT_MATIERE or UPDATED_COEFFICIENT_MATIERE
        defaultMatiereShouldBeFound("coefficientMatiere.in=" + DEFAULT_COEFFICIENT_MATIERE + "," + UPDATED_COEFFICIENT_MATIERE);

        // Get all the matiereList where coefficientMatiere equals to UPDATED_COEFFICIENT_MATIERE
        defaultMatiereShouldNotBeFound("coefficientMatiere.in=" + UPDATED_COEFFICIENT_MATIERE);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientMatiereIsNullOrNotNull() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientMatiere is not null
        defaultMatiereShouldBeFound("coefficientMatiere.specified=true");

        // Get all the matiereList where coefficientMatiere is null
        defaultMatiereShouldNotBeFound("coefficientMatiere.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientMatiereIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientMatiere is greater than or equal to DEFAULT_COEFFICIENT_MATIERE
        defaultMatiereShouldBeFound("coefficientMatiere.greaterThanOrEqual=" + DEFAULT_COEFFICIENT_MATIERE);

        // Get all the matiereList where coefficientMatiere is greater than or equal to UPDATED_COEFFICIENT_MATIERE
        defaultMatiereShouldNotBeFound("coefficientMatiere.greaterThanOrEqual=" + UPDATED_COEFFICIENT_MATIERE);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientMatiereIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientMatiere is less than or equal to DEFAULT_COEFFICIENT_MATIERE
        defaultMatiereShouldBeFound("coefficientMatiere.lessThanOrEqual=" + DEFAULT_COEFFICIENT_MATIERE);

        // Get all the matiereList where coefficientMatiere is less than or equal to SMALLER_COEFFICIENT_MATIERE
        defaultMatiereShouldNotBeFound("coefficientMatiere.lessThanOrEqual=" + SMALLER_COEFFICIENT_MATIERE);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientMatiereIsLessThanSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientMatiere is less than DEFAULT_COEFFICIENT_MATIERE
        defaultMatiereShouldNotBeFound("coefficientMatiere.lessThan=" + DEFAULT_COEFFICIENT_MATIERE);

        // Get all the matiereList where coefficientMatiere is less than UPDATED_COEFFICIENT_MATIERE
        defaultMatiereShouldBeFound("coefficientMatiere.lessThan=" + UPDATED_COEFFICIENT_MATIERE);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientMatiereIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientMatiere is greater than DEFAULT_COEFFICIENT_MATIERE
        defaultMatiereShouldNotBeFound("coefficientMatiere.greaterThan=" + DEFAULT_COEFFICIENT_MATIERE);

        // Get all the matiereList where coefficientMatiere is greater than SMALLER_COEFFICIENT_MATIERE
        defaultMatiereShouldBeFound("coefficientMatiere.greaterThan=" + SMALLER_COEFFICIENT_MATIERE);
    }


    @Test
    @Transactional
    public void getAllMatieresByCoefficientTpIsEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientTp equals to DEFAULT_COEFFICIENT_TP
        defaultMatiereShouldBeFound("coefficientTp.equals=" + DEFAULT_COEFFICIENT_TP);

        // Get all the matiereList where coefficientTp equals to UPDATED_COEFFICIENT_TP
        defaultMatiereShouldNotBeFound("coefficientTp.equals=" + UPDATED_COEFFICIENT_TP);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientTpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientTp not equals to DEFAULT_COEFFICIENT_TP
        defaultMatiereShouldNotBeFound("coefficientTp.notEquals=" + DEFAULT_COEFFICIENT_TP);

        // Get all the matiereList where coefficientTp not equals to UPDATED_COEFFICIENT_TP
        defaultMatiereShouldBeFound("coefficientTp.notEquals=" + UPDATED_COEFFICIENT_TP);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientTpIsInShouldWork() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientTp in DEFAULT_COEFFICIENT_TP or UPDATED_COEFFICIENT_TP
        defaultMatiereShouldBeFound("coefficientTp.in=" + DEFAULT_COEFFICIENT_TP + "," + UPDATED_COEFFICIENT_TP);

        // Get all the matiereList where coefficientTp equals to UPDATED_COEFFICIENT_TP
        defaultMatiereShouldNotBeFound("coefficientTp.in=" + UPDATED_COEFFICIENT_TP);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientTpIsNullOrNotNull() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientTp is not null
        defaultMatiereShouldBeFound("coefficientTp.specified=true");

        // Get all the matiereList where coefficientTp is null
        defaultMatiereShouldNotBeFound("coefficientTp.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientTpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientTp is greater than or equal to DEFAULT_COEFFICIENT_TP
        defaultMatiereShouldBeFound("coefficientTp.greaterThanOrEqual=" + DEFAULT_COEFFICIENT_TP);

        // Get all the matiereList where coefficientTp is greater than or equal to UPDATED_COEFFICIENT_TP
        defaultMatiereShouldNotBeFound("coefficientTp.greaterThanOrEqual=" + UPDATED_COEFFICIENT_TP);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientTpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientTp is less than or equal to DEFAULT_COEFFICIENT_TP
        defaultMatiereShouldBeFound("coefficientTp.lessThanOrEqual=" + DEFAULT_COEFFICIENT_TP);

        // Get all the matiereList where coefficientTp is less than or equal to SMALLER_COEFFICIENT_TP
        defaultMatiereShouldNotBeFound("coefficientTp.lessThanOrEqual=" + SMALLER_COEFFICIENT_TP);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientTpIsLessThanSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientTp is less than DEFAULT_COEFFICIENT_TP
        defaultMatiereShouldNotBeFound("coefficientTp.lessThan=" + DEFAULT_COEFFICIENT_TP);

        // Get all the matiereList where coefficientTp is less than UPDATED_COEFFICIENT_TP
        defaultMatiereShouldBeFound("coefficientTp.lessThan=" + UPDATED_COEFFICIENT_TP);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientTpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientTp is greater than DEFAULT_COEFFICIENT_TP
        defaultMatiereShouldNotBeFound("coefficientTp.greaterThan=" + DEFAULT_COEFFICIENT_TP);

        // Get all the matiereList where coefficientTp is greater than SMALLER_COEFFICIENT_TP
        defaultMatiereShouldBeFound("coefficientTp.greaterThan=" + SMALLER_COEFFICIENT_TP);
    }


    @Test
    @Transactional
    public void getAllMatieresByCoefficientDcIsEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientDc equals to DEFAULT_COEFFICIENT_DC
        defaultMatiereShouldBeFound("coefficientDc.equals=" + DEFAULT_COEFFICIENT_DC);

        // Get all the matiereList where coefficientDc equals to UPDATED_COEFFICIENT_DC
        defaultMatiereShouldNotBeFound("coefficientDc.equals=" + UPDATED_COEFFICIENT_DC);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientDcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientDc not equals to DEFAULT_COEFFICIENT_DC
        defaultMatiereShouldNotBeFound("coefficientDc.notEquals=" + DEFAULT_COEFFICIENT_DC);

        // Get all the matiereList where coefficientDc not equals to UPDATED_COEFFICIENT_DC
        defaultMatiereShouldBeFound("coefficientDc.notEquals=" + UPDATED_COEFFICIENT_DC);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientDcIsInShouldWork() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientDc in DEFAULT_COEFFICIENT_DC or UPDATED_COEFFICIENT_DC
        defaultMatiereShouldBeFound("coefficientDc.in=" + DEFAULT_COEFFICIENT_DC + "," + UPDATED_COEFFICIENT_DC);

        // Get all the matiereList where coefficientDc equals to UPDATED_COEFFICIENT_DC
        defaultMatiereShouldNotBeFound("coefficientDc.in=" + UPDATED_COEFFICIENT_DC);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientDcIsNullOrNotNull() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientDc is not null
        defaultMatiereShouldBeFound("coefficientDc.specified=true");

        // Get all the matiereList where coefficientDc is null
        defaultMatiereShouldNotBeFound("coefficientDc.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientDcIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientDc is greater than or equal to DEFAULT_COEFFICIENT_DC
        defaultMatiereShouldBeFound("coefficientDc.greaterThanOrEqual=" + DEFAULT_COEFFICIENT_DC);

        // Get all the matiereList where coefficientDc is greater than or equal to UPDATED_COEFFICIENT_DC
        defaultMatiereShouldNotBeFound("coefficientDc.greaterThanOrEqual=" + UPDATED_COEFFICIENT_DC);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientDcIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientDc is less than or equal to DEFAULT_COEFFICIENT_DC
        defaultMatiereShouldBeFound("coefficientDc.lessThanOrEqual=" + DEFAULT_COEFFICIENT_DC);

        // Get all the matiereList where coefficientDc is less than or equal to SMALLER_COEFFICIENT_DC
        defaultMatiereShouldNotBeFound("coefficientDc.lessThanOrEqual=" + SMALLER_COEFFICIENT_DC);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientDcIsLessThanSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientDc is less than DEFAULT_COEFFICIENT_DC
        defaultMatiereShouldNotBeFound("coefficientDc.lessThan=" + DEFAULT_COEFFICIENT_DC);

        // Get all the matiereList where coefficientDc is less than UPDATED_COEFFICIENT_DC
        defaultMatiereShouldBeFound("coefficientDc.lessThan=" + UPDATED_COEFFICIENT_DC);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientDcIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientDc is greater than DEFAULT_COEFFICIENT_DC
        defaultMatiereShouldNotBeFound("coefficientDc.greaterThan=" + DEFAULT_COEFFICIENT_DC);

        // Get all the matiereList where coefficientDc is greater than SMALLER_COEFFICIENT_DC
        defaultMatiereShouldBeFound("coefficientDc.greaterThan=" + SMALLER_COEFFICIENT_DC);
    }


    @Test
    @Transactional
    public void getAllMatieresByCoefficientExemIsEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientExem equals to DEFAULT_COEFFICIENT_EXEM
        defaultMatiereShouldBeFound("coefficientExem.equals=" + DEFAULT_COEFFICIENT_EXEM);

        // Get all the matiereList where coefficientExem equals to UPDATED_COEFFICIENT_EXEM
        defaultMatiereShouldNotBeFound("coefficientExem.equals=" + UPDATED_COEFFICIENT_EXEM);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientExemIsNotEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientExem not equals to DEFAULT_COEFFICIENT_EXEM
        defaultMatiereShouldNotBeFound("coefficientExem.notEquals=" + DEFAULT_COEFFICIENT_EXEM);

        // Get all the matiereList where coefficientExem not equals to UPDATED_COEFFICIENT_EXEM
        defaultMatiereShouldBeFound("coefficientExem.notEquals=" + UPDATED_COEFFICIENT_EXEM);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientExemIsInShouldWork() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientExem in DEFAULT_COEFFICIENT_EXEM or UPDATED_COEFFICIENT_EXEM
        defaultMatiereShouldBeFound("coefficientExem.in=" + DEFAULT_COEFFICIENT_EXEM + "," + UPDATED_COEFFICIENT_EXEM);

        // Get all the matiereList where coefficientExem equals to UPDATED_COEFFICIENT_EXEM
        defaultMatiereShouldNotBeFound("coefficientExem.in=" + UPDATED_COEFFICIENT_EXEM);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientExemIsNullOrNotNull() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientExem is not null
        defaultMatiereShouldBeFound("coefficientExem.specified=true");

        // Get all the matiereList where coefficientExem is null
        defaultMatiereShouldNotBeFound("coefficientExem.specified=false");
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientExemIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientExem is greater than or equal to DEFAULT_COEFFICIENT_EXEM
        defaultMatiereShouldBeFound("coefficientExem.greaterThanOrEqual=" + DEFAULT_COEFFICIENT_EXEM);

        // Get all the matiereList where coefficientExem is greater than or equal to UPDATED_COEFFICIENT_EXEM
        defaultMatiereShouldNotBeFound("coefficientExem.greaterThanOrEqual=" + UPDATED_COEFFICIENT_EXEM);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientExemIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientExem is less than or equal to DEFAULT_COEFFICIENT_EXEM
        defaultMatiereShouldBeFound("coefficientExem.lessThanOrEqual=" + DEFAULT_COEFFICIENT_EXEM);

        // Get all the matiereList where coefficientExem is less than or equal to SMALLER_COEFFICIENT_EXEM
        defaultMatiereShouldNotBeFound("coefficientExem.lessThanOrEqual=" + SMALLER_COEFFICIENT_EXEM);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientExemIsLessThanSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientExem is less than DEFAULT_COEFFICIENT_EXEM
        defaultMatiereShouldNotBeFound("coefficientExem.lessThan=" + DEFAULT_COEFFICIENT_EXEM);

        // Get all the matiereList where coefficientExem is less than UPDATED_COEFFICIENT_EXEM
        defaultMatiereShouldBeFound("coefficientExem.lessThan=" + UPDATED_COEFFICIENT_EXEM);
    }

    @Test
    @Transactional
    public void getAllMatieresByCoefficientExemIsGreaterThanSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where coefficientExem is greater than DEFAULT_COEFFICIENT_EXEM
        defaultMatiereShouldNotBeFound("coefficientExem.greaterThan=" + DEFAULT_COEFFICIENT_EXEM);

        // Get all the matiereList where coefficientExem is greater than SMALLER_COEFFICIENT_EXEM
        defaultMatiereShouldBeFound("coefficientExem.greaterThan=" + SMALLER_COEFFICIENT_EXEM);
    }


    @Test
    @Transactional
    public void getAllMatieresByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where designation equals to DEFAULT_DESIGNATION
        defaultMatiereShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the matiereList where designation equals to UPDATED_DESIGNATION
        defaultMatiereShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllMatieresByDesignationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where designation not equals to DEFAULT_DESIGNATION
        defaultMatiereShouldNotBeFound("designation.notEquals=" + DEFAULT_DESIGNATION);

        // Get all the matiereList where designation not equals to UPDATED_DESIGNATION
        defaultMatiereShouldBeFound("designation.notEquals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllMatieresByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultMatiereShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the matiereList where designation equals to UPDATED_DESIGNATION
        defaultMatiereShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllMatieresByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where designation is not null
        defaultMatiereShouldBeFound("designation.specified=true");

        // Get all the matiereList where designation is null
        defaultMatiereShouldNotBeFound("designation.specified=false");
    }
                @Test
    @Transactional
    public void getAllMatieresByDesignationContainsSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where designation contains DEFAULT_DESIGNATION
        defaultMatiereShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the matiereList where designation contains UPDATED_DESIGNATION
        defaultMatiereShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllMatieresByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where designation does not contain DEFAULT_DESIGNATION
        defaultMatiereShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the matiereList where designation does not contain UPDATED_DESIGNATION
        defaultMatiereShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }


    @Test
    @Transactional
    public void getAllMatieresByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom equals to DEFAULT_NOM
        defaultMatiereShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the matiereList where nom equals to UPDATED_NOM
        defaultMatiereShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMatieresByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom not equals to DEFAULT_NOM
        defaultMatiereShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the matiereList where nom not equals to UPDATED_NOM
        defaultMatiereShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMatieresByNomIsInShouldWork() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultMatiereShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the matiereList where nom equals to UPDATED_NOM
        defaultMatiereShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMatieresByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom is not null
        defaultMatiereShouldBeFound("nom.specified=true");

        // Get all the matiereList where nom is null
        defaultMatiereShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllMatieresByNomContainsSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom contains DEFAULT_NOM
        defaultMatiereShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the matiereList where nom contains UPDATED_NOM
        defaultMatiereShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMatieresByNomNotContainsSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        // Get all the matiereList where nom does not contain DEFAULT_NOM
        defaultMatiereShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the matiereList where nom does not contain UPDATED_NOM
        defaultMatiereShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllMatieresByRegimeIsEqualToSomething() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);
        Regime regime = RegimeResourceIT.createEntity(em);
        em.persist(regime);
        em.flush();
        matiere.setRegime(regime);
        matiereRepository.saveAndFlush(matiere);
        Long regimeId = regime.getId();

        // Get all the matiereList where regime equals to regimeId
        defaultMatiereShouldBeFound("regimeId.equals=" + regimeId);

        // Get all the matiereList where regime equals to regimeId + 1
        defaultMatiereShouldNotBeFound("regimeId.equals=" + (regimeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMatiereShouldBeFound(String filter) throws Exception {
        restMatiereMockMvc.perform(get("/api/matieres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(matiere.getId().intValue())))
            .andExpect(jsonPath("$.[*].coefficientMatiere").value(hasItem(DEFAULT_COEFFICIENT_MATIERE.doubleValue())))
            .andExpect(jsonPath("$.[*].coefficientTp").value(hasItem(DEFAULT_COEFFICIENT_TP.doubleValue())))
            .andExpect(jsonPath("$.[*].coefficientDc").value(hasItem(DEFAULT_COEFFICIENT_DC.doubleValue())))
            .andExpect(jsonPath("$.[*].coefficientExem").value(hasItem(DEFAULT_COEFFICIENT_EXEM.doubleValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));

        // Check, that the count call also returns 1
        restMatiereMockMvc.perform(get("/api/matieres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMatiereShouldNotBeFound(String filter) throws Exception {
        restMatiereMockMvc.perform(get("/api/matieres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMatiereMockMvc.perform(get("/api/matieres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMatiere() throws Exception {
        // Get the matiere
        restMatiereMockMvc.perform(get("/api/matieres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // Update the matiere
        Matiere updatedMatiere = matiereRepository.findById(matiere.getId()).get();
        // Disconnect from session so that the updates on updatedMatiere are not directly saved in db
        em.detach(updatedMatiere);
        updatedMatiere
            .coefficientMatiere(UPDATED_COEFFICIENT_MATIERE)
            .coefficientTp(UPDATED_COEFFICIENT_TP)
            .coefficientDc(UPDATED_COEFFICIENT_DC)
            .coefficientExem(UPDATED_COEFFICIENT_EXEM)
            .designation(UPDATED_DESIGNATION)
            .nom(UPDATED_NOM);
        MatiereDTO matiereDTO = matiereMapper.toDto(updatedMatiere);

        restMatiereMockMvc.perform(put("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isOk());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate);
        Matiere testMatiere = matiereList.get(matiereList.size() - 1);
        assertThat(testMatiere.getCoefficientMatiere()).isEqualTo(UPDATED_COEFFICIENT_MATIERE);
        assertThat(testMatiere.getCoefficientTp()).isEqualTo(UPDATED_COEFFICIENT_TP);
        assertThat(testMatiere.getCoefficientDc()).isEqualTo(UPDATED_COEFFICIENT_DC);
        assertThat(testMatiere.getCoefficientExem()).isEqualTo(UPDATED_COEFFICIENT_EXEM);
        assertThat(testMatiere.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testMatiere.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingMatiere() throws Exception {
        int databaseSizeBeforeUpdate = matiereRepository.findAll().size();

        // Create the Matiere
        MatiereDTO matiereDTO = matiereMapper.toDto(matiere);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatiereMockMvc.perform(put("/api/matieres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(matiereDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Matiere in the database
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMatiere() throws Exception {
        // Initialize the database
        matiereRepository.saveAndFlush(matiere);

        int databaseSizeBeforeDelete = matiereRepository.findAll().size();

        // Delete the matiere
        restMatiereMockMvc.perform(delete("/api/matieres/{id}", matiere.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Matiere> matiereList = matiereRepository.findAll();
        assertThat(matiereList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
