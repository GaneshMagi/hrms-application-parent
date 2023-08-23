package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.Status;
import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Status}.
 */
@Service
@Transactional
public class StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusService.class);

    private final StatusRepository statusRepository;
    @Autowired
    AppProperties appProperties;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    /**
     * Save a status.
     *
     * @param status the entity to save.
     * @return the persisted entity.
     */
     public Status save(Status status) throws ResourceNotFoundException, DataIntegrityException {
        log.debug("Request to save Status : {}", status);
          Status s=statusRepository.findByName(status.getStatusName());
          if(s!=null)
          {
                  throw  new ResourceNotFoundException(appProperties.getStatus());
          }else {
           if(status.getIsActive()==null)
           {
               throw new DataIntegrityException(appProperties.getValue());
           }else {
               status = statusRepository.save(status);
           }
          }
        return status ;
    }

    /**
     * Update a status.
     *
     * @param status the entity to save.
     * @return the persisted entity.
     */
    public Status update(Status status) {
        log.debug("Request to update Status : {}", status);

        return statusRepository.save(status);
    }

    /**
     * Partially update a status.
     *
     * @param status the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Status> partialUpdate(Status status) {
        log.debug("Request to partially update Status : {}", status);

        return statusRepository
            .findById(status.getId())
            .map(existingStatus -> {
                if (status.getStatusName() != null) {
                    existingStatus.setStatusName(status.getStatusName());
                }
                if (status.getStatusType() != null) {
                    existingStatus.setStatusType(status.getStatusType());
                }
                if (status.getIsActive() != null) {
                    existingStatus.setIsActive(status.getIsActive());
                }
//                if (status.getCreatedBy() != null) {
//                    existingStatus.setCreatedBy(status.getCreatedBy());
//                }
//                if (status.getCreatedOn() != null) {
//                    existingStatus.setCreatedOn(status.getCreatedOn());
//                }
//                if (status.getModifiedBy() != null) {
//                    existingStatus.setModifiedBy(status.getModifiedBy());
//                }
//                if (status.getModifiedOn() != null) {
//                    existingStatus.setModifiedOn(status.getModifiedOn());
//                }

                return existingStatus;
            })
            .map(statusRepository::save);
    }

    /**
     * Get all the statuses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Status> findAll() throws DataNotFoundException {
        log.debug("Request to get all Statuses");
        List<Status> statusList=statusRepository.findAllData();
        if(statusList.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return statusList;
        }

    }

    /**
     * Get one status by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Status findOne(Long id) throws DataNotFoundException {
        Status status=statusRepository.getById(id);
        if(status==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return statusRepository.getById(id);
        }

    }

    /**
     * Delete the status by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.deleteById(id);
    }

    public Status findData(Long id) throws DataNotFoundException {
        Status status=statusRepository.getById(id);
        if(status==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return statusRepository.getById(id);
        }
    }
}
