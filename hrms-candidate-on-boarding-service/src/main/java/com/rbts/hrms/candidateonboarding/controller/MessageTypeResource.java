package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.MessageType;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.MessageTypeRepository;
import com.rbts.hrms.candidateonboarding.service.MessageTypeService;
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
public class MessageTypeResource {

    private final Logger log = LoggerFactory.getLogger(MessageTypeResource.class);

    private static final String ENTITY_NAME = "candidateonboardingMessageType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageTypeService messageTypeService;

    private final MessageTypeRepository messageTypeRepository;

    public MessageTypeResource(MessageTypeService messageTypeService, MessageTypeRepository messageTypeRepository) {
        this.messageTypeService = messageTypeService;
        this.messageTypeRepository = messageTypeRepository;
    }

    /**
     * {@code POST  /message-types} : Create a new messageType.
     *
     * @param messageType the messageType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageType, or with status {@code 400 (Bad Request)} if the messageType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-types")
    public ResponseEntity<MessageType> createMessageType(@RequestBody MessageType messageType) throws URISyntaxException {
        log.debug("REST request to save MessageType : {}", messageType);
        if (messageType.getId() != null) {
            try {
                throw new BadRequestAlertException("A new messageType cannot already have an ID", ENTITY_NAME, "idexists");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        MessageType result = null;
        try {
            result = messageTypeService.save(messageType);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/message-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-types/:id} : Updates an existing messageType.
     *
     * @param id the id of the messageType to save.
     * @param messageType the messageType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageType,
     * or with status {@code 400 (Bad Request)} if the messageType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-types/{id}")
    public ResponseEntity<MessageType> updateMessageType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MessageType messageType
    ) throws URISyntaxException {
       messageType.setId(id);
        MessageType result = null;
        try {
            result = messageTypeService.update(messageType);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageType.getId().toString()))
            .body(result);
    }



    /**
     * {@code GET  /message-types} : get all the messageTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageTypes in body.
     */
    @GetMapping("/message-types")
    public List<MessageType> getAllMessageTypes() {
        log.debug("REST request to get all MessageTypes");
        try {
            return messageTypeService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code GET  /message-types/:id} : get the "id" messageType.
     *
     * @param id the id of the messageType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-types/{id}")
    public MessageType getMessageType(@PathVariable Long id) {
        log.debug("REST request to get MessageType : {}", id);
        MessageType messageType = null;
        try {
            messageType = messageTypeService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return messageType;
    }

    /**
     * {@code DELETE  /message-types/:id} : delete the "id" messageType.
     *
     * @param id the id of the messageType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-types/{id}")
    public ResponseEntity<Void> deleteMessageType(@PathVariable Long id) {
        log.debug("REST request to delete MessageType : {}", id);
        messageTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
