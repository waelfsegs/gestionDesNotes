package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Regime;
import com.pfe.gestionnote.repository.RegimeRepository;
import com.pfe.gestionnote.service.RegimeService;
import com.pfe.gestionnote.service.dto.RegimeDTO;
import com.pfe.gestionnote.service.mapper.RegimeMapper;
import com.pfe.gestionnote.service.dto.RegimeCriteria;
import com.pfe.gestionnote.service.RegimeQueryService;

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
 * Integration tests for the {@link RegimeResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RegimeResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private RegimeRepository regimeRepository;

    @Autowired
    private RegimeMapper regimeMapper;

    @Autowired
    private RegimeService regimeService;

    @Autowired
    private RegimeQueryService regimeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegimeMockMvc;

    private Regime regime;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regime createEntity(EntityManager em) {
        Regime regime = new Regime()
            .type(DEFAULT_TYPE);
        return regime;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Regime createUpdatedEntity(EntityManager em) {
        Regime regime = new Regime()
            .type(UPDATED_TYPE);
        return regime;
    }

    @BeforeEach
    public void initTest() {
        regime = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegime() throws Exception {
        int databaseSizeBeforeCreate = regimeRepository.findAll().size();
        // Create the Regime
        RegimeDTO regimeDTO = regimeMapper.toDto(regime);
        restRegimeMockMvc.perform(post("/api/regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regimeDTO)))
            .andExpect(status().isCreated());

        // Validate the Regime in the database
        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeCreate + 1);
        Regime testRegime = regimeList.get(regimeList.size() - 1);
        assertThat(testRegime.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createRegimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regimeRepository.findAll().size();

        // Create the Regime with an existing ID
        regime.setId(1L);
        RegimeDTO regimeDTO = regimeMapper.toDto(regime);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegimeMockMvc.perform(post("/api/regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Regime in the database
        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRegimes() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        // Get all the regimeList
        restRegimeMockMvc.perform(get("/api/regimes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regime.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }
    
    @Test
    @Transactional
    public void getRegime() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        // Get the regime
        restRegimeMockMvc.perform(get("/api/regimes/{id}", regime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regime.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }


    @Test
    @Transactional
    public void getRegimesByIdFiltering() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        Long id = regime.getId();

        defaultRegimeShouldBeFound("id.equals=" + id);
        defaultRegimeShouldNotBeFound("id.notEquals=" + id);

        defaultRegimeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRegimeShouldNotBeFound("id.greaterThan=" + id);

        defaultRegimeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRegimeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRegimesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        // Get all the regimeList where type equals to DEFAULT_TYPE
        defaultRegimeShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the regimeList where type equals to UPDATED_TYPE
        defaultRegimeShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllRegimesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        // Get all the regimeList where type not equals to DEFAULT_TYPE
        defaultRegimeShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the regimeList where type not equals to UPDATED_TYPE
        defaultRegimeShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllRegimesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        // Get all the regimeList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultRegimeShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the regimeList where type equals to UPDATED_TYPE
        defaultRegimeShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllRegimesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        // Get all the regimeList where type is not null
        defaultRegimeShouldBeFound("type.specified=true");

        // Get all the regimeList where type is null
        defaultRegimeShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllRegimesByTypeContainsSomething() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        // Get all the regimeList where type contains DEFAULT_TYPE
        defaultRegimeShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the regimeList where type contains UPDATED_TYPE
        defaultRegimeShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllRegimesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        // Get all the regimeList where type does not contain DEFAULT_TYPE
        defaultRegimeShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the regimeList where type does not contain UPDATED_TYPE
        defaultRegimeShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegimeShouldBeFound(String filter) throws Exception {
        restRegimeMockMvc.perform(get("/api/regimes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regime.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restRegimeMockMvc.perform(get("/api/regimes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegimeShouldNotBeFound(String filter) throws Exception {
        restRegimeMockMvc.perform(get("/api/regimes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegimeMockMvc.perform(get("/api/regimes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRegime() throws Exception {
        // Get the regime
        restRegimeMockMvc.perform(get("/api/regimes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegime() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        int databaseSizeBeforeUpdate = regimeRepository.findAll().size();

        // Update the regime
        Regime updatedRegime = regimeRepository.findById(regime.getId()).get();
        // Disconnect from session so that the updates on updatedRegime are not directly saved in db
        em.detach(updatedRegime);
        updatedRegime
            .type(UPDATED_TYPE);
        RegimeDTO regimeDTO = regimeMapper.toDto(updatedRegime);

        restRegimeMockMvc.perform(put("/api/regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regimeDTO)))
            .andExpect(status().isOk());

        // Validate the Regime in the database
        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeUpdate);
        Regime testRegime = regimeList.get(regimeList.size() - 1);
        assertThat(testRegime.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingRegime() throws Exception {
        int databaseSizeBeforeUpdate = regimeRepository.findAll().size();

        // Create the Regime
        RegimeDTO regimeDTO = regimeMapper.toDto(regime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegimeMockMvc.perform(put("/api/regimes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(regimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Regime in the database
        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegime() throws Exception {
        // Initialize the database
        regimeRepository.saveAndFlush(regime);

        int databaseSizeBeforeDelete = regimeRepository.findAll().size();

        // Delete the regime
        restRegimeMockMvc.perform(delete("/api/regimes/{id}", regime.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Regime> regimeList = regimeRepository.findAll();
        assertThat(regimeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
