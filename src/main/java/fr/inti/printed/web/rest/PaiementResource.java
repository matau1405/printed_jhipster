package fr.inti.printed.web.rest;

import fr.inti.printed.domain.Paiement;
import fr.inti.printed.repository.PaiementRepository;
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
 * REST controller for managing {@link fr.inti.printed.domain.Paiement}.
 */
@RestController
@RequestMapping("/api")
public class PaiementResource {

    private final Logger log = LoggerFactory.getLogger(PaiementResource.class);

    private static final String ENTITY_NAME = "paiement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaiementRepository paiementRepository;

    public PaiementResource(PaiementRepository paiementRepository) {
        this.paiementRepository = paiementRepository;
    }

    /**
     * {@code POST  /paiements} : Create a new paiement.
     *
     * @param paiement the paiement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paiement, or with status {@code 400 (Bad Request)} if the paiement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paiements")
    public ResponseEntity<Paiement> createPaiement(@RequestBody Paiement paiement) throws URISyntaxException {
        log.debug("REST request to save Paiement : {}", paiement);
        if (paiement.getId() != null) {
            throw new BadRequestAlertException("A new paiement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paiement result = paiementRepository.save(paiement);
        return ResponseEntity.created(new URI("/api/paiements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paiements} : Updates an existing paiement.
     *
     * @param paiement the paiement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paiement,
     * or with status {@code 400 (Bad Request)} if the paiement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paiement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paiements")
    public ResponseEntity<Paiement> updatePaiement(@RequestBody Paiement paiement) throws URISyntaxException {
        log.debug("REST request to update Paiement : {}", paiement);
        if (paiement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Paiement result = paiementRepository.save(paiement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paiement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paiements} : get all the paiements.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paiements in body.
     */
    @GetMapping("/paiements")
    public List<Paiement> getAllPaiements() {
        log.debug("REST request to get all Paiements");
        return paiementRepository.findAll();
    }

    /**
     * {@code GET  /paiements/:id} : get the "id" paiement.
     *
     * @param id the id of the paiement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paiement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paiements/{id}")
    public ResponseEntity<Paiement> getPaiement(@PathVariable String id) {
        log.debug("REST request to get Paiement : {}", id);
        Optional<Paiement> paiement = paiementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(paiement);
    }

    /**
     * {@code DELETE  /paiements/:id} : delete the "id" paiement.
     *
     * @param id the id of the paiement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paiements/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable String id) {
        log.debug("REST request to delete Paiement : {}", id);
        paiementRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
