package fr.inti.printed.web.rest;

import fr.inti.printed.PrintedJhipsterApp;
import fr.inti.printed.domain.Paiement;
import fr.inti.printed.repository.PaiementRepository;
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
 * Integration tests for the {@link PaiementResource} REST controller.
 */
@SpringBootTest(classes = PrintedJhipsterApp.class)
public class PaiementResourceIT {

    private static final String DEFAULT_POSSESEUR_CARTE = "AAAAAAAAAA";
    private static final String UPDATED_POSSESEUR_CARTE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_CARTE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CARTE = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMERO_CARTE = 1L;
    private static final Long UPDATED_NUMERO_CARTE = 2L;

    private static final String DEFAULT_DATE_EXP = "AAAAAAAAAA";
    private static final String UPDATED_DATE_EXP = "BBBBBBBBBB";

    private static final Long DEFAULT_CRYPOTOGRAMME = 1L;
    private static final Long UPDATED_CRYPOTOGRAMME = 2L;

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restPaiementMockMvc;

    private Paiement paiement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaiementResource paiementResource = new PaiementResource(paiementRepository);
        this.restPaiementMockMvc = MockMvcBuilders.standaloneSetup(paiementResource)
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
    public static Paiement createEntity() {
        Paiement paiement = new Paiement()
            .posseseurCarte(DEFAULT_POSSESEUR_CARTE)
            .typeCarte(DEFAULT_TYPE_CARTE)
            .numeroCarte(DEFAULT_NUMERO_CARTE)
            .dateExp(DEFAULT_DATE_EXP)
            .crypotogramme(DEFAULT_CRYPOTOGRAMME);
        return paiement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paiement createUpdatedEntity() {
        Paiement paiement = new Paiement()
            .posseseurCarte(UPDATED_POSSESEUR_CARTE)
            .typeCarte(UPDATED_TYPE_CARTE)
            .numeroCarte(UPDATED_NUMERO_CARTE)
            .dateExp(UPDATED_DATE_EXP)
            .crypotogramme(UPDATED_CRYPOTOGRAMME);
        return paiement;
    }

    @BeforeEach
    public void initTest() {
        paiementRepository.deleteAll();
        paiement = createEntity();
    }

    @Test
    public void createPaiement() throws Exception {
        int databaseSizeBeforeCreate = paiementRepository.findAll().size();

        // Create the Paiement
        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiement)))
            .andExpect(status().isCreated());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeCreate + 1);
        Paiement testPaiement = paiementList.get(paiementList.size() - 1);
        assertThat(testPaiement.getPosseseurCarte()).isEqualTo(DEFAULT_POSSESEUR_CARTE);
        assertThat(testPaiement.getTypeCarte()).isEqualTo(DEFAULT_TYPE_CARTE);
        assertThat(testPaiement.getNumeroCarte()).isEqualTo(DEFAULT_NUMERO_CARTE);
        assertThat(testPaiement.getDateExp()).isEqualTo(DEFAULT_DATE_EXP);
        assertThat(testPaiement.getCrypotogramme()).isEqualTo(DEFAULT_CRYPOTOGRAMME);
    }

    @Test
    public void createPaiementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paiementRepository.findAll().size();

        // Create the Paiement with an existing ID
        paiement.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaiementMockMvc.perform(post("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiement)))
            .andExpect(status().isBadRequest());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllPaiements() throws Exception {
        // Initialize the database
        paiementRepository.save(paiement);

        // Get all the paiementList
        restPaiementMockMvc.perform(get("/api/paiements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paiement.getId())))
            .andExpect(jsonPath("$.[*].posseseurCarte").value(hasItem(DEFAULT_POSSESEUR_CARTE)))
            .andExpect(jsonPath("$.[*].typeCarte").value(hasItem(DEFAULT_TYPE_CARTE)))
            .andExpect(jsonPath("$.[*].numeroCarte").value(hasItem(DEFAULT_NUMERO_CARTE.intValue())))
            .andExpect(jsonPath("$.[*].dateExp").value(hasItem(DEFAULT_DATE_EXP)))
            .andExpect(jsonPath("$.[*].crypotogramme").value(hasItem(DEFAULT_CRYPOTOGRAMME.intValue())));
    }
    
    @Test
    public void getPaiement() throws Exception {
        // Initialize the database
        paiementRepository.save(paiement);

        // Get the paiement
        restPaiementMockMvc.perform(get("/api/paiements/{id}", paiement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paiement.getId()))
            .andExpect(jsonPath("$.posseseurCarte").value(DEFAULT_POSSESEUR_CARTE))
            .andExpect(jsonPath("$.typeCarte").value(DEFAULT_TYPE_CARTE))
            .andExpect(jsonPath("$.numeroCarte").value(DEFAULT_NUMERO_CARTE.intValue()))
            .andExpect(jsonPath("$.dateExp").value(DEFAULT_DATE_EXP))
            .andExpect(jsonPath("$.crypotogramme").value(DEFAULT_CRYPOTOGRAMME.intValue()));
    }

    @Test
    public void getNonExistingPaiement() throws Exception {
        // Get the paiement
        restPaiementMockMvc.perform(get("/api/paiements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePaiement() throws Exception {
        // Initialize the database
        paiementRepository.save(paiement);

        int databaseSizeBeforeUpdate = paiementRepository.findAll().size();

        // Update the paiement
        Paiement updatedPaiement = paiementRepository.findById(paiement.getId()).get();
        updatedPaiement
            .posseseurCarte(UPDATED_POSSESEUR_CARTE)
            .typeCarte(UPDATED_TYPE_CARTE)
            .numeroCarte(UPDATED_NUMERO_CARTE)
            .dateExp(UPDATED_DATE_EXP)
            .crypotogramme(UPDATED_CRYPOTOGRAMME);

        restPaiementMockMvc.perform(put("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaiement)))
            .andExpect(status().isOk());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeUpdate);
        Paiement testPaiement = paiementList.get(paiementList.size() - 1);
        assertThat(testPaiement.getPosseseurCarte()).isEqualTo(UPDATED_POSSESEUR_CARTE);
        assertThat(testPaiement.getTypeCarte()).isEqualTo(UPDATED_TYPE_CARTE);
        assertThat(testPaiement.getNumeroCarte()).isEqualTo(UPDATED_NUMERO_CARTE);
        assertThat(testPaiement.getDateExp()).isEqualTo(UPDATED_DATE_EXP);
        assertThat(testPaiement.getCrypotogramme()).isEqualTo(UPDATED_CRYPOTOGRAMME);
    }

    @Test
    public void updateNonExistingPaiement() throws Exception {
        int databaseSizeBeforeUpdate = paiementRepository.findAll().size();

        // Create the Paiement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaiementMockMvc.perform(put("/api/paiements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paiement)))
            .andExpect(status().isBadRequest());

        // Validate the Paiement in the database
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePaiement() throws Exception {
        // Initialize the database
        paiementRepository.save(paiement);

        int databaseSizeBeforeDelete = paiementRepository.findAll().size();

        // Delete the paiement
        restPaiementMockMvc.perform(delete("/api/paiements/{id}", paiement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paiement> paiementList = paiementRepository.findAll();
        assertThat(paiementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paiement.class);
        Paiement paiement1 = new Paiement();
        paiement1.setId("id1");
        Paiement paiement2 = new Paiement();
        paiement2.setId(paiement1.getId());
        assertThat(paiement1).isEqualTo(paiement2);
        paiement2.setId("id2");
        assertThat(paiement1).isNotEqualTo(paiement2);
        paiement1.setId(null);
        assertThat(paiement1).isNotEqualTo(paiement2);
    }
}
