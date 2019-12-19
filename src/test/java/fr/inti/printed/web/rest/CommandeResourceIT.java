package fr.inti.printed.web.rest;

import fr.inti.printed.PrintedJhipsterApp;
import fr.inti.printed.domain.Commande;
import fr.inti.printed.repository.CommandeRepository;
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
 * Integration tests for the {@link CommandeResource} REST controller.
 */
@SpringBootTest(classes = PrintedJhipsterApp.class)
public class CommandeResourceIT {

    private static final String DEFAULT_ID_CMD = "AAAAAAAAAA";
    private static final String UPDATED_ID_CMD = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_CMD = "AAAAAAAAAA";
    private static final String UPDATED_DATE_CMD = "BBBBBBBBBB";

    private static final Float DEFAULT_MONTANT_CMD = 1F;
    private static final Float UPDATED_MONTANT_CMD = 2F;

    private static final Double DEFAULT_DELAI_LIVRAISON_CMD = 1D;
    private static final Double UPDATED_DELAI_LIVRAISON_CMD = 2D;

    private static final String DEFAULT_ETAT_LIVRAISON_CMD = "AAAAAAAAAA";
    private static final String UPDATED_ETAT_LIVRAISON_CMD = "BBBBBBBBBB";

    private static final String DEFAULT_LIEU_LIVRAISON_CMD = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_LIVRAISON_CMD = "BBBBBBBBBB";

    private static final String DEFAULT_MODE_LIVRAISON_CMD = "AAAAAAAAAA";
    private static final String UPDATED_MODE_LIVRAISON_CMD = "BBBBBBBBBB";

    private static final Float DEFAULT_PRIX_TOTAL_CMD = 1F;
    private static final Float UPDATED_PRIX_TOTAL_CMD = 2F;

    private static final String DEFAULT_MODE_PAIEMENT = "AAAAAAAAAA";
    private static final String UPDATED_MODE_PAIEMENT = "BBBBBBBBBB";

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCommandeMockMvc;

