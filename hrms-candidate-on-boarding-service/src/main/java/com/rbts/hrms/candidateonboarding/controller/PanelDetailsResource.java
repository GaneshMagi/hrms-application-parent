package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.PanelDetails;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.PanelDetailsRepository;
import com.rbts.hrms.candidateonboarding.service.PanelDetailsService;
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
public class PanelDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PanelDetailsResource.class);

    private static final String ENTITY_NAME = "candidateonboardingPanelDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PanelDetailsService panelDetailsService;

    private final PanelDetailsRepository panelDetailsRepository;

    public PanelDetailsResource(PanelDetailsService panelDetailsService, PanelDetailsRepository panelDetailsRepository) {
        this.panelDetailsService = panelDetailsService;
        this.panelDetailsRepository = panelDetailsRepository;
    }

    /**
     * {@code POST  /panel-details} : Create a new panelDetails.
     *
     * @param panelDetails the panelDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new panelDetails, or with status {@code 400 (Bad Request)} if the panelDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/panel-details")
    public ResponseEntity<PanelDetails> createPanelDetails(@RequestBody PanelDetails panelDetails) throws URISyntaxException {
        log.debug("REST request to save PanelDetails : {}", panelDetails);
        if (panelDetails.getId() != null) {
            try {
                throw new BadRequestAlertException("A new panelDetails cannot already have an ID", ENTITY_NAME, "idexists");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        PanelDetails result = null;
        try {
            result = panelDetailsService.save(panelDetails);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/panel-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /panel-details/:id} : Updates an existing panelDetails.
     *
     * @param id the id of the panelDetails to save.
     * @param panelDetails the panelDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated panelDetails,
     * or with status {@code 400 (Bad Request)} if the panelDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the panelDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/panel-details/{id}")
    public ResponseEntity<PanelDetails> updatePanelDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PanelDetails panelDetails
    ) throws URISyntaxException {
        log.debug("REST request to update PanelDetails : {}, {}", id, panelDetails);
        if (panelDetails.getId() == null) {
            try {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Objects.equals(id, panelDetails.getId())) {
            try {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }

        if (!panelDetailsRepository.existsById(id)) {
            try {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }

        PanelDetails result = panelDetailsService.update(panelDetails);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, panelDetails.getId().toString()))
            .body(result);
    }



    /**
     * {@code GET  /panel-details} : get all the panelDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of panelDetails in body.
     */
    @GetMapping("/panel-details")
    public List<PanelDetails> getAllPanelDetails() {
        log.debug("REST request to get all PanelDetails");
        try {
            return panelDetailsService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code GET  /panel-details/:id} : get the "id" panelDetails.
     *
     * @param id the id of the panelDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the panelDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/panel-details/{id}")
    public PanelDetails getPanelDetails(@PathVariable Long id) {
        log.debug("REST request to get PanelDetails : {}", id);
        PanelDetails panelDetails = null;
        try {
            panelDetails = panelDetailsService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return panelDetails;
    }



    @GetMapping("/panel-details/get/{designation}")
    public List<PanelDetails> getPanelDetails(@PathVariable(name = "designation") String designation) {

        List<PanelDetails >panelDetails = null;
        try {
            panelDetails = panelDetailsService.findByDesignation(designation);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return panelDetails;
    }

    /**
     * {@code DELETE  /panel-details/:id} : delete the "id" panelDetails.
     *
     * @param id the id of the panelDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/panel-details/{id}")
    public ResponseEntity<Void> deletePanelDetails(@PathVariable Long id) {
        log.debug("REST request to delete PanelDetails : {}", id);
        panelDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
