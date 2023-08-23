package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.NotificationDetails;
import com.rbts.hrms.candidateonboarding.entity.NotificationTemplate;
import com.rbts.hrms.candidateonboarding.kafka.*;
import com.rbts.hrms.candidateonboarding.repository.NotificationDetailsRepository;
import com.rbts.hrms.candidateonboarding.repository.NotificationTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link NotificationDetails}.
 */
@Service
@Transactional
public class NotificationDetailsService {

    private final Logger log = LoggerFactory.getLogger(NotificationDetailsService.class);

    private final NotificationDetailsRepository notificationDetailsRepository;

    @Autowired
    NotificationTemplateRepository notificationTemplateRepository;

    @Autowired
    KafkaEmailFacade kafkaEmailFacade;
    @Autowired
    KafkaWhatsappFacade kafkaWhatsappFacade;

    @Autowired
    KafkaSmsFacade kafkaSmsFacade;

    @Autowired
    AppProperties appProperties;


    public NotificationDetailsService(NotificationDetailsRepository notificationDetailsRepository) {
        this.notificationDetailsRepository = notificationDetailsRepository;
    }

    /**
     * Save a notificationDetails.
     *
     * @param notificationDetails the entity to save.
     * @return the persisted entity.
     */
    public NotificationDetails save(NotificationDetails notificationDetails) throws DataNotFoundException {
        log.debug("Request to save NotificationDetails : {}", notificationDetails);
        String type=notificationDetails.getTemplateId().getMessageId().getType();

        switch (type){
            case "Email":
                KafkaEmailMessage message=new KafkaEmailMessage();
                kafkaEmailFacade.sendMailNotification(message,notificationDetails);
                break;

            case "Whatsapp":
                KafkaWhatsappMessage w=new KafkaWhatsappMessage();
                kafkaWhatsappFacade.sendWhatsappNotification(w,notificationDetails);
                break;
            case "Sms":

                KafkaSmsMessage m=new KafkaSmsMessage();
                kafkaSmsFacade.sendSmsNotification(m,notificationDetails);
                break;


        }


        NotificationTemplate t=notificationTemplateRepository.getById(notificationDetails.getTemplateId().getId());
        if(t!=null)
        {
            notificationDetails.setTemplateId(t);
        }else
        {
          throw  new DataNotFoundException(appProperties.getNotificationTemplatedata());
        }

        return notificationDetailsRepository.save(notificationDetails);
    }

    /**
     * Update a notificationDetails.
     *
     * @param notificationDetails the entity to save.
     * @return the persisted entity.
     */
    public NotificationDetails update(NotificationDetails notificationDetails) throws DataNotFoundException {
        log.debug("Request to update NotificationDetails : {}", notificationDetails);
        NotificationDetails details=notificationDetailsRepository.getById(notificationDetails.getId());
        if(details==null)
        {
            throw  new DataNotFoundException(appProperties.getNotificationDetails());
        }else
        {
            return notificationDetailsRepository.save(notificationDetails);
        }


    }

    /**
     * Partially update a notificationDetails.
     *
     * @param notificationDetails the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NotificationDetails> partialUpdate(NotificationDetails notificationDetails) {
        log.debug("Request to partially update NotificationDetails : {}", notificationDetails);

        return notificationDetailsRepository
            .findById(notificationDetails.getId())
            .map(existingNotificationDetails -> {
                if (notificationDetails.getMessage() != null) {
                    existingNotificationDetails.setMessage(notificationDetails.getMessage());
                }
                if (notificationDetails.getFromMail() != null) {
                    existingNotificationDetails.setFromMail(notificationDetails.getFromMail());
                }
                if (notificationDetails.getToMail() != null) {
                    existingNotificationDetails.setToMail(notificationDetails.getToMail());
                }
                if (notificationDetails.getCc() != null) {
                    existingNotificationDetails.setCc(notificationDetails.getCc());
                }
                if (notificationDetails.getBcc() != null) {
                    existingNotificationDetails.setBcc(notificationDetails.getBcc());
                }
                if (notificationDetails.getSubject() != null) {
                    existingNotificationDetails.setSubject(notificationDetails.getSubject());
                }
                if (notificationDetails.getBody() != null) {
                    existingNotificationDetails.setBody(notificationDetails.getBody());
                }

                return existingNotificationDetails;
            })
            .map(notificationDetailsRepository::save);
    }

    /**
     * Get all the notificationDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NotificationDetails> findAll() throws DataNotFoundException {
        log.debug("Request to get all NotificationDetails");
        List<NotificationDetails> notificationDetails=notificationDetailsRepository.findAllData();
        if(notificationDetails.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return notificationDetails;
        }

    }

    /**
     * Get one notificationDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public NotificationDetails findOne(Long id) throws DataNotFoundException {
        log.debug("Request to get NotificationDetails : {}", id);

        NotificationDetails details=notificationDetailsRepository.getById(id);
        if(details==null)
        {
            throw new DataNotFoundException(appProperties.getNotificationDetails());
        }else {
            return details;
        }

    }

    /**
     * Delete the notificationDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NotificationDetails : {}", id);
        notificationDetailsRepository.deleteById(id);
    }

    public List<NotificationDetails> findAllNotificaton(Long id) throws DataNotFoundException {
        List<NotificationDetails> notificationDetails=notificationDetailsRepository.findByModuleId(id);
        if(notificationDetails.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return notificationDetails;
        }

    }
}
