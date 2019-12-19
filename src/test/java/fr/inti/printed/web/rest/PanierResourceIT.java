package fr.inti.printed.web.rest;

import fr.inti.printed.PrintedJhipsterApp;
import fr.inti.printed.domain.Panier;
import fr.inti.printed.repository.PanierRepository;
import fr.inti.printed.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;


import java.util.List;

import static fr.inti.printed.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PanierResource} REST controller.
 */
@SpringBootTest(classes = PrintedJhipsterApp.class)
public class PanierResourceIT {

    private static final String DEFAULT_ID_PANIER = "AAAAAAAAAA";
    private static final String UPDATED_ID_PANIER = "BBBBBBBBBB";

    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restPanierMockMvc;

    private Panier panier;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PanierResource panierResource = new PanierResource(panierRepository);
        this.restPanierMockMvc = MockMvcBuilders.standaloneSetup(panierResource)
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
    public static Panier createEntity() {
        Panier panier = new Panier()
            .idPanier(DEFAULT_ID_PANIER);
        return panier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Panier createUpdatedEntity() {
        Panier panier = new Panier()
            .idPanier(UPDATED_ID_PANIER);
        return panier;
    }

    @BeforeEach
    public void initTest() {
        panierRepository.deleteAll();
        panier = createEntity();
    }

    @Test
    public void createPanier() throws Exception {
        int databaseSizeBeforeCreate = panierRepository.findAll().size();

        // Create the Panier
        restPanierMockMvc.perform(post("/api/paniers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(panier)))
            .andExpect(status().isCreated());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeCreate + 1);
        Panier testPanier = panierList.get(panierList.size() - 1);
        assertThat(testPanier.getIdPanier()).isEqualTo(DEFAULT_ID_PANIER);
    }

    @Test
    public void createPanierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = panierRepository.findAll().size();

        // Create the Panier with an existing ID
        panier.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPanierMockMvc.perform(post("/api/paniers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(panier)))
            .andExpect(status().isBadRequest());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllPaniers() throws Exception {
        // Initialize the database
        panierRepository.save(panier);

        // Get all the panierList
        restPanierMockMvc.perform(get("/api/paniers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(panier.getId())))
            .andExpect(jsonPath("$.[*].idPanier").value(hasItem(DEFAULT_ID_PANIER)));
    }
    
    @Test
    public void getPanier() throws Exception {
        // Initialize the database
        panierRepository.save(panier);

        // Get the panier
        restPanierMockMvc.perform(get("/api/paniers/{id}", panier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(panier.getId()))
            .andExpect(jsonPath("$.idPanier").value(DEFAULT_ID_PANIER));
    }

    @Test
    public void getNonExistingPanier() throws Exception {
        // Get the panier
        restPanierMockMvc.perform(get("/api/paniers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePanier() throws Exception {
        // Initialize the database
        panierRepository.save(panier);

        int databaseSizeBeforeUpdate = panierRepository.findAll().size();

        // Update the panier
        Panier updatedPanier = panierRepository.findById(panier.getId()).get();
        updatedPanier
            .idPanier(UPDATED_ID_PANIER);

        restPanierMockMvc.perform(put("/api/paniers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPanier)))
            .andExpect(status().isOk());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeUpdate);
        Panier testPanier = panierList.get(panierList.size() - 1);
        assertThat(testPanier.getIdPanier()).isEqualTo(UPDATED_ID_PANIER);
    }

    @Test
    public void updateNonExistingPanier() throws Exception {
        int databaseSizeBeforeUpdate = panierRepository.findAll().size();

        // Create the Panier

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPanierMockMvc.perform(put("/api/paniers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(panier)))
            .andExpect(status().isBadRequest());

        // Validate the Panier in the database
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePanier() throws Exception {
        // Initialize the database
        panierRepository.save(panier);

        int databaseSizeBeforeDelete = panierRepository.findAll().size();

        // Delete the panier
        restPanierMockMvc.perform(delete("/api/paniers/{id}", panier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Panier> panierList = panierRepository.findAll();
        assertThat(panierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Panier.class);
        Panier panier1 = new Panier();
        panier1.setId("id1");
        Panier panier2 = new Panier();
        panier2.setId(panier1.getId());
        assertThat(panier1).isEqualTo(panier2);
        panier2.setId("id2");
        assertThat(panier1).isNotEqualTo(panier2);
        panier1.setId(null);
        assertThat(panier1).isNotEqualTo(panier2);
    }
}
