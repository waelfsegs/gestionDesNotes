package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.GestionNotesFsegsApp;
import com.pfe.gestionnote.domain.Classe;
import com.pfe.gestionnote.domain.Specialite;
import com.pfe.gestionnote.domain.Niveau;
import com.pfe.gestionnote.repository.ClasseRepository;
import com.pfe.gestionnote.service.ClasseService;
import com.pfe.gestionnote.service.dto.ClasseDTO;
import com.pfe.gestionnote.service.mapper.ClasseMapper;
import com.pfe.gestionnote.service.dto.ClasseCriteria;
import com.pfe.gestionnote.service.ClasseQueryService;

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
 * Integration tests for the {@link ClasseResource} REST controller.
 */
@SpringBootTest(classes = GestionNotesFsegsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClasseResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private ClasseMapper classeMapper;

    @Autowired
    private ClasseService classeService;

    @Autowired
    private ClasseQueryService classeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClasseMockMvc;

    private Classe classe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classe createEntity(EntityManager em) {
        Classe classe = new Classe()
            .nom(DEFAULT_NOM);
        return classe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classe createUpdatedEntity(EntityManager em) {
        Classe classe = new Classe()
            .nom(UPDATED_NOM);
        return classe;
    }

    @BeforeEach
    public void initTest() {
        classe = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasse() throws Exception {
        int databaseSizeBeforeCreate = classeRepository.findAll().size();
        // Create the Classe
        ClasseDTO classeDTO = classeMapper.toDto(classe);
        restClasseMockMvc.perform(post("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isCreated());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeCreate + 1);
        Classe testClasse = classeList.get(classeList.size() - 1);
        assertThat(testClasse.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createClasseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classeRepository.findAll().size();

        // Create the Classe with an existing ID
        classe.setId(1L);
        ClasseDTO classeDTO = classeMapper.toDto(classe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasseMockMvc.perform(post("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClasses() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList
        restClasseMockMvc.perform(get("/api/classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
    
    @Test
    @Transactional
    public void getClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get the classe
        restClasseMockMvc.perform(get("/api/classes/{id}", classe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classe.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }


    @Test
    @Transactional
    public void getClassesByIdFiltering() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        Long id = classe.getId();

        defaultClasseShouldBeFound("id.equals=" + id);
        defaultClasseShouldNotBeFound("id.notEquals=" + id);

        defaultClasseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClasseShouldNotBeFound("id.greaterThan=" + id);

        defaultClasseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClasseShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClassesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where nom equals to DEFAULT_NOM
        defaultClasseShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the classeList where nom equals to UPDATED_NOM
        defaultClasseShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClassesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where nom not equals to DEFAULT_NOM
        defaultClasseShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the classeList where nom not equals to UPDATED_NOM
        defaultClasseShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClassesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultClasseShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the classeList where nom equals to UPDATED_NOM
        defaultClasseShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClassesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where nom is not null
        defaultClasseShouldBeFound("nom.specified=true");

        // Get all the classeList where nom is null
        defaultClasseShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllClassesByNomContainsSomething() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where nom contains DEFAULT_NOM
        defaultClasseShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the classeList where nom contains UPDATED_NOM
        defaultClasseShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllClassesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where nom does not contain DEFAULT_NOM
        defaultClasseShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the classeList where nom does not contain UPDATED_NOM
        defaultClasseShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllClassesBySpecialiteIsEqualToSomething() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);
        Specialite specialite = SpecialiteResourceIT.createEntity(em);
        em.persist(specialite);
        em.flush();
        classe.setSpecialite(specialite);
        classeRepository.saveAndFlush(classe);
        Long specialiteId = specialite.getId();

        // Get all the classeList where specialite equals to specialiteId
        defaultClasseShouldBeFound("specialiteId.equals=" + specialiteId);

        // Get all the classeList where specialite equals to specialiteId + 1
        defaultClasseShouldNotBeFound("specialiteId.equals=" + (specialiteId + 1));
    }


    @Test
    @Transactional
    public void getAllClassesByNiveauIsEqualToSomething() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);
        Niveau niveau = NiveauResourceIT.createEntity(em);
        em.persist(niveau);
        em.flush();
        classe.setNiveau(niveau);
        classeRepository.saveAndFlush(classe);
        Long niveauId = niveau.getId();

        // Get all the classeList where niveau equals to niveauId
        defaultClasseShouldBeFound("niveauId.equals=" + niveauId);

        // Get all the classeList where niveau equals to niveauId + 1
        defaultClasseShouldNotBeFound("niveauId.equals=" + (niveauId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClasseShouldBeFound(String filter) throws Exception {
        restClasseMockMvc.perform(get("/api/classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classe.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));

        // Check, that the count call also returns 1
        restClasseMockMvc.perform(get("/api/classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClasseShouldNotBeFound(String filter) throws Exception {
        restClasseMockMvc.perform(get("/api/classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClasseMockMvc.perform(get("/api/classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingClasse() throws Exception {
        // Get the classe
        restClasseMockMvc.perform(get("/api/classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        int databaseSizeBeforeUpdate = classeRepository.findAll().size();

        // Update the classe
        Classe updatedClasse = classeRepository.findById(classe.getId()).get();
        // Disconnect from session so that the updates on updatedClasse are not directly saved in db
        em.detach(updatedClasse);
        updatedClasse
            .nom(UPDATED_NOM);
        ClasseDTO classeDTO = classeMapper.toDto(updatedClasse);

        restClasseMockMvc.perform(put("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isOk());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeUpdate);
        Classe testClasse = classeList.get(classeList.size() - 1);
        assertThat(testClasse.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingClasse() throws Exception {
        int databaseSizeBeforeUpdate = classeRepository.findAll().size();

        // Create the Classe
        ClasseDTO classeDTO = classeMapper.toDto(classe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasseMockMvc.perform(put("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        int databaseSizeBeforeDelete = classeRepository.findAll().size();

        // Delete the classe
        restClasseMockMvc.perform(delete("/api/classes/{id}", classe.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
