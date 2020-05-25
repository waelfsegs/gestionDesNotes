package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Groupe;
import com.pfe.gestionnote.repository.GroupeRepository;
import com.pfe.gestionnote.service.GroupeService;
import com.pfe.gestionnote.service.dto.GroupeDTO;
import com.pfe.gestionnote.service.mapper.GroupeMapper;
import com.pfe.gestionnote.service.dto.GroupeCriteria;
import com.pfe.gestionnote.service.GroupeQueryService;

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
 * Integration tests for the {@link GroupeResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GroupeResourceIT {

    private static final String DEFAULT_NOMGROUP = "AAAAAAAAAA";
    private static final String UPDATED_NOMGROUP = "BBBBBBBBBB";

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private GroupeMapper groupeMapper;

    @Autowired
    private GroupeService groupeService;

    @Autowired
    private GroupeQueryService groupeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupeMockMvc;

    private Groupe groupe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groupe createEntity(EntityManager em) {
        Groupe groupe = new Groupe()
            .nomgroup(DEFAULT_NOMGROUP);
        return groupe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groupe createUpdatedEntity(EntityManager em) {
        Groupe groupe = new Groupe()
            .nomgroup(UPDATED_NOMGROUP);
        return groupe;
    }

    @BeforeEach
    public void initTest() {
        groupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupe() throws Exception {
        int databaseSizeBeforeCreate = groupeRepository.findAll().size();
        // Create the Groupe
        GroupeDTO groupeDTO = groupeMapper.toDto(groupe);
        restGroupeMockMvc.perform(post("/api/groupes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeDTO)))
            .andExpect(status().isCreated());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeCreate + 1);
        Groupe testGroupe = groupeList.get(groupeList.size() - 1);
        assertThat(testGroupe.getNomgroup()).isEqualTo(DEFAULT_NOMGROUP);
    }

    @Test
    @Transactional
    public void createGroupeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupeRepository.findAll().size();

        // Create the Groupe with an existing ID
        groupe.setId(1L);
        GroupeDTO groupeDTO = groupeMapper.toDto(groupe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupeMockMvc.perform(post("/api/groupes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGroupes() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get all the groupeList
        restGroupeMockMvc.perform(get("/api/groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomgroup").value(hasItem(DEFAULT_NOMGROUP)));
    }
    
    @Test
    @Transactional
    public void getGroupe() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get the groupe
        restGroupeMockMvc.perform(get("/api/groupes/{id}", groupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupe.getId().intValue()))
            .andExpect(jsonPath("$.nomgroup").value(DEFAULT_NOMGROUP));
    }


    @Test
    @Transactional
    public void getGroupesByIdFiltering() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        Long id = groupe.getId();

        defaultGroupeShouldBeFound("id.equals=" + id);
        defaultGroupeShouldNotBeFound("id.notEquals=" + id);

        defaultGroupeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGroupeShouldNotBeFound("id.greaterThan=" + id);

        defaultGroupeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGroupeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGroupesByNomgroupIsEqualToSomething() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get all the groupeList where nomgroup equals to DEFAULT_NOMGROUP
        defaultGroupeShouldBeFound("nomgroup.equals=" + DEFAULT_NOMGROUP);

        // Get all the groupeList where nomgroup equals to UPDATED_NOMGROUP
        defaultGroupeShouldNotBeFound("nomgroup.equals=" + UPDATED_NOMGROUP);
    }

    @Test
    @Transactional
    public void getAllGroupesByNomgroupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get all the groupeList where nomgroup not equals to DEFAULT_NOMGROUP
        defaultGroupeShouldNotBeFound("nomgroup.notEquals=" + DEFAULT_NOMGROUP);

        // Get all the groupeList where nomgroup not equals to UPDATED_NOMGROUP
        defaultGroupeShouldBeFound("nomgroup.notEquals=" + UPDATED_NOMGROUP);
    }

    @Test
    @Transactional
    public void getAllGroupesByNomgroupIsInShouldWork() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get all the groupeList where nomgroup in DEFAULT_NOMGROUP or UPDATED_NOMGROUP
        defaultGroupeShouldBeFound("nomgroup.in=" + DEFAULT_NOMGROUP + "," + UPDATED_NOMGROUP);

        // Get all the groupeList where nomgroup equals to UPDATED_NOMGROUP
        defaultGroupeShouldNotBeFound("nomgroup.in=" + UPDATED_NOMGROUP);
    }

    @Test
    @Transactional
    public void getAllGroupesByNomgroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get all the groupeList where nomgroup is not null
        defaultGroupeShouldBeFound("nomgroup.specified=true");

        // Get all the groupeList where nomgroup is null
        defaultGroupeShouldNotBeFound("nomgroup.specified=false");
    }
                @Test
    @Transactional
    public void getAllGroupesByNomgroupContainsSomething() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get all the groupeList where nomgroup contains DEFAULT_NOMGROUP
        defaultGroupeShouldBeFound("nomgroup.contains=" + DEFAULT_NOMGROUP);

        // Get all the groupeList where nomgroup contains UPDATED_NOMGROUP
        defaultGroupeShouldNotBeFound("nomgroup.contains=" + UPDATED_NOMGROUP);
    }

    @Test
    @Transactional
    public void getAllGroupesByNomgroupNotContainsSomething() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get all the groupeList where nomgroup does not contain DEFAULT_NOMGROUP
        defaultGroupeShouldNotBeFound("nomgroup.doesNotContain=" + DEFAULT_NOMGROUP);

        // Get all the groupeList where nomgroup does not contain UPDATED_NOMGROUP
        defaultGroupeShouldBeFound("nomgroup.doesNotContain=" + UPDATED_NOMGROUP);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGroupeShouldBeFound(String filter) throws Exception {
        restGroupeMockMvc.perform(get("/api/groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomgroup").value(hasItem(DEFAULT_NOMGROUP)));

        // Check, that the count call also returns 1
        restGroupeMockMvc.perform(get("/api/groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGroupeShouldNotBeFound(String filter) throws Exception {
        restGroupeMockMvc.perform(get("/api/groupes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGroupeMockMvc.perform(get("/api/groupes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGroupe() throws Exception {
        // Get the groupe
        restGroupeMockMvc.perform(get("/api/groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupe() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();

        // Update the groupe
        Groupe updatedGroupe = groupeRepository.findById(groupe.getId()).get();
        // Disconnect from session so that the updates on updatedGroupe are not directly saved in db
        em.detach(updatedGroupe);
        updatedGroupe
            .nomgroup(UPDATED_NOMGROUP);
        GroupeDTO groupeDTO = groupeMapper.toDto(updatedGroupe);

        restGroupeMockMvc.perform(put("/api/groupes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeDTO)))
            .andExpect(status().isOk());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
        Groupe testGroupe = groupeList.get(groupeList.size() - 1);
        assertThat(testGroupe.getNomgroup()).isEqualTo(UPDATED_NOMGROUP);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupe() throws Exception {
        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();

        // Create the Groupe
        GroupeDTO groupeDTO = groupeMapper.toDto(groupe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupeMockMvc.perform(put("/api/groupes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(groupeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupe() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        int databaseSizeBeforeDelete = groupeRepository.findAll().size();

        // Delete the groupe
        restGroupeMockMvc.perform(delete("/api/groupes/{id}", groupe.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