    private Commande commande;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommandeResource commandeResource = new CommandeResource(commandeRepository);
        this.restCommandeMockMvc = MockMvcBuilders.standaloneSetup(commandeResource)
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
    public static Commande createEntity() {
        Commande commande = new Commande()
            .idCmd(DEFAULT_ID_CMD)
            .dateCmd(DEFAULT_DATE_CMD)
            .montantCmd(DEFAULT_MONTANT_CMD)
            .delaiLivraisonCmd(DEFAULT_DELAI_LIVRAISON_CMD)
            .etatLivraisonCmd(DEFAULT_ETAT_LIVRAISON_CMD)
            .lieuLivraisonCmd(DEFAULT_LIEU_LIVRAISON_CMD)
            .modeLivraisonCmd(DEFAULT_MODE_LIVRAISON_CMD)
            .prixTotalCmd(DEFAULT_PRIX_TOTAL_CMD)
            .modePaiement(DEFAULT_MODE_PAIEMENT);
        return commande;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commande createUpdatedEntity() {
        Commande commande = new Commande()
            .idCmd(UPDATED_ID_CMD)
            .dateCmd(UPDATED_DATE_CMD)
            .montantCmd(UPDATED_MONTANT_CMD)
            .delaiLivraisonCmd(UPDATED_DELAI_LIVRAISON_CMD)
            .etatLivraisonCmd(UPDATED_ETAT_LIVRAISON_CMD)
            .lieuLivraisonCmd(UPDATED_LIEU_LIVRAISON_CMD)
            .modeLivraisonCmd(UPDATED_MODE_LIVRAISON_CMD)
            .prixTotalCmd(UPDATED_PRIX_TOTAL_CMD)
            .modePaiement(UPDATED_MODE_PAIEMENT);
        return commande;
    }

    @BeforeEach
    public void initTest() {
        commandeRepository.deleteAll();
        commande = createEntity();
    }

    @Test
    public void createCommande() throws Exception {
        int databaseSizeBeforeCreate = commandeRepository.findAll().size();

        // Create the Commande
        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isCreated());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeCreate + 1);
        Commande testCommande = commandeList.get(commandeList.size() - 1);
        assertThat(testCommande.getIdCmd()).isEqualTo(DEFAULT_ID_CMD);
        assertThat(testCommande.getDateCmd()).isEqualTo(DEFAULT_DATE_CMD);
        assertThat(testCommande.getMontantCmd()).isEqualTo(DEFAULT_MONTANT_CMD);
        assertThat(testCommande.getDelaiLivraisonCmd()).isEqualTo(DEFAULT_DELAI_LIVRAISON_CMD);
        assertThat(testCommande.getEtatLivraisonCmd()).isEqualTo(DEFAULT_ETAT_LIVRAISON_CMD);
        assertThat(testCommande.getLieuLivraisonCmd()).isEqualTo(DEFAULT_LIEU_LIVRAISON_CMD);
        assertThat(testCommande.getModeLivraisonCmd()).isEqualTo(DEFAULT_MODE_LIVRAISON_CMD);
        assertThat(testCommande.getPrixTotalCmd()).isEqualTo(DEFAULT_PRIX_TOTAL_CMD);
        assertThat(testCommande.getModePaiement()).isEqualTo(DEFAULT_MODE_PAIEMENT);
    }

    @Test
    public void createCommandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandeRepository.findAll().size();

        // Create the Commande with an existing ID
        commande.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandeMockMvc.perform(post("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isBadRequest());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllCommandes() throws Exception {
        // Initialize the database
        commandeRepository.save(commande);

        // Get all the commandeList
        restCommandeMockMvc.perform(get("/api/commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commande.getId())))
            .andExpect(jsonPath("$.[*].idCmd").value(hasItem(DEFAULT_ID_CMD)))
            .andExpect(jsonPath("$.[*].dateCmd").value(hasItem(DEFAULT_DATE_CMD)))
            .andExpect(jsonPath("$.[*].montantCmd").value(hasItem(DEFAULT_MONTANT_CMD.doubleValue())))
            .andExpect(jsonPath("$.[*].delaiLivraisonCmd").value(hasItem(DEFAULT_DELAI_LIVRAISON_CMD.doubleValue())))
            .andExpect(jsonPath("$.[*].etatLivraisonCmd").value(hasItem(DEFAULT_ETAT_LIVRAISON_CMD)))
            .andExpect(jsonPath("$.[*].lieuLivraisonCmd").value(hasItem(DEFAULT_LIEU_LIVRAISON_CMD)))
            .andExpect(jsonPath("$.[*].modeLivraisonCmd").value(hasItem(DEFAULT_MODE_LIVRAISON_CMD)))
            .andExpect(jsonPath("$.[*].prixTotalCmd").value(hasItem(DEFAULT_PRIX_TOTAL_CMD.doubleValue())))
            .andExpect(jsonPath("$.[*].modePaiement").value(hasItem(DEFAULT_MODE_PAIEMENT)));
    }
    
    @Test
    public void getCommande() throws Exception {
        // Initialize the database
        commandeRepository.save(commande);

        // Get the commande
        restCommandeMockMvc.perform(get("/api/commandes/{id}", commande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commande.getId()))
            .andExpect(jsonPath("$.idCmd").value(DEFAULT_ID_CMD))
            .andExpect(jsonPath("$.dateCmd").value(DEFAULT_DATE_CMD))
            .andExpect(jsonPath("$.montantCmd").value(DEFAULT_MONTANT_CMD.doubleValue()))
            .andExpect(jsonPath("$.delaiLivraisonCmd").value(DEFAULT_DELAI_LIVRAISON_CMD.doubleValue()))
            .andExpect(jsonPath("$.etatLivraisonCmd").value(DEFAULT_ETAT_LIVRAISON_CMD))
            .andExpect(jsonPath("$.lieuLivraisonCmd").value(DEFAULT_LIEU_LIVRAISON_CMD))
            .andExpect(jsonPath("$.modeLivraisonCmd").value(DEFAULT_MODE_LIVRAISON_CMD))
            .andExpect(jsonPath("$.prixTotalCmd").value(DEFAULT_PRIX_TOTAL_CMD.doubleValue()))
            .andExpect(jsonPath("$.modePaiement").value(DEFAULT_MODE_PAIEMENT));
    }

    @Test
    public void getNonExistingCommande() throws Exception {
        // Get the commande
        restCommandeMockMvc.perform(get("/api/commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCommande() throws Exception {
        // Initialize the database
        commandeRepository.save(commande);

        int databaseSizeBeforeUpdate = commandeRepository.findAll().size();

        // Update the commande
        Commande updatedCommande = commandeRepository.findById(commande.getId()).get();
        updatedCommande
            .idCmd(UPDATED_ID_CMD)
            .dateCmd(UPDATED_DATE_CMD)
            .montantCmd(UPDATED_MONTANT_CMD)
            .delaiLivraisonCmd(UPDATED_DELAI_LIVRAISON_CMD)
            .etatLivraisonCmd(UPDATED_ETAT_LIVRAISON_CMD)
            .lieuLivraisonCmd(UPDATED_LIEU_LIVRAISON_CMD)
            .modeLivraisonCmd(UPDATED_MODE_LIVRAISON_CMD)
            .prixTotalCmd(UPDATED_PRIX_TOTAL_CMD)
            .modePaiement(UPDATED_MODE_PAIEMENT);

        restCommandeMockMvc.perform(put("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommande)))
            .andExpect(status().isOk());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeUpdate);
        Commande testCommande = commandeList.get(commandeList.size() - 1);
        assertThat(testCommande.getIdCmd()).isEqualTo(UPDATED_ID_CMD);
        assertThat(testCommande.getDateCmd()).isEqualTo(UPDATED_DATE_CMD);
        assertThat(testCommande.getMontantCmd()).isEqualTo(UPDATED_MONTANT_CMD);
        assertThat(testCommande.getDelaiLivraisonCmd()).isEqualTo(UPDATED_DELAI_LIVRAISON_CMD);
        assertThat(testCommande.getEtatLivraisonCmd()).isEqualTo(UPDATED_ETAT_LIVRAISON_CMD);
        assertThat(testCommande.getLieuLivraisonCmd()).isEqualTo(UPDATED_LIEU_LIVRAISON_CMD);
        assertThat(testCommande.getModeLivraisonCmd()).isEqualTo(UPDATED_MODE_LIVRAISON_CMD);
        assertThat(testCommande.getPrixTotalCmd()).isEqualTo(UPDATED_PRIX_TOTAL_CMD);
        assertThat(testCommande.getModePaiement()).isEqualTo(UPDATED_MODE_PAIEMENT);
    }

    @Test
    public void updateNonExistingCommande() throws Exception {
        int databaseSizeBeforeUpdate = commandeRepository.findAll().size();

        // Create the Commande

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommandeMockMvc.perform(put("/api/commandes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commande)))
            .andExpect(status().isBadRequest());

        // Validate the Commande in the database
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCommande() throws Exception {
        // Initialize the database
        commandeRepository.save(commande);

        int databaseSizeBeforeDelete = commandeRepository.findAll().size();

        // Delete the commande
        restCommandeMockMvc.perform(delete("/api/commandes/{id}", commande.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commande> commandeList = commandeRepository.findAll();
        assertThat(commandeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commande.class);
        Commande commande1 = new Commande();
        commande1.setId("id1");
        Commande commande2 = new Commande();
        commande2.setId(commande1.getId());
        assertThat(commande1).isEqualTo(commande2);
        commande2.setId("id2");
        assertThat(commande1).isNotEqualTo(commande2);
        commande1.setId(null);
        assertThat(commande1).isNotEqualTo(commande2);
    }
}
