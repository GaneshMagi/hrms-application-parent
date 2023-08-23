package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.PanelSkillRequest;
import com.rbts.hrms.candidateonboarding.dto.PanelSkillResponse;
import com.rbts.hrms.candidateonboarding.entity.PanelSkill;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.PanelSkillRepository;
import com.rbts.hrms.candidateonboarding.service.PanelSkillService;
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
public class PanelSkillResource {

    private final Logger log = LoggerFactory.getLogger(PanelSkillResource.class);

    private static final String ENTITY_NAME = "candidateonboardingPanelSkill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PanelSkillService panelSkillService;

    private final PanelSkillRepository panelSkillRepository;

    public PanelSkillResource(PanelSkillService panelSkillService, PanelSkillRepository panelSkillRepository) {
        this.panelSkillService = panelSkillService;
        this.panelSkillRepository = panelSkillRepository;
    }

    /**
     * {@code POST  /panel-skills} : Create a new panelSkill.
     *
     * @param panelSkill the panelSkill to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new panelSkill, or with status {@code 400 (Bad Request)} if the panelSkill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/panel-skills")
    public List<PanelSkillResponse> createPanelSkill(@RequestBody PanelSkillRequest panelSkill) throws URISyntaxException {
        log.debug("REST request to save PanelSkill : {}", panelSkill);

        List<PanelSkillResponse> result = null;
        try {
            result = panelSkillService.save(panelSkill);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * {@code PUT  /panel-skills/:id} : Updates an existing panelSkill.
     *
     * @param id the id of the panelSkill to save.
     * @param panelSkill the panelSkill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated panelSkill,
     * or with status {@code 400 (Bad Request)} if the panelSkill is not valid,
     * or with status {@code 500 (Internal Server Error)} if the panelSkill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/panel-skills/{id}")
    public ResponseEntity<PanelSkillResponse> updatePanelSkill(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PanelSkillRequest panelSkill
    ) throws URISyntaxException {
        log.debug("REST request to update PanelSkill : {}, {}", id, panelSkill);


        PanelSkillResponse result = null;
        try {
            result = panelSkillService.update(panelSkill,id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .ok()
            .body(result);
    }


    /**
     * {@code GET  /panel-skills} : get all the panelSkills.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of panelSkills in body.
     */
    @GetMapping("/panel-skills")
    public List<PanelSkillResponse> getAllPanelSkills() {
        log.debug("REST request to get all PanelSkills");
        try {
            return panelSkillService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code GET  /panel-skills/:id} : get the "id" panelSkill.
     *
     * @param id the id of the panelSkill to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the panelSkill, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/panel-skills/{id}")
    public ResponseEntity<PanelSkillResponse> getPanelSkill(@PathVariable Long id) {
        log.debug("REST request to get PanelSkill : {}", id);
        PanelSkillResponse panelSkill = null;
        try {
            panelSkill = panelSkillService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
                .ok()
                //  .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, panelSkill.getId().toString()))
                .body(panelSkill);
    }




    @GetMapping("/panel-skills/get/{skill}")
    public List<PanelSkillResponse> getPanelSkill(@PathVariable String skill) {
        List<PanelSkillResponse> panelSkill = null;
        try {
            panelSkill = panelSkillService.findByName(skill);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return panelSkill;
    }

    /**
     * {@code DELETE  /panel-skills/:id} : delete the "id" panelSkill.
     *
     * @param id the id of the panelSkill to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/panel-skills/{id}")
    public ResponseEntity<Void> deletePanelSkill(@PathVariable Long id) {
        log.debug("REST request to delete PanelSkill : {}", id);
        panelSkillService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
