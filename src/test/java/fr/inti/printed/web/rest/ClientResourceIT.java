package fr.inti.printed.web.rest;

import fr.inti.printed.PrintedJhipsterApp;
import fr.inti.printed.domain.Client;
import fr.inti.printed.repository.ClientRepository;
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


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static fr.inti.printed.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClientResource} REST controller.
 */
@SpringBootTest(classes = PrintedJhipsterApp.class)
public class ClientResourceIT {

    private static final String DEFAULT_ID_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_ID_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_CLIENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_NAISSANCE_CLIENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISSANCE_CLIENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ADRESSE_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_VILLE_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_PAYS_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_LIST_COMMANDE = "AAAAAAAAAA";
    private static final String UPDATED_LIST_COMMANDE = "BBBBBBBBBB";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restClientMockMvc;

    private Client client;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientResource clientResource = new ClientResource(clientRepository);
        this.restClientMockMvc = MockMvcBuilders.standaloneSetup(clientResource)
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
    public static Client createEntity() {
        Client client = new Client()
            .idClient(DEFAULT_ID_CLIENT)
            .nomClient(DEFAULT_NOM_CLIENT)
            .prenomClient(DEFAULT_PRENOM_CLIENT)
            .dateNaissanceClient(DEFAULT_DATE_NAISSANCE_CLIENT)
            .adresseClient(DEFAULT_ADRESSE_CLIENT)
            .villeClient(DEFAULT_VILLE_CLIENT)
            .paysClient(DEFAULT_PAYS_CLIENT)
            .emailClient(DEFAULT_EMAIL_CLIENT)
            .listCommande(DEFAULT_LIST_COMMANDE);
        return client;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity() {
        Client client = new Client()
            .idClient(UPDATED_ID_CLIENT)
            .nomClient(UPDATED_NOM_CLIENT)
            .prenomClient(UPDATED_PRENOM_CLIENT)
            .dateNaissanceClient(UPDATED_DATE_NAISSANCE_CLIENT)
            .adresseClient(UPDATED_ADRESSE_CLIENT)
            .villeClient(UPDATED_VILLE_CLIENT)
            .paysClient(UPDATED_PAYS_CLIENT)
            .emailClient(UPDATED_EMAIL_CLIENT)
            .listCommande(UPDATED_LIST_COMMANDE);
        return client;
    }

    @BeforeEach
    public void initTest() {
        clientRepository.deleteAll();
        client = createEntity();
    }

    @Test
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getIdClient()).isEqualTo(DEFAULT_ID_CLIENT);
        assertThat(testClient.getNomClient()).isEqualTo(DEFAULT_NOM_CLIENT);
        assertThat(testClient.getPrenomClient()).isEqualTo(DEFAULT_PRENOM_CLIENT);
        assertThat(testClient.getDateNaissanceClient()).isEqualTo(DEFAULT_DATE_NAISSANCE_CLIENT);
        assertThat(testClient.getAdresseClient()).isEqualTo(DEFAULT_ADRESSE_CLIENT);
        assertThat(testClient.getVilleClient()).isEqualTo(DEFAULT_VILLE_CLIENT);
        assertThat(testClient.getPaysClient()).isEqualTo(DEFAULT_PAYS_CLIENT);
        assertThat(testClient.getEmailClient()).isEqualTo(DEFAULT_EMAIL_CLIENT);
        assertThat(testClient.getListCommande()).isEqualTo(DEFAULT_LIST_COMMANDE);
    }

    @Test
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkAdresseClientIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientRepository.findAll().size();
        // set the field null
        client.setAdresseClient(null);

        // Create the Client, which fails.

        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId())))
            .andExpect(jsonPath("$.[*].idClient").value(hasItem(DEFAULT_ID_CLIENT)))
            .andExpect(jsonPath("$.[*].nomClient").value(hasItem(DEFAULT_NOM_CLIENT)))
            .andExpect(jsonPath("$.[*].prenomClient").value(hasItem(DEFAULT_PRENOM_CLIENT)))
            .andExpect(jsonPath("$.[*].dateNaissanceClient").value(hasItem(DEFAULT_DATE_NAISSANCE_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].adresseClient").value(hasItem(DEFAULT_ADRESSE_CLIENT)))
            .andExpect(jsonPath("$.[*].villeClient").value(hasItem(DEFAULT_VILLE_CLIENT)))
            .andExpect(jsonPath("$.[*].paysClient").value(hasItem(DEFAULT_PAYS_CLIENT)))
            .andExpect(jsonPath("$.[*].emailClient").value(hasItem(DEFAULT_EMAIL_CLIENT)))
            .andExpect(jsonPath("$.[*].listCommande").value(hasItem(DEFAULT_LIST_COMMANDE)));
    }
    
    @Test
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId()))
            .andExpect(jsonPath("$.idClient").value(DEFAULT_ID_CLIENT))
            .andExpect(jsonPath("$.nomClient").value(DEFAULT_NOM_CLIENT))
            .andExpect(jsonPath("$.prenomClient").value(DEFAULT_PRENOM_CLIENT))
            .andExpect(jsonPath("$.dateNaissanceClient").value(DEFAULT_DATE_NAISSANCE_CLIENT.toString()))
            .andExpect(jsonPath("$.adresseClient").value(DEFAULT_ADRESSE_CLIENT))
            .andExpect(jsonPath("$.villeClient").value(DEFAULT_VILLE_CLIENT))
            .andExpect(jsonPath("$.paysClient").value(DEFAULT_PAYS_CLIENT))
            .andExpect(jsonPath("$.emailClient").value(DEFAULT_EMAIL_CLIENT))
            .andExpect(jsonPath("$.listCommande").value(DEFAULT_LIST_COMMANDE));
    }

    @Test
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateClient() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        updatedClient
            .idClient(UPDATED_ID_CLIENT)
            .nomClient(UPDATED_NOM_CLIENT)
            .prenomClient(UPDATED_PRENOM_CLIENT)
            .dateNaissanceClient(UPDATED_DATE_NAISSANCE_CLIENT)
            .adresseClient(UPDATED_ADRESSE_CLIENT)
            .villeClient(UPDATED_VILLE_CLIENT)
            .paysClient(UPDATED_PAYS_CLIENT)
            .emailClient(UPDATED_EMAIL_CLIENT)
            .listCommande(UPDATED_LIST_COMMANDE);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClient)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getIdClient()).isEqualTo(UPDATED_ID_CLIENT);
        assertThat(testClient.getNomClient()).isEqualTo(UPDATED_NOM_CLIENT);
        assertThat(testClient.getPrenomClient()).isEqualTo(UPDATED_PRENOM_CLIENT);
        assertThat(testClient.getDateNaissanceClient()).isEqualTo(UPDATED_DATE_NAISSANCE_CLIENT);
        assertThat(testClient.getAdresseClient()).isEqualTo(UPDATED_ADRESSE_CLIENT);
        assertThat(testClient.getVilleClient()).isEqualTo(UPDATED_VILLE_CLIENT);
        assertThat(testClient.getPaysClient()).isEqualTo(UPDATED_PAYS_CLIENT);
        assertThat(testClient.getEmailClient()).isEqualTo(UPDATED_EMAIL_CLIENT);
        assertThat(testClient.getListCommande()).isEqualTo(UPDATED_LIST_COMMANDE);
    }

    @Test
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Create the Client

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.save(client);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = new Client();
        client1.setId("id1");
        Client client2 = new Client();
        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);
        client2.setId("id2");
        assertThat(client1).isNotEqualTo(client2);
        client1.setId(null);
        assertThat(client1).isNotEqualTo(client2);
    }
}
