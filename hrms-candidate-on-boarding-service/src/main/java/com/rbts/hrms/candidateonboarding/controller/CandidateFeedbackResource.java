package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.CandidateFeedbackResponse;
import com.rbts.hrms.candidateonboarding.dto.FeedbackRequest;
import com.rbts.hrms.candidateonboarding.dto.FeedbackResponse;
import com.rbts.hrms.candidateonboarding.entity.CandidateFeedback;
import com.rbts.hrms.candidateonboarding.entity.InterviewLevel;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.CandidateFeedbackRepository;
import com.rbts.hrms.candidateonboarding.service.CandidateFeedbackService;
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
public class CandidateFeedbackResource {

    private final Logger log = LoggerFactory.getLogger(CandidateFeedbackResource.class);

    private static final String ENTITY_NAME = "candidateonboardingCandidateFeedback";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CandidateFeedbackService candidateFeedbackService;

    private final CandidateFeedbackRepository candidateFeedbackRepository;

    public CandidateFeedbackResource(
        CandidateFeedbackService candidateFeedbackService,
        CandidateFeedbackRepository candidateFeedbackRepository
    ) {
        this.candidateFeedbackService = candidateFeedbackService;
        this.candidateFeedbackRepository = candidateFeedbackRepository;
    }

    /**
     * {@code POST  /candidate-feedbacks} : Create a new candidateFeedback.
     *
     * @param candidateFeedback the candidateFeedback to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidateFeedback, or with status {@code 400 (Bad Request)} if the candidateFeedback has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidate-feedbacks")
    public ResponseEntity<FeedbackResponse> createCandidateFeedback(@RequestBody FeedbackRequest candidateFeedback)
        throws URISyntaxException {
        log.debug("REST request to save CandidateFeedback : {}", candidateFeedback);

        FeedbackResponse result = null;
        try {
            result = candidateFeedbackService.save(candidateFeedback);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/candidate-feedbacks/" ))
           // .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /candidate-feedbacks/:id} : Updates an existing candidateFeedback.
     *
     * @param id the id of the candidateFeedback to save.
     * @param candidateFeedback the candidateFeedback to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateFeedback,
     * or with status {@code 400 (Bad Request)} if the candidateFeedback is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidateFeedback couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/candidate-feedbacks/{id}")
    public ResponseEntity<FeedbackResponse> updateCandidateFeedback(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeedbackRequest candidateFeedback
    ) throws URISyntaxException {


        FeedbackResponse result = null;
        try {
            result = candidateFeedbackService.update(candidateFeedback, id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
                .ok()
                //  .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateDetails.getId().toString()))
                .body(result);
    }


    /**
     * {@code GET  /candidate-feedbacks} : get all the candidateFeedbacks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidateFeedbacks in body.
     */
    @GetMapping("/candidate-feedbacks")
    public List<FeedbackResponse> getAllCandidateFeedbacks() {
        log.debug("REST request to get all CandidateFeedbacks");

        try {
            return candidateFeedbackService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * get all the candidateFeedbacks with candidate details
     * @return
     */
    @GetMapping("/candidate-feedbacks/details")
    public List<CandidateFeedbackResponse> getAllDetails() {
        log.debug("REST request to get all CandidateFeedbacks");
        try {
            return candidateFeedbackService.findAllDetails();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * show the list of interview level for that candidate
     * @param candidateId
     * @return
     */
    @GetMapping("/candidate-feedbacks/level/{candidateId}")
    public List<InterviewLevel> getByInterviewLevel(@PathVariable Long candidateId) {
        log.debug("REST request to get all CandidateFeedbacks");
        return candidateFeedbackService.getByInterviewLevel(candidateId);
    }

    /**
     * get all the candidateFeedbacks with candidate details for one candidate
     * @param id pass candidate id for get details
     * @return
     */

    @GetMapping("/candidate-feedbacks/details/{id}")
    public CandidateFeedbackResponse getByIdDetails(@PathVariable Long id) {
        log.debug("REST request to get all CandidateFeedbacks");
        try {
            return candidateFeedbackService.findByIdDetails(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * {@code GET  /candidate-feedbacks/:id} : get the "id" candidateFeedback.
     *
     * @param id the id of the candidateFeedback to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidateFeedback, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidate-feedbacks/{id}")
    public ResponseEntity<FeedbackResponse> getCandidateFeedback(@PathVariable Long id) {
        log.debug("REST request to get CandidateFeedback : {}", id);
        FeedbackResponse candidateFeedback = null;
        try {
            candidateFeedback = candidateFeedbackService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
                .ok()
                //  .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateDetails.getId().toString()))
                .body(candidateFeedback);
    }

    /**
     * {@code DELETE  /candidate-feedbacks/:id} : delete the "id" candidateFeedback.
     *
     * @param id the id of the candidateFeedback to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/candidate-feedbacks/{id}")
    public ResponseEntity<Void> deleteCandidateFeedback(@PathVariable Long id) {
        log.debug("REST request to delete CandidateFeedback : {}", id);
        candidateFeedbackService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
