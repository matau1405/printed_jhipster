package fr.inti.printed.web.rest;

import fr.inti.printed.PrintedJhipsterApp;
import fr.inti.printed.domain.Authentification;
import fr.inti.printed.repository.AuthentificationRepository;
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
 * Integration tests for the {@link AuthentificationResource} REST controller.
 */
@SpringBootTest(classes = PrintedJhipsterApp.class)
public class AuthentificationResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSEWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSEWORD = "BBBBBBBBBB";

    @Autowired
    private AuthentificationRepository authentificationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restAuthentificationMockMvc;

    private Authentification authentification;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuthentificationResource authentificationResource = new AuthentificationResource(authentificationRepository);
        this.restAuthentificationMockMvc = MockMvcBuilders.standaloneSetup(authentificationResource)
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
    public static Authentification createEntity() {
        Authentification authentification = new Authentification()
            .login(DEFAULT_LOGIN)
            .passeword(DEFAULT_PASSEWORD);
        return authentification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Authentification createUpdatedEntity() {
        Authentification authentification = new Authentification()
            .login(UPDATED_LOGIN)
            .passeword(UPDATED_PASSEWORD);
        return authentification;
    }

    @BeforeEach
    public void initTest() {
        authentificationRepository.deleteAll();
        authentification = createEntity();
    }

    @Test
    public void createAuthentification() throws Exception {
        int databaseSizeBeforeCreate = authentificationRepository.findAll().size();

        // Create the Authentification
        restAuthentificationMockMvc.perform(post("/api/authentifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authentification)))
            .andExpect(status().isCreated());

        // Validate the Authentification in the database
        List<Authentification> authentificationList = authentificationRepository.findAll();
        assertThat(authentificationList).hasSize(databaseSizeBeforeCreate + 1);
        Authentification testAuthentification = authentificationList.get(authentificationList.size() - 1);
        assertThat(testAuthentification.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testAuthentification.getPasseword()).isEqualTo(DEFAULT_PASSEWORD);
    }

    @Test
    public void createAuthentificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = authentificationRepository.findAll().size();

        // Create the Authentification with an existing ID
        authentification.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthentificationMockMvc.perform(post("/api/authentifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authentification)))
            .andExpect(status().isBadRequest());

        // Validate the Authentification in the database
        List<Authentification> authentificationList = authentificationRepository.findAll();
        assertThat(authentificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllAuthentifications() throws Exception {
        // Initialize the database
        authentificationRepository.save(authentification);

        // Get all the authentificationList
        restAuthentificationMockMvc.perform(get("/api/authentifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(authentification.getId())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].passeword").value(hasItem(DEFAULT_PASSEWORD)));
    }
    
    @Test
    public void getAuthentification() throws Exception {
        // Initialize the database
        authentificationRepository.save(authentification);

        // Get the authentification
        restAuthentificationMockMvc.perform(get("/api/authentifications/{id}", authentification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(authentification.getId()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.passeword").value(DEFAULT_PASSEWORD));
    }

    @Test
    public void getNonExistingAuthentification() throws Exception {
        // Get the authentification
        restAuthentificationMockMvc.perform(get("/api/authentifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuthentification() throws Exception {
        // Initialize the database
        authentificationRepository.save(authentification);

        int databaseSizeBeforeUpdate = authentificationRepository.findAll().size();

        // Update the authentification
        Authentification updatedAuthentification = authentificationRepository.findById(authentification.getId()).get();
        updatedAuthentification
            .login(UPDATED_LOGIN)
            .passeword(UPDATED_PASSEWORD);

        restAuthentificationMockMvc.perform(put("/api/authentifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuthentification)))
            .andExpect(status().isOk());

        // Validate the Authentification in the database
        List<Authentification> authentificationList = authentificationRepository.findAll();
        assertThat(authentificationList).hasSize(databaseSizeBeforeUpdate);
        Authentification testAuthentification = authentificationList.get(authentificationList.size() - 1);
        assertThat(testAuthentification.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testAuthentification.getPasseword()).isEqualTo(UPDATED_PASSEWORD);
    }

    @Test
    public void updateNonExistingAuthentification() throws Exception {
        int databaseSizeBeforeUpdate = authentificationRepository.findAll().size();

        // Create the Authentification

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthentificationMockMvc.perform(put("/api/authentifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(authentification)))
            .andExpect(status().isBadRequest());

        // Validate the Authentification in the database
        List<Authentification> authentificationList = authentificationRepository.findAll();
        assertThat(authentificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAuthentification() throws Exception {
        // Initialize the database
        authentificationRepository.save(authentification);

        int databaseSizeBeforeDelete = authentificationRepository.findAll().size();

        // Delete the authentification
        restAuthentificationMockMvc.perform(delete("/api/authentifications/{id}", authentification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Authentification> authentificationList = authentificationRepository.findAll();
        assertThat(authentificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Authentification.class);
        Authentification authentification1 = new Authentification();
        authentification1.setId("id1");
        Authentification authentification2 = new Authentification();
        authentification2.setId(authentification1.getId());
        assertThat(authentification1).isEqualTo(authentification2);
        authentification2.setId("id2");
        assertThat(authentification1).isNotEqualTo(authentification2);
        authentification1.setId(null);
        assertThat(authentification1).isNotEqualTo(authentification2);
    }
}
