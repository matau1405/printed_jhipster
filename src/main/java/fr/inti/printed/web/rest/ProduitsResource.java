package fr.inti.printed.web.rest;

import fr.inti.printed.domain.Produits;
import fr.inti.printed.repository.ProduitsRepository;
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
 * REST controller for managing {@link fr.inti.printed.domain.Produits}.
 */
@RestController
@RequestMapping("/api")
public class ProduitsResource {

    private final Logger log = LoggerFactory.getLogger(ProduitsResource.class);

    private static final String ENTITY_NAME = "produits";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitsRepository produitsRepository;

    public ProduitsResource(ProduitsRepository produitsRepository) {
        this.produitsRepository = produitsRepository;
    }

    /**
     * {@code POST  /produits} : Create a new produits.
     *
     * @param produits the produits to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produits, or with status {@code 400 (Bad Request)} if the produits has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produits")
    public ResponseEntity<Produits> createProduits(@RequestBody Produits produits) throws URISyntaxException {
        log.debug("REST request to save Produits : {}", produits);
        if (produits.getId() != null) {
            throw new BadRequestAlertException("A new produits cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Produits result = produitsRepository.save(produits);
        return ResponseEntity.created(new URI("/api/produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produits} : Updates an existing produits.
     *
     * @param produits the produits to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produits,
     * or with status {@code 400 (Bad Request)} if the produits is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produits couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produits")
    public ResponseEntity<Produits> updateProduits(@RequestBody Produits produits) throws URISyntaxException {
        log.debug("REST request to update Produits : {}", produits);
        if (produits.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Produits result = produitsRepository.save(produits);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, produits.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /produits} : get all the produits.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produits in body.
     */
    @GetMapping("/produits")
    public List<Produits> getAllProduits() {
        log.debug("REST request to get all Produits");
        return produitsRepository.findAll();
    }

    /**
     * {@code GET  /produits/:id} : get the "id" produits.
     *
     * @param id the id of the produits to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produits, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produits/{id}")
    public ResponseEntity<Produits> getProduits(@PathVariable String id) {
        log.debug("REST request to get Produits : {}", id);
        Optional<Produits> produits = produitsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(produits);
    }

    /**
     * {@code DELETE  /produits/:id} : delete the "id" produits.
     *
     * @param id the id of the produits to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produits/{id}")
    public ResponseEntity<Void> deleteProduits(@PathVariable String id) {
        log.debug("REST request to delete Produits : {}", id);
        produitsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
