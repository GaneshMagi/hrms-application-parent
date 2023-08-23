package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.NotificationDetails;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.NotificationDetailsRepository;
import com.rbts.hrms.candidateonboarding.service.NotificationDetailsService;
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
public class NotificationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(NotificationDetailsResource.class);

    private static final String ENTITY_NAME = "candidateonboardingNotificationDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationDetailsService notificationDetailsService;

    private final NotificationDetailsRepository notificationDetailsRepository;

    public NotificationDetailsResource(
        NotificationDetailsService notificationDetailsService,
        NotificationDetailsRepository notificationDetailsRepository
    ) {
        this.notificationDetailsService = notificationDetailsService;
        this.notificationDetailsRepository = notificationDetailsRepository;
    }

    /**
     * {@code POST  /notification-details} : Create a new notificationDetails.
     *
     * @param notificationDetails the notificationDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationDetails, or with status {@code 400 (Bad Request)} if the notificationDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification-details")
    public ResponseEntity<NotificationDetails> createNotificationDetails(@RequestBody NotificationDetails notificationDetails)
        throws URISyntaxException {
        log.debug("REST request to save NotificationDetails : {}", notificationDetails);
        if (notificationDetails.getId() != null) {
            try {
                throw new BadRequestAlertException("A new notificationDetails cannot already have an ID", ENTITY_NAME, "idexists");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        NotificationDetails result = null;
        try {
            result = notificationDetailsService.save(notificationDetails);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/notification-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notification-details/:id} : Updates an existing notificationDetails.
     *
     * @param id the id of the notificationDetails to save.
     * @param notificationDetails the notificationDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationDetails,
     * or with status {@code 400 (Bad Request)} if the notificationDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification-details/{id}")
    public ResponseEntity<NotificationDetails> updateNotificationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NotificationDetails notificationDetails
    ) throws URISyntaxException {
        notificationDetails.setId(id);

        NotificationDetails result = null;
        try {
            result = notificationDetailsService.update(notificationDetails);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificationDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notification-details/:id} : Partial updates given fields of an existing notificationDetails, field will ignore if it is null
     *
     * @param id the id of the notificationDetails to save.
     * @param notificationDetails the notificationDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationDetails,
     * or with status {@code 400 (Bad Request)} if the notificationDetails is not valid,
     * or with status {@code 404 (Not Found)} if the notificationDetails is not found,
     * or with status {@code 500 (Internal Server Error)} if the notificationDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notification-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotificationDetails> partialUpdateNotificationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NotificationDetails notificationDetails
    ) throws URISyntaxException {
        log.debug("REST request to partial update NotificationDetails partially : {}, {}", id, notificationDetails);
        if (notificationDetails.getId() == null) {
            try {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Objects.equals(id, notificationDetails.getId())) {
            try {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }

        if (!notificationDetailsRepository.existsById(id)) {
            try {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }

        Optional<NotificationDetails> result = notificationDetailsService.partialUpdate(notificationDetails);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificationDetails.getId().toString())
        );
    }

    /**
     * {@code GET  /notification-details} : get all the notificationDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificationDetails in body.
     */
    @GetMapping("/notification-details")
    public List<NotificationDetails> getAllNotificationDetails() {
        log.debug("REST request to get all NotificationDetails");
        try {
            return notificationDetailsService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/notification-details/get/{id}")
    public List<NotificationDetails> getAllNotificationDetailsByModule(@PathVariable (value = "id")Long id) {
        log.debug("REST request to get all NotificationDetails");
        try {
            return notificationDetailsService.findAllNotificaton(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * {@code GET  /notification-details/:id} : get the "id" notificationDetails.
     *
     * @param id the id of the notificationDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notification-details/{id}")
    public NotificationDetails getNotificationDetails(@PathVariable Long id) {
        log.debug("REST request to get NotificationDetails : {}", id);
        NotificationDetails notificationDetails = null;
        try {
            notificationDetails = notificationDetailsService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return notificationDetails;
    }

    /**
     * {@code DELETE  /notification-details/:id} : delete the "id" notificationDetails.
     *
     * @param id the id of the notificationDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notification-details/{id}")
    public ResponseEntity<Void> deleteNotificationDetails(@PathVariable Long id) {
        log.debug("REST request to delete NotificationDetails : {}", id);
        notificationDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
