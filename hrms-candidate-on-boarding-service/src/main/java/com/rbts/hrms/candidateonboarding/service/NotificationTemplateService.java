package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.MessageType;
import com.rbts.hrms.candidateonboarding.entity.NotificationTemplate;
import com.rbts.hrms.candidateonboarding.entity.Status;
import com.rbts.hrms.candidateonboarding.repository.MessageTypeRepository;
import com.rbts.hrms.candidateonboarding.repository.NotificationTemplateRepository;
import com.rbts.hrms.candidateonboarding.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link NotificationTemplate}.
 */
@Service
@Transactional
public class NotificationTemplateService {

    private final Logger log = LoggerFactory.getLogger(NotificationTemplateService.class);

    private final NotificationTemplateRepository notificationTemplateRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    MessageTypeRepository messageTypeRepository;

    @Autowired
    AppProperties appProperties;

    public NotificationTemplateService(NotificationTemplateRepository notificationTemplateRepository) {
        this.notificationTemplateRepository = notificationTemplateRepository;
    }

    /**
     * Save a notificationTemplate.
     *
     * @param notificationTemplate the entity to save.
     * @return the persisted entity.
     */
    public NotificationTemplate save(NotificationTemplate notificationTemplate) throws ResourceNotFoundException, DataNotFoundException, DataIntegrityException {
        log.debug("Request to save NotificationTemplate : {}", notificationTemplate);

        if(notificationTemplate.getName()==null)
        {
            throw  new DataIntegrityException(appProperties.getNotificationname());
        }else {
        NotificationTemplate template=notificationTemplateRepository.findByname(notificationTemplate.getName());
        if(template!=null)
        {
                throw  new ResourceNotFoundException(appProperties.getNotificationTemplate());

        }else {
            Status s=statusRepository.getById(notificationTemplate.getStatusId().getId());
            notificationTemplate.setStatusId(s);
            MessageType type=messageTypeRepository.getById(notificationTemplate.getMessageId().getId());
            if(type==null)
            {
                throw  new DataNotFoundException(appProperties.getMessagedata());
            }else {
                notificationTemplate.setMessageId(type);
            }
            notificationTemplate.setName(notificationTemplate.getName());
        }}


        return notificationTemplateRepository.save(notificationTemplate);
    }

    /**
     * Update a notificationTemplate.
     *
     * @param notificationTemplate the entity to save.
     * @return the persisted entity.
     */
    public NotificationTemplate update(NotificationTemplate notificationTemplate) throws ResourceNotFoundException {
        log.debug("Request to update NotificationTemplate : {}", notificationTemplate);
        NotificationTemplate notificationTemplate1=notificationTemplateRepository.getById(notificationTemplate.getId());
        if(notificationTemplate1!=null)
        {
            notificationTemplate=notificationTemplateRepository.save(notificationTemplate);
        }else {

                throw  new ResourceNotFoundException(appProperties.getNotificationTemplatedata());

        }
        return notificationTemplate;
    }


    /**
     * Get all the notificationTemplates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NotificationTemplate> findAll() throws DataNotFoundException {
        log.debug("Request to get all NotificationTemplates");
        List<NotificationTemplate> notificationTemplates=notificationTemplateRepository.findAllData();
        if(notificationTemplates.isEmpty())
        {
          throw new DataNotFoundException(appProperties.getData());
        }else {
            return notificationTemplates;
        }


    }

    /**
     * Get one notificationTemplate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public NotificationTemplate findOne(Long id) throws DataNotFoundException {
        log.debug("Request to get NotificationTemplate : {}", id);
        NotificationTemplate notificationTemplate=notificationTemplateRepository.getById(id);
        if(notificationTemplate==null)
        {
            throw  new DataNotFoundException(appProperties.getNotificationTemplatedata());

        }else {
            return notificationTemplate;
        }

    }

    /**
     * Delete the notificationTemplate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NotificationTemplate : {}", id);
        notificationTemplateRepository.deleteById(id);
    }
}
