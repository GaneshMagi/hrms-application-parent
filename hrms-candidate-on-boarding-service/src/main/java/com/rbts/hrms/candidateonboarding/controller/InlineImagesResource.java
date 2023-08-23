package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.InlineImages;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.InlineImagesRepository;
import com.rbts.hrms.candidateonboarding.service.InlineImagesService;
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
public class InlineImagesResource {

    private final Logger log = LoggerFactory.getLogger(InlineImagesResource.class);

    private static final String ENTITY_NAME = "candidateonboardingInlineImages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InlineImagesService inlineImagesService;

    private final InlineImagesRepository inlineImagesRepository;

    public InlineImagesResource(InlineImagesService inlineImagesService, InlineImagesRepository inlineImagesRepository) {
        this.inlineImagesService = inlineImagesService;
        this.inlineImagesRepository = inlineImagesRepository;
    }

    /**
     * {@code POST  /inline-images} : Create a new inlineImages.
     *
     * @param inlineImages the inlineImages to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inlineImages, or with status {@code 400 (Bad Request)} if the inlineImages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inline-images")
    public ResponseEntity<InlineImages> createInlineImages(@RequestBody InlineImages inlineImages) throws URISyntaxException {
        log.debug("REST request to save InlineImages : {}", inlineImages);
        if (inlineImages.getId() != null) {
            try {
                throw new BadRequestAlertException("A new inlineImages cannot already have an ID", ENTITY_NAME, "idexists");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        InlineImages result = null;
        try {
            result = inlineImagesService.save(inlineImages);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/inline-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inline-images/:id} : Updates an existing inlineImages.
     *
     * @param id the id of the inlineImages to save.
     * @param inlineImages the inlineImages to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inlineImages,
     * or with status {@code 400 (Bad Request)} if the inlineImages is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inlineImages couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inline-images/{id}")
    public ResponseEntity<InlineImages> updateInlineImages(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InlineImages inlineImages
    ) throws URISyntaxException {
        log.debug("REST request to update InlineImages : {}, {}", id, inlineImages);

        inlineImages.setId(id);
        InlineImages result = null;
        try {
            result = inlineImagesService.update(inlineImages);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inlineImages.getId().toString()))
            .body(result);
    }



    /**
     * {@code GET  /inline-images} : get all the inlineImages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inlineImages in body.
     */
    @GetMapping("/inline-images")
    public List<InlineImages> getAllInlineImages() {
        log.debug("REST request to get all InlineImages");
        try {
            return inlineImagesService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code GET  /inline-images/:id} : get the "id" inlineImages.
     *
     * @param id the id of the inlineImages to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inlineImages, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inline-images/{id}")
    public InlineImages getInlineImages(@PathVariable Long id) {
        log.debug("REST request to get InlineImages : {}", id);
        InlineImages inlineImages = inlineImagesService.findOne(id);
        return inlineImages;
    }

    /**
     * {@code DELETE  /inline-images/:id} : delete the "id" inlineImages.
     *
     * @param id the id of the inlineImages to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inline-images/{id}")
    public ResponseEntity<Void> deleteInlineImages(@PathVariable Long id) {
        log.debug("REST request to delete InlineImages : {}", id);
        inlineImagesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
