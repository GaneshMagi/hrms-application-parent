package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.InterviewLevel;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.InterviewLevelRepository;
import com.rbts.hrms.candidateonboarding.service.InterviewLevelService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
public class InterviewLevelResource {

    private final Logger log = LoggerFactory.getLogger(InterviewLevelResource.class);

    private static final String ENTITY_NAME = "candidateonboardingInterviewLevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    AppProperties appProperties;

    private final InterviewLevelService interviewLevelService;

    private final InterviewLevelRepository interviewLevelRepository;

    public InterviewLevelResource(InterviewLevelService interviewLevelService, InterviewLevelRepository interviewLevelRepository) {
        this.interviewLevelService = interviewLevelService;
        this.interviewLevelRepository = interviewLevelRepository;
    }

    /**
     * {@code POST  /interview-levels} : Create a new interviewLevel.
     *
     * @param interviewLevel the interviewLevel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewLevel, or with status {@code 400 (Bad Request)} if the interviewLevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interview-levels")
    public ResponseEntity<InterviewLevel> createInterviewLevel(@RequestBody InterviewLevel interviewLevel) throws URISyntaxException {
        log.debug("REST request to save InterviewLevel : {}", interviewLevel);
        if (interviewLevel.getId() != null) {
            try {
                throw new BadRequestAlertException("A new interviewLevel cannot already have an ID", ENTITY_NAME, "idexists");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        InterviewLevel result = null;
        try {
            result = interviewLevelService.save(interviewLevel);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/interview-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interview-levels/:id} : Updates an existing interviewLevel.
     *
     * @param id the id of the interviewLevel to save.
     * @param interviewLevel the interviewLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewLevel,
     * or with status {@code 400 (Bad Request)} if the interviewLevel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interview-levels/{id}")
    public ResponseEntity<InterviewLevel> updateInterviewLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InterviewLevel interviewLevel
    ) throws URISyntaxException {
        log.debug("REST request to update InterviewLevel : {}, {}", id, interviewLevel);

        InterviewLevel result = null;
        try {
            interviewLevel.setId(id);
            result = interviewLevelService.update(interviewLevel,id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewLevel.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /interview-levels/:id} : Partial updates given fields of an existing interviewLevel, field will ignore if it is null
     *
     * @param id the id of the interviewLevel to save.
     * @param interviewLevel the interviewLevel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewLevel,
     * or with status {@code 400 (Bad Request)} if the interviewLevel is not valid,
     * or with status {@code 404 (Not Found)} if the interviewLevel is not found,
     * or with status {@code 500 (Internal Server Error)} if the interviewLevel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/interview-levels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InterviewLevel> partialUpdateInterviewLevel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InterviewLevel interviewLevel
    ) throws URISyntaxException {
        log.debug("REST request to partial update InterviewLevel partially : {}, {}", id, interviewLevel);
        if (interviewLevel.getId() == null) {
            try {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Objects.equals(id, interviewLevel.getId())) {
            try {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }

        if (!interviewLevelRepository.existsById(id)) {
            try {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }

        Optional<InterviewLevel> result = interviewLevelService.partialUpdate(interviewLevel);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interviewLevel.getId().toString())
        );
    }

    /**
     * {@code GET  /interview-levels} : get all the interviewLevels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviewLevels in body.
     */
    @GetMapping("/interview-levels")
    public List<InterviewLevel> getAllInterviewLevels() {
        log.debug("REST request to get all InterviewLevels");
        try {
            return interviewLevelService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code GET  /interview-levels/:id} : get the "id" interviewLevel.
     *
     * @param id the id of the interviewLevel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewLevel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interview-levels/{id}")
    public InterviewLevel getInterviewLevel(@PathVariable Long id) {
        log.debug("REST request to get InterviewLevel : {}", id);
        InterviewLevel interviewLevel = null;
        try {
            interviewLevel = interviewLevelService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return interviewLevel;
    }

    @GetMapping("/interview-levels/get/{name}")
    public InterviewLevel getByInterviewLevel(@PathVariable String name) {
        log.debug("REST request to get all CandidateFeedbacks");
        try {
            return interviewLevelService.getByInterviewLevel(name);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code DELETE  /interview-levels/:id} : delete the "id" interviewLevel.
     *
     * @param id the id of the interviewLevel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interview-levels/{id}")
    public ResponseEntity<String> deleteInterviewLevel(@PathVariable Long id) {
        log.debug("REST request to delete InterviewLevel : {}", id);
        try {
            interviewLevelService.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }



}
