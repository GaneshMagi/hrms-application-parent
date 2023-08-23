package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.OfferLetter;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.OfferLetterRepository;
import com.rbts.hrms.candidateonboarding.service.OfferLetterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class OfferLetterResource {

    private final Logger log = LoggerFactory.getLogger(OfferLetterResource.class);

    private static final String ENTITY_NAME = "candidateonboardingOfferLetter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfferLetterService offerLetterService;

    private final OfferLetterRepository offerLetterRepository;

    public OfferLetterResource(OfferLetterService offerLetterService, OfferLetterRepository offerLetterRepository) {
        this.offerLetterService = offerLetterService;
        this.offerLetterRepository = offerLetterRepository;
    }

    /**
     * {@code POST  /offer-letters} : Create a new offerLetter.
     *
     * @param offerLetter the offerLetter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offerLetter, or with status {@code 400 (Bad Request)} if the offerLetter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offer-letters")
    public ResponseEntity<OfferLetter> createOfferLetter(@RequestBody OfferLetter offerLetter) throws URISyntaxException {
        log.debug("REST request to save OfferLetter : {}", offerLetter);
        if (offerLetter.getId() != null) {
            try {
                throw new BadRequestAlertException("A new offerLetter cannot already have an ID", ENTITY_NAME, "idexists");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        OfferLetter result = null;
        try {
            result = offerLetterService.save(offerLetter);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/offer-letters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offer-letters/:id} : Updates an existing offerLetter.
     *
     * @param id the id of the offerLetter to save.
     * @param offerLetter the offerLetter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerLetter,
     * or with status {@code 400 (Bad Request)} if the offerLetter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offerLetter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offer-letters/{id}")
    public ResponseEntity<OfferLetter> updateOfferLetter(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OfferLetter offerLetter
    ) throws URISyntaxException {
        log.debug("REST request to update OfferLetter : {}, {}", id, offerLetter);
       offerLetter.setId(id);
        OfferLetter result = null;
        try {
            result = offerLetterService.update(offerLetter);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offerLetter.getId().toString()))
            .body(result);
    }


    /**
     * {@code GET  /offer-letters} : get all the offerLetters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offerLetters in body.
     */
    @GetMapping("/offer-letters")
    public List<OfferLetter> getAllOfferLetters() {
        log.debug("REST request to get all OfferLetters");
        try {
            return offerLetterService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code GET  /offer-letters/:id} : get the "id" offerLetter.
     *
     * @param id the id of the offerLetter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerLetter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offer-letters/{id}")
    public OfferLetter getOfferLetter(@PathVariable Long id) {
        log.debug("REST request to get OfferLetter : {}", id);
        OfferLetter offerLetter = null;
        try {
            offerLetter = offerLetterService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return offerLetter;
    }

    /**
     * {@code DELETE  /offer-letters/:id} : delete the "id" offerLetter.
     *
     * @param id the id of the offerLetter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offer-letters/{id}")
    public ResponseEntity<Void> deleteOfferLetter(@PathVariable Long id) {
        log.debug("REST request to delete OfferLetter : {}", id);
        offerLetterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
