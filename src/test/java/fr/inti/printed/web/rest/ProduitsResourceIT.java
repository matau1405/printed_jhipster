package fr.inti.printed.web.rest;

import fr.inti.printed.PrintedJhipsterApp;
import fr.inti.printed.domain.Produits;
import fr.inti.printed.repository.ProduitsRepository;
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
 * Integration tests for the {@link ProduitsResource} REST controller.
 */
@SpringBootTest(classes = PrintedJhipsterApp.class)
public class ProduitsResourceIT {

    private static final String DEFAULT_ID_PROD = "AAAAAAAAAA";
    private static final String UPDATED_ID_PROD = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PROD = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PROD = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_PROD = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_PROD = "BBBBBBBBBB";

    private static final String DEFAULT_PRIX_PROD = "AAAAAAAAAA";
    private static final String UPDATED_PRIX_PROD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPO = false;
    private static final Boolean UPDATED_DISPO = true;

    private static final Long DEFAULT_STOCK = 1L;
    private static final Long UPDATED_STOCK = 2L;

    private static final String DEFAULT_MARQUE = "AAAAAAAAAA";
    private static final String UPDATED_MARQUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PERSONNALISABLE = false;
    private static final Boolean UPDATED_PERSONNALISABLE = true;

    private static final String DEFAULT_IMAGE_PROD = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PROD = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_PERSONNALISATION = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PERSONNALISATION = "BBBBBBBBBB";

    @Autowired
    private ProduitsRepository produitsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restProduitsMockMvc;

