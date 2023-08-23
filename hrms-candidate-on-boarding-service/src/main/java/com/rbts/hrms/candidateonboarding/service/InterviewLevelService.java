package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.InterviewLevel;
import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.Status;
import com.rbts.hrms.candidateonboarding.repository.InterviewLevelRepository;
import com.rbts.hrms.candidateonboarding.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InterviewLevel}.
 */
@Service
@Transactional
public class InterviewLevelService {

    private final Logger log = LoggerFactory.getLogger(InterviewLevelService.class);

    private final InterviewLevelRepository interviewLevelRepository;

    @Autowired
    AppProperties appProperties;

    @Autowired
    StatusRepository statusRepository;

    public InterviewLevelService(InterviewLevelRepository interviewLevelRepository) {
        this.interviewLevelRepository = interviewLevelRepository;
    }

    /**
     * Save a interviewLevel.
     *
     * @param interviewLevel the entity to save.
     * @return the persisted entity.
     */
    public InterviewLevel save(InterviewLevel interviewLevel) throws ResourceNotFoundException, DataIntegrityException {
        log.debug("Request to save InterviewLevel : {}", interviewLevel);


        if(interviewLevel.getLevelName()==null)
        {
           throw  new DataIntegrityException(appProperties.getInterviewname());
        }else {

            if(interviewLevel.getPreference()==0)
            {
                throw  new DataIntegrityException(appProperties.getPreference());

            }else {
                InterviewLevel i = interviewLevelRepository.findByLevel(interviewLevel.getLevelName());
                if (i != null) {
                    throw new ResourceNotFoundException(appProperties.getInterviewlevel());
                } else {
                    Status status = statusRepository.getById(interviewLevel.getStatusId().getId());
                    interviewLevel.setStatusId(status);
                    interviewLevel = interviewLevelRepository.save(interviewLevel);
                }
            }
        }

        return interviewLevel;
    }

    /**
     * Update a interviewLevel.
     *
     * @param interviewLevel the entity to save.
     * @param id
     * @return the persisted entity.
     */
    public InterviewLevel update(InterviewLevel interviewLevel, Long id) throws DataNotFoundException {
        log.debug("Request to update InterviewLevel : {}", interviewLevel);
        System.out.println("iiiiiiiiiii"+id);
        InterviewLevel i=interviewLevelRepository.getById(id);

        if(i !=null)
        {

            return interviewLevelRepository.save(interviewLevel);
        }else {


            throw  new DataNotFoundException(appProperties.getInterviewleveldata());
        }
    }

    /**
     * Partially update a interviewLevel.
     *
     * @param interviewLevel the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InterviewLevel> partialUpdate(InterviewLevel interviewLevel) {
        log.debug("Request to partially update InterviewLevel : {}", interviewLevel);

        return interviewLevelRepository
            .findById(interviewLevel.getId())
            .map(existingInterviewLevel -> {
                if (interviewLevel.getLevelName() != null) {
                    existingInterviewLevel.setLevelName(interviewLevel.getLevelName());
                }
                if (interviewLevel.getPredecessor() != null) {
                    existingInterviewLevel.setPredecessor(interviewLevel.getPredecessor());
                }
                return existingInterviewLevel;
            })
            .map(interviewLevelRepository::save);
    }

    /**
     * Get all the interviewLevels.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InterviewLevel> findAll() throws DataNotFoundException {
        log.debug("Request to get all InterviewLevels");
        List<InterviewLevel> interviewLevels=interviewLevelRepository.findAllData();
        if(interviewLevels.isEmpty())
        {
            throw new DataNotFoundException(appProperties.getData());

        }else {
            return interviewLevels;
        }
    }

    /**
     * Get one interviewLevel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public InterviewLevel findOne(Long id) throws DataNotFoundException {

        InterviewLevel level=interviewLevelRepository.getById(id);

        if(level==null)
        {
            throw new DataNotFoundException(appProperties.getInterviewleveldata());
        }else {
            return level;
        }


    }

    /**
     * Delete the interviewLevel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) throws DataNotFoundException {
        log.debug("Request to delete InterviewLevel : {}", id);
        InterviewLevel interviewLevel=interviewLevelRepository.getById(id);
        if(interviewLevel!=null)
        {
            interviewLevelRepository.deleteById(id);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }

    }

    public InterviewLevel getByInterviewLevel(String name) throws ResourceNotFoundException {
        InterviewLevel l = interviewLevelRepository.findByLevel(name);
        InterviewLevel level=new InterviewLevel();
        if (l != null) {
            return l;
        } else {
            throw new ResourceNotFoundException(appProperties.getLevelname());
        }
    }


}
