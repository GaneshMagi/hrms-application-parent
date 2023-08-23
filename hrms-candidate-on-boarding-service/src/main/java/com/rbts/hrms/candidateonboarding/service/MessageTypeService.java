package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.MessageType;
import com.rbts.hrms.candidateonboarding.repository.MessageTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MessageType}.
 */
@Service
@Transactional
public class MessageTypeService {

    private final Logger log = LoggerFactory.getLogger(MessageTypeService.class);

    private final MessageTypeRepository messageTypeRepository;
    @Autowired
    AppProperties appProperties;

    public MessageTypeService(MessageTypeRepository messageTypeRepository) {
        this.messageTypeRepository = messageTypeRepository;
    }

    /**
     * Save a messageType.
     *
     * @param messageType the entity to save.
     * @return the persisted entity.
     */
    public MessageType save(MessageType messageType) throws ResourceNotFoundException, DataIntegrityException {
        log.debug("Request to save MessageType : {}", messageType);
         MessageType type=messageTypeRepository.findByType(messageType.getType());
         if(messageType.getType()==null)
         {
           throw new DataIntegrityException(appProperties.getMessagetype());
         }else {
         if(type!=null)
         {
                 throw  new ResourceNotFoundException(appProperties.getMessage());
         }else {
             messageType.setType(messageType.getType());
         }}

        return messageTypeRepository.save(messageType);
    }

    /**
     * Update a messageType.
     *
     * @param messageType the entity to save.
     * @return the persisted entity.
     */
    public MessageType update(MessageType messageType) throws DataNotFoundException {
        log.debug("Request to update MessageType : {}", messageType);
        MessageType message=messageTypeRepository.getById(messageType.getId());
        if(message==null)
        {
          throw  new DataNotFoundException(appProperties.getMessagedata());

        }else {
            return messageTypeRepository.save(messageType);
        }

    }

    /**
     * Get all the messageTypes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MessageType> findAll() throws DataNotFoundException {
        log.debug("Request to get all MessageTypes");

        List<MessageType> messageTypes= messageTypeRepository.getAllData();
        if(messageTypes.isEmpty())
        {
         throw  new DataNotFoundException(appProperties.getData());
        }else {
            return messageTypes;
        }

    }

    /**
     * Get one messageType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public MessageType findOne(Long id) throws DataNotFoundException {
        log.debug("Request to get MessageType : {}", id);
        MessageType msg=messageTypeRepository.getById(id);
        if(msg==null)
        {
            throw  new DataNotFoundException(appProperties.getMessagedata());
        }else {
            return msg;
        }

    }

    /**
     * Delete the messageType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MessageType : {}", id);
        messageTypeRepository.deleteById(id);
    }
}
