package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.CandidateRequest;
import com.rbts.hrms.candidateonboarding.dto.CandidateResponse;
import com.rbts.hrms.candidateonboarding.repository.CandidateDetailsRepository;
import com.rbts.hrms.candidateonboarding.service.CandidateDetailsService;
import com.rbts.hrms.candidateonboarding.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CandidateDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CandidateDetailsResource.class);

    private static final String ENTITY_NAME = "candidateonboardingCandidateDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    FileStorageService storageService;

    @Autowired
    AppProperties appProperties;

    private final CandidateDetailsService candidateDetailsService;

    private final CandidateDetailsRepository candidateDetailsRepository;

    public CandidateDetailsResource(
        CandidateDetailsService candidateDetailsService,
        CandidateDetailsRepository candidateDetailsRepository
    ) {
        this.candidateDetailsService = candidateDetailsService;
        this.candidateDetailsRepository = candidateDetailsRepository;
    }

    /**
     * {@code POST  /candidate-details} : Create a new candidateDetails.
     *
     * @param candidateDetails the candidateDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidateDetails, or with status {@code 400 (Bad Request)} if the candidateDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/candidate-details")
    public ResponseEntity<CandidateResponse> createCandidateDetails(@RequestBody CandidateRequest candidateDetails)
        throws URISyntaxException {
        log.debug("REST request to save CandidateDetails : {}", candidateDetails);
        CandidateResponse result = null;
        try {
            result = candidateDetailsService.save(candidateDetails);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/candidate-details/" ))
            .body(result);
    }

    /**
     * {@code PUT  /candidate-details/:id} : Updates an existing candidateDetails.
     *
     * @param id the id of the candidateDetails to save.
     * @param candidateDetails the candidateDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateDetails,
     * or with status {@code 400 (Bad Request)} if the candidateDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the candidateDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */


    @PutMapping("/candidate-details/{id}")
    public ResponseEntity<CandidateResponse> updateCandidateDetails(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody CandidateRequest candidateDetails
    ) throws URISyntaxException {


        CandidateResponse result = candidateDetailsService.update(candidateDetails, id);
        return ResponseEntity
                .ok()
              //  .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateDetails.getId().toString()))
                .body(result);
    }


    /**
     * {@code GET  /candidate-details} : get all the candidateDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidateDetails in body.
     */
    @GetMapping("/candidate-details")
    public List<CandidateResponse> getAllCandidateDetails() {
        log.debug("REST request to get all CandidateDetails");

        try {
            return candidateDetailsService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * {@code GET  /candidate-details/:id} : get the "id" candidateDetails.
     *
     * @param id the id of the candidateDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidateDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/candidate-details/{id}")
    public ResponseEntity<CandidateResponse> getCandidateDetails(@PathVariable Long id) throws DataNotFoundException {
        log.debug("REST request to get CandidateDetails : {}", id);
        CandidateResponse  candidateDetails = candidateDetailsService.findOne(id);
        return ResponseEntity
                .ok()
                //  .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, candidateDetails.getId().toString()))
                .body(candidateDetails);
    }

    /**
     * {@code DELETE  /candidate-details/:id} : delete the "id" candidateDetails.
     *
     * @param id the id of the candidateDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/candidate-details/{id}")
    public ResponseEntity<Void> deleteCandidateDetails(@PathVariable Long id) {
        log.debug("REST request to delete CandidateDetails : {}", id);
        candidateDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }


    /**
     * upload resume api
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam(name = "file", required = false) MultipartFile file
    ) throws IOException {
            storageService.storeFile(file);
        return ResponseEntity.ok().body("file uploaded successfully");

}






}
