package com.rbts.hrms.candidateonboarding.service;

import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.Attachment;

import com.rbts.hrms.candidateonboarding.entity.NotificationDetails;
import com.rbts.hrms.candidateonboarding.repository.AttachmentRepository;
import com.rbts.hrms.candidateonboarding.repository.NotificationDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Attachment}.
 */
@Service
@Transactional
public class AttachmentService {

    private final Logger log = LoggerFactory.getLogger(AttachmentService.class);

    private final AttachmentRepository attachmentRepository;

    @Autowired
    NotificationDetailsRepository notificationDetailsRepository;

    @Autowired
    AppProperties appProperties;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    /**
     * Save a attachment.
     *
     * @param attachment the entity to save.
     * @return the persisted entity.
     */
    public Attachment save(Attachment attachment) throws ResourceNotFoundException, DataIntegrityException {
        log.debug("Request to save Attachment : {}", attachment);
        if(attachment.getNotificationDetailsId()==null)
        {
            throw new DataIntegrityException(appProperties.getNotificationid());

        }else {
        NotificationDetails details=notificationDetailsRepository.getById(attachment.getNotificationDetailsId().getId());
        if(details!=null)
        {
            attachment.setNotificationDetailsId(details);
        }else {
         throw new ResourceNotFoundException(appProperties.getNotificationDetails());
        }}
        return attachmentRepository.save(attachment);
    }

    /**
     * Update a attachment.
     *
     * @param attachment the entity to save.
     * @return the persisted entity.
     */
    public Attachment update(Attachment attachment) throws DataNotFoundException {
        log.debug("Request to update Attachment : {}", attachment);
        Attachment a=attachmentRepository.getById(attachment.getId());
        if(a==null)
        {
            throw new DataNotFoundException(appProperties.getData());

        }else {
            return attachmentRepository.save(attachment);
        }


    }


    /**
     * Get all the attachments.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Attachment> findAll() throws DataNotFoundException {
        log.debug("Request to get all Attachments");
        List<Attachment> attachment=attachmentRepository.findAll();

        if(attachment.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else
        {
            return attachmentRepository.findAll();
        }

    }

    /**
     * Get one attachment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Attachment findOne(Long id) throws DataNotFoundException {

        Attachment attachment=attachmentRepository.getById(id);
        if(attachment==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return attachment;
        }


    }

    /**
     * Delete the attachment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Attachment : {}", id);
        attachmentRepository.deleteById(id);
    }
}
