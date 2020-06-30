package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Corrige;
import com.pfe.gestionnote.domain.Enseignant;
import com.pfe.gestionnote.domain.Enveloppe;
import com.pfe.gestionnote.repository.CorrigeRepository;
import com.pfe.gestionnote.service.CorrigeService;
import com.pfe.gestionnote.service.dto.CorrigeDTO;
import com.pfe.gestionnote.service.mapper.CorrigeMapper;
import com.pfe.gestionnote.web.rest.errors.ExceptionTranslator;
import com.pfe.gestionnote.service.dto.CorrigeCriteria;
import com.pfe.gestionnote.service.CorrigeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pfe.gestionnote.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CorrigeResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
public class CorrigeResourceIT {

    @Autowired
    private CorrigeRepository corrigeRepository;

    @Autowired
    private CorrigeMapper corrigeMapper;

    @Autowired
    private CorrigeService corrigeService;

    @Autowired
    private CorrigeQueryService corrigeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCorrigeMockMvc;

    private Corrige corrige;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrigeResource corrigeResource = new CorrigeResource(corrigeService, corrigeQueryService);
        this.restCorrigeMockMvc = MockMvcBuilders.standaloneSetup(corrigeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corrige createEntity(EntityManager em) {
        Corrige corrige = new Corrige();
        return corrige;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corrige createUpdatedEntity(EntityManager em) {
        Corrige corrige = new Corrige();
        return corrige;
    }

    @BeforeEach
    public void initTest() {
        corrige = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrige() throws Exception {
        int databaseSizeBeforeCreate = corrigeRepository.findAll().size();

        // Create the Corrige
        CorrigeDTO corrigeDTO = corrigeMapper.toDto(corrige);
        restCorrigeMockMvc.perform(post("/api/corriges")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corrigeDTO)))
            .andExpect(status().isCreated());

        // Validate the Corrige in the database
        List<Corrige> corrigeList = corrigeRepository.findAll();
        assertThat(corrigeList).hasSize(databaseSizeBeforeCreate + 1);
        Corrige testCorrige = corrigeList.get(corrigeList.size() - 1);
    }

    @Test
    @Transactional
    public void createCorrigeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = corrigeRepository.findAll().size();

        // Create the Corrige with an existing ID
        corrige.setId(1L);
        CorrigeDTO corrigeDTO = corrigeMapper.toDto(corrige);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrigeMockMvc.perform(post("/api/corriges")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corrigeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Corrige in the database
        List<Corrige> corrigeList = corrigeRepository.findAll();
        assertThat(corrigeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCorriges() throws Exception {
        // Initialize the database
        corrigeRepository.saveAndFlush(corrige);

        // Get all the corrigeList
        restCorrigeMockMvc.perform(get("/api/corriges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corrige.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCorrige() throws Exception {
        // Initialize the database
        corrigeRepository.saveAndFlush(corrige);

        // Get the corrige
        restCorrigeMockMvc.perform(get("/api/corriges/{id}", corrige.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(corrige.getId().intValue()));
    }


    @Test
    @Transactional
    public void getCorrigesByIdFiltering() throws Exception {
        // Initialize the database
        corrigeRepository.saveAndFlush(corrige);

        Long id = corrige.getId();

        defaultCorrigeShouldBeFound("id.equals=" + id);
        defaultCorrigeShouldNotBeFound("id.notEquals=" + id);

        defaultCorrigeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCorrigeShouldNotBeFound("id.greaterThan=" + id);

        defaultCorrigeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCorrigeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCorrigesByEnseignantIsEqualToSomething() throws Exception {
        // Initialize the database
        corrigeRepository.saveAndFlush(corrige);
        Enseignant enseignant = EnseignantResourceIT.createEntity(em);
        em.persist(enseignant);
        em.flush();
        corrige.setEnseignant(enseignant);
        corrigeRepository.saveAndFlush(corrige);
        Long enseignantId = enseignant.getId();

        // Get all the corrigeList where enseignant equals to enseignantId
        defaultCorrigeShouldBeFound("enseignantId.equals=" + enseignantId);

        // Get all the corrigeList where enseignant equals to enseignantId + 1
        defaultCorrigeShouldNotBeFound("enseignantId.equals=" + (enseignantId + 1));
    }


    @Test
    @Transactional
    public void getAllCorrigesByEnveloppeIsEqualToSomething() throws Exception {
        // Initialize the database
        corrigeRepository.saveAndFlush(corrige);
        Enveloppe enveloppe = EnveloppeResourceIT.createEntity(em);
        em.persist(enveloppe);
        em.flush();
        corrige.setEnveloppe(enveloppe);
        corrigeRepository.saveAndFlush(corrige);
        Long enveloppeId = enveloppe.getId();

        // Get all the corrigeList where enveloppe equals to enveloppeId
        defaultCorrigeShouldBeFound("enveloppeId.equals=" + enveloppeId);

        // Get all the corrigeList where enveloppe equals to enveloppeId + 1
        defaultCorrigeShouldNotBeFound("enveloppeId.equals=" + (enveloppeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCorrigeShouldBeFound(String filter) throws Exception {
        restCorrigeMockMvc.perform(get("/api/corriges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corrige.getId().intValue())));

        // Check, that the count call also returns 1
        restCorrigeMockMvc.perform(get("/api/corriges/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCorrigeShouldNotBeFound(String filter) throws Exception {
        restCorrigeMockMvc.perform(get("/api/corriges?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCorrigeMockMvc.perform(get("/api/corriges/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCorrige() throws Exception {
        // Get the corrige
        restCorrigeMockMvc.perform(get("/api/corriges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrige() throws Exception {
        // Initialize the database
        corrigeRepository.saveAndFlush(corrige);

        int databaseSizeBeforeUpdate = corrigeRepository.findAll().size();

        // Update the corrige
        Corrige updatedCorrige = corrigeRepository.findById(corrige.getId()).get();
        // Disconnect from session so that the updates on updatedCorrige are not directly saved in db
        em.detach(updatedCorrige);
        CorrigeDTO corrigeDTO = corrigeMapper.toDto(updatedCorrige);

        restCorrigeMockMvc.perform(put("/api/corriges")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corrigeDTO)))
            .andExpect(status().isOk());

        // Validate the Corrige in the database
        List<Corrige> corrigeList = corrigeRepository.findAll();
        assertThat(corrigeList).hasSize(databaseSizeBeforeUpdate);
        Corrige testCorrige = corrigeList.get(corrigeList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrige() throws Exception {
        int databaseSizeBeforeUpdate = corrigeRepository.findAll().size();

        // Create the Corrige
        CorrigeDTO corrigeDTO = corrigeMapper.toDto(corrige);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorrigeMockMvc.perform(put("/api/corriges")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(corrigeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Corrige in the database
        List<Corrige> corrigeList = corrigeRepository.findAll();
        assertThat(corrigeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCorrige() throws Exception {
        // Initialize the database
        corrigeRepository.saveAndFlush(corrige);

        int databaseSizeBeforeDelete = corrigeRepository.findAll().size();

        // Delete the corrige
        restCorrigeMockMvc.perform(delete("/api/corriges/{id}", corrige.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Corrige> corrigeList = corrigeRepository.findAll();
        assertThat(corrigeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
