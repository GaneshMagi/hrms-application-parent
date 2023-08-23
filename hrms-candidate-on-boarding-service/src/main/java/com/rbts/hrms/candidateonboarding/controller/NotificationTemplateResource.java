package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.CompanyProfile;
import com.rbts.hrms.candidateonboarding.entity.NotificationTemplate;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.NotificationTemplateRepository;
import com.rbts.hrms.candidateonboarding.service.CompanyProfileServices;
import com.rbts.hrms.candidateonboarding.service.NotificationTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class NotificationTemplateResource {

    private final Logger log = LoggerFactory.getLogger(NotificationTemplateResource.class);

    private static final String ENTITY_NAME = "candidateonboardingNotificationTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    @Value("${upload.folder.template}")
    private String uploadFolder;
    private final NotificationTemplateService notificationTemplateService;

    private final NotificationTemplateRepository notificationTemplateRepository;

    @Autowired
    CompanyProfileServices companyProfileServices;

    public NotificationTemplateResource(
        NotificationTemplateService notificationTemplateService,
        NotificationTemplateRepository notificationTemplateRepository
    ) {
        this.notificationTemplateService = notificationTemplateService;
        this.notificationTemplateRepository = notificationTemplateRepository;
    }

    /**
     * {@code POST  /notification-templates} : Create a new notificationTemplate.
     *
     * @param notificationTemplate the notificationTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationTemplate, or with status {@code 400 (Bad Request)} if the notificationTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification-templates")
    public ResponseEntity<NotificationTemplate> createNotificationTemplate(@RequestBody NotificationTemplate notificationTemplate)
        throws URISyntaxException {
        log.debug("REST request to save NotificationTemplate : {}", notificationTemplate);
        if (notificationTemplate.getId() != null) {
            try {
                throw new BadRequestAlertException("A new notificationTemplate cannot already have an ID", ENTITY_NAME, "idexists");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        NotificationTemplate result = null;
        try {
            result = notificationTemplateService.save(notificationTemplate);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/notification-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notification-templates/:id} : Updates an existing notificationTemplate.
     *
     * @param id the id of the notificationTemplate to save.
     * @param notificationTemplate the notificationTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationTemplate,
     * or with status {@code 400 (Bad Request)} if the notificationTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification-templates/{id}")
    public ResponseEntity<NotificationTemplate> updateNotificationTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NotificationTemplate notificationTemplate
    ) throws URISyntaxException {
        log.debug("REST request to update NotificationTemplate : {}, {}", id, notificationTemplate);
       notificationTemplate.setId(id);

        NotificationTemplate result = null;
        try {
            result = notificationTemplateService.update(notificationTemplate);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificationTemplate.getId().toString()))
            .body(result);
    }


    /**
     * {@code GET  /notification-templates} : get all the notificationTemplates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificationTemplates in body.
     */
    @GetMapping("/notification-templates")
    public List<NotificationTemplate> getAllNotificationTemplates() {
        log.debug("REST request to get all NotificationTemplates");
        try {
            return notificationTemplateService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code GET  /notification-templates/:id} : get the "id" notificationTemplate.
     *
     * @param id the id of the notificationTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notification-templates/{id}")
    public NotificationTemplate getNotificationTemplate(@PathVariable Long id) {
        log.debug("REST request to get NotificationTemplate : {}", id);
        NotificationTemplate notificationTemplate = null;
        try {
            notificationTemplate = notificationTemplateService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return notificationTemplate;
    }

    /**
     * {@code DELETE  /notification-templates/:id} : delete the "id" notificationTemplate.
     *
     * @param id the id of the notificationTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notification-templates/{id}")
    public ResponseEntity<Void> deleteNotificationTemplate(@PathVariable Long id) {
        log.debug("REST request to delete NotificationTemplate : {}", id);
        notificationTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }





    @PostMapping("/upload/template/{id}")
    public @ResponseBody String uploadFile(@RequestParam("file") MultipartFile file, @PathVariable(name = "id")Long id) {
        try {
            // Generate a unique filename for storing in the file system
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            // Save the file to the specified folder
            String fileUrl = uploadFolder + "/" + fileName;
            companyProfileServices.saveFile(uploadFolder, fileName, file);

            // Save the file URL to the database
            NotificationTemplate notificationTemplate=notificationTemplateRepository.getById(id);
            notificationTemplate.setId(id);
            notificationTemplate.setVelocityFile(fileUrl);
          notificationTemplateService.update(notificationTemplate);

            return "File uploaded successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file.";
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