    private Produits produits;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProduitsResource produitsResource = new ProduitsResource(produitsRepository);
        this.restProduitsMockMvc = MockMvcBuilders.standaloneSetup(produitsResource)
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
    public static Produits createEntity() {
        Produits produits = new Produits()
            .idProd(DEFAULT_ID_PROD)
            .nomProd(DEFAULT_NOM_PROD)
            .descriptionProd(DEFAULT_DESCRIPTION_PROD)
            .prixProd(DEFAULT_PRIX_PROD)
            .dispo(DEFAULT_DISPO)
            .stock(DEFAULT_STOCK)
            .marque(DEFAULT_MARQUE)
            .personnalisable(DEFAULT_PERSONNALISABLE)
            .imageProd(DEFAULT_IMAGE_PROD)
            .imagePersonnalisation(DEFAULT_IMAGE_PERSONNALISATION);
        return produits;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produits createUpdatedEntity() {
        Produits produits = new Produits()
            .idProd(UPDATED_ID_PROD)
            .nomProd(UPDATED_NOM_PROD)
            .descriptionProd(UPDATED_DESCRIPTION_PROD)
            .prixProd(UPDATED_PRIX_PROD)
            .dispo(UPDATED_DISPO)
            .stock(UPDATED_STOCK)
            .marque(UPDATED_MARQUE)
            .personnalisable(UPDATED_PERSONNALISABLE)
            .imageProd(UPDATED_IMAGE_PROD)
            .imagePersonnalisation(UPDATED_IMAGE_PERSONNALISATION);
        return produits;
    }

    @BeforeEach
    public void initTest() {
        produitsRepository.deleteAll();
        produits = createEntity();
    }

    @Test
    public void createProduits() throws Exception {
        int databaseSizeBeforeCreate = produitsRepository.findAll().size();

        // Create the Produits
        restProduitsMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produits)))
            .andExpect(status().isCreated());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeCreate + 1);
        Produits testProduits = produitsList.get(produitsList.size() - 1);
        assertThat(testProduits.getIdProd()).isEqualTo(DEFAULT_ID_PROD);
        assertThat(testProduits.getNomProd()).isEqualTo(DEFAULT_NOM_PROD);
        assertThat(testProduits.getDescriptionProd()).isEqualTo(DEFAULT_DESCRIPTION_PROD);
        assertThat(testProduits.getPrixProd()).isEqualTo(DEFAULT_PRIX_PROD);
        assertThat(testProduits.isDispo()).isEqualTo(DEFAULT_DISPO);
        assertThat(testProduits.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testProduits.getMarque()).isEqualTo(DEFAULT_MARQUE);
        assertThat(testProduits.isPersonnalisable()).isEqualTo(DEFAULT_PERSONNALISABLE);
        assertThat(testProduits.getImageProd()).isEqualTo(DEFAULT_IMAGE_PROD);
        assertThat(testProduits.getImagePersonnalisation()).isEqualTo(DEFAULT_IMAGE_PERSONNALISATION);
    }

    @Test
    public void createProduitsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produitsRepository.findAll().size();

        // Create the Produits with an existing ID
        produits.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitsMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produits)))
            .andExpect(status().isBadRequest());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllProduits() throws Exception {
        // Initialize the database
        produitsRepository.save(produits);

        // Get all the produitsList
        restProduitsMockMvc.perform(get("/api/produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produits.getId())))
            .andExpect(jsonPath("$.[*].idProd").value(hasItem(DEFAULT_ID_PROD)))
            .andExpect(jsonPath("$.[*].nomProd").value(hasItem(DEFAULT_NOM_PROD)))
            .andExpect(jsonPath("$.[*].descriptionProd").value(hasItem(DEFAULT_DESCRIPTION_PROD)))
            .andExpect(jsonPath("$.[*].prixProd").value(hasItem(DEFAULT_PRIX_PROD)))
            .andExpect(jsonPath("$.[*].dispo").value(hasItem(DEFAULT_DISPO.booleanValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].marque").value(hasItem(DEFAULT_MARQUE)))
            .andExpect(jsonPath("$.[*].personnalisable").value(hasItem(DEFAULT_PERSONNALISABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].imageProd").value(hasItem(DEFAULT_IMAGE_PROD)))
            .andExpect(jsonPath("$.[*].imagePersonnalisation").value(hasItem(DEFAULT_IMAGE_PERSONNALISATION)));
    }
    
    @Test
    public void getProduits() throws Exception {
        // Initialize the database
        produitsRepository.save(produits);

        // Get the produits
        restProduitsMockMvc.perform(get("/api/produits/{id}", produits.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produits.getId()))
            .andExpect(jsonPath("$.idProd").value(DEFAULT_ID_PROD))
            .andExpect(jsonPath("$.nomProd").value(DEFAULT_NOM_PROD))
            .andExpect(jsonPath("$.descriptionProd").value(DEFAULT_DESCRIPTION_PROD))
            .andExpect(jsonPath("$.prixProd").value(DEFAULT_PRIX_PROD))
            .andExpect(jsonPath("$.dispo").value(DEFAULT_DISPO.booleanValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.intValue()))
            .andExpect(jsonPath("$.marque").value(DEFAULT_MARQUE))
            .andExpect(jsonPath("$.personnalisable").value(DEFAULT_PERSONNALISABLE.booleanValue()))
            .andExpect(jsonPath("$.imageProd").value(DEFAULT_IMAGE_PROD))
            .andExpect(jsonPath("$.imagePersonnalisation").value(DEFAULT_IMAGE_PERSONNALISATION));
    }

    @Test
    public void getNonExistingProduits() throws Exception {
        // Get the produits
        restProduitsMockMvc.perform(get("/api/produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProduits() throws Exception {
        // Initialize the database
        produitsRepository.save(produits);

        int databaseSizeBeforeUpdate = produitsRepository.findAll().size();

        // Update the produits
        Produits updatedProduits = produitsRepository.findById(produits.getId()).get();
        updatedProduits
            .idProd(UPDATED_ID_PROD)
            .nomProd(UPDATED_NOM_PROD)
            .descriptionProd(UPDATED_DESCRIPTION_PROD)
            .prixProd(UPDATED_PRIX_PROD)
            .dispo(UPDATED_DISPO)
            .stock(UPDATED_STOCK)
            .marque(UPDATED_MARQUE)
            .personnalisable(UPDATED_PERSONNALISABLE)
            .imageProd(UPDATED_IMAGE_PROD)
            .imagePersonnalisation(UPDATED_IMAGE_PERSONNALISATION);

        restProduitsMockMvc.perform(put("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduits)))
            .andExpect(status().isOk());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeUpdate);
        Produits testProduits = produitsList.get(produitsList.size() - 1);
        assertThat(testProduits.getIdProd()).isEqualTo(UPDATED_ID_PROD);
        assertThat(testProduits.getNomProd()).isEqualTo(UPDATED_NOM_PROD);
        assertThat(testProduits.getDescriptionProd()).isEqualTo(UPDATED_DESCRIPTION_PROD);
        assertThat(testProduits.getPrixProd()).isEqualTo(UPDATED_PRIX_PROD);
        assertThat(testProduits.isDispo()).isEqualTo(UPDATED_DISPO);
        assertThat(testProduits.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testProduits.getMarque()).isEqualTo(UPDATED_MARQUE);
        assertThat(testProduits.isPersonnalisable()).isEqualTo(UPDATED_PERSONNALISABLE);
        assertThat(testProduits.getImageProd()).isEqualTo(UPDATED_IMAGE_PROD);
        assertThat(testProduits.getImagePersonnalisation()).isEqualTo(UPDATED_IMAGE_PERSONNALISATION);
    }

    @Test
    public void updateNonExistingProduits() throws Exception {
        int databaseSizeBeforeUpdate = produitsRepository.findAll().size();

        // Create the Produits

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitsMockMvc.perform(put("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produits)))
            .andExpect(status().isBadRequest());

        // Validate the Produits in the database
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteProduits() throws Exception {
        // Initialize the database
        produitsRepository.save(produits);

        int databaseSizeBeforeDelete = produitsRepository.findAll().size();

        // Delete the produits
        restProduitsMockMvc.perform(delete("/api/produits/{id}", produits.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produits> produitsList = produitsRepository.findAll();
        assertThat(produitsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produits.class);
        Produits produits1 = new Produits();
        produits1.setId("id1");
        Produits produits2 = new Produits();
        produits2.setId(produits1.getId());
        assertThat(produits1).isEqualTo(produits2);
        produits2.setId("id2");
        assertThat(produits1).isNotEqualTo(produits2);
        produits1.setId(null);
        assertThat(produits1).isNotEqualTo(produits2);
    }
}
