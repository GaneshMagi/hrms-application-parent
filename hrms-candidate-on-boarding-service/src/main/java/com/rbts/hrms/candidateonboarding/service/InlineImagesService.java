package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.InlineImages;
import com.rbts.hrms.candidateonboarding.entity.NotificationDetails;
import com.rbts.hrms.candidateonboarding.repository.InlineImagesRepository;
import com.rbts.hrms.candidateonboarding.repository.NotificationDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InlineImages}.
 */
@Service
@Transactional
public class InlineImagesService {

    private final Logger log = LoggerFactory.getLogger(InlineImagesService.class);

    private final InlineImagesRepository inlineImagesRepository;

    @Autowired
    NotificationDetailsRepository notificationDetailsRepository;

    @Autowired
    AppProperties appProperties;

    public InlineImagesService(InlineImagesRepository inlineImagesRepository) {
        this.inlineImagesRepository = inlineImagesRepository;
    }

    /**
     * Save a inlineImages.
     *
     * @param inlineImages the entity to save.
     * @return the persisted entity.
     */
    public InlineImages save(InlineImages inlineImages) throws ResourceNotFoundException, DataIntegrityException {
        log.debug("Request to save InlineImages : {}", inlineImages);
        if(inlineImages.getNotificationDetailsId()==null)
        {
          throw new DataIntegrityException(appProperties.getNotificationid());
        }else {
        NotificationDetails details=notificationDetailsRepository.getById(inlineImages.getNotificationDetailsId().getId());
        if(details!=null)
        {
            inlineImages.setNotificationDetailsId(details);
        }else {
            throw new ResourceNotFoundException(appProperties.getNotificationDetails());
        }}
        return inlineImagesRepository.save(inlineImages);
    }

    /**
     * Update a inlineImages.
     *
     * @param inlineImages the entity to save.
     * @return the persisted entity.
     */
    public InlineImages update(InlineImages inlineImages) throws DataNotFoundException {
        log.debug("Request to update InlineImages : {}", inlineImages);

        InlineImages inlineImages1=inlineImagesRepository.getById(inlineImages.getId());
        if(inlineImages1==null)
        {

                throw  new DataNotFoundException(appProperties.getData());

        }else {
            return inlineImagesRepository.save(inlineImages);
        }



    }


    /**
     * Get all the inlineImages.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InlineImages> findAll() throws DataNotFoundException {
        log.debug("Request to get all InlineImages");
        List<InlineImages> inlineImages=inlineImagesRepository.findAll();
        if(inlineImages.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return inlineImagesRepository.findAll();
        }

    }

    /**
     * Get one inlineImages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public InlineImages findOne(Long id) {
        log.debug("Request to get InlineImages : {}", id);
        InlineImages inlineImages=inlineImagesRepository.getById(id);
        if(inlineImages==null)
        {
            try {
                throw  new DataNotFoundException(appProperties.getData());
            } catch (DataNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            return inlineImages;
        }

    }

    /**
     * Delete the inlineImages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InlineImages : {}", id);
        inlineImagesRepository.deleteById(id);
    }
}
