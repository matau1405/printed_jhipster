package fr.inti.printed.web.rest;

import fr.inti.printed.domain.Authentification;
import fr.inti.printed.repository.AuthentificationRepository;
import fr.inti.printed.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.inti.printed.domain.Authentification}.
 */
@RestController
@RequestMapping("/api")
public class AuthentificationResource {

    private final Logger log = LoggerFactory.getLogger(AuthentificationResource.class);

    private static final String ENTITY_NAME = "authentification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuthentificationRepository authentificationRepository;

    public AuthentificationResource(AuthentificationRepository authentificationRepository) {
        this.authentificationRepository = authentificationRepository;
    }

    /**
     * {@code POST  /authentifications} : Create a new authentification.
     *
     * @param authentification the authentification to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new authentification, or with status {@code 400 (Bad Request)} if the authentification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/authentifications")
    public ResponseEntity<Authentification> createAuthentification(@RequestBody Authentification authentification) throws URISyntaxException {
        log.debug("REST request to save Authentification : {}", authentification);
        if (authentification.getId() != null) {
            throw new BadRequestAlertException("A new authentification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Authentification result = authentificationRepository.save(authentification);
        return ResponseEntity.created(new URI("/api/authentifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /authentifications} : Updates an existing authentification.
     *
     * @param authentification the authentification to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated authentification,
     * or with status {@code 400 (Bad Request)} if the authentification is not valid,
     * or with status {@code 500 (Internal Server Error)} if the authentification couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/authentifications")
    public ResponseEntity<Authentification> updateAuthentification(@RequestBody Authentification authentification) throws URISyntaxException {
        log.debug("REST request to update Authentification : {}", authentification);
        if (authentification.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Authentification result = authentificationRepository.save(authentification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, authentification.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /authentifications} : get all the authentifications.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of authentifications in body.
     */
    @GetMapping("/authentifications")
    public List<Authentification> getAllAuthentifications() {
        log.debug("REST request to get all Authentifications");
        return authentificationRepository.findAll();
    }

    /**
     * {@code GET  /authentifications/:id} : get the "id" authentification.
     *
     * @param id the id of the authentification to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the authentification, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/authentifications/{id}")
    public ResponseEntity<Authentification> getAuthentification(@PathVariable String id) {
        log.debug("REST request to get Authentification : {}", id);
        Optional<Authentification> authentification = authentificationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(authentification);
    }

    /**
     * {@code DELETE  /authentifications/:id} : delete the "id" authentification.
     *
     * @param id the id of the authentification to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/authentifications/{id}")
    public ResponseEntity<Void> deleteAuthentification(@PathVariable String id) {
        log.debug("REST request to delete Authentification : {}", id);
        authentificationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
