package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.CandidateFeedback;
import com.rbts.hrms.candidateonboarding.entity.OfferLetter;
import com.rbts.hrms.candidateonboarding.repository.CandidateFeedbackRepository;
import com.rbts.hrms.candidateonboarding.repository.OfferLetterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OfferLetter}.
 */
@Service
@Transactional
public class OfferLetterService {

    private final Logger log = LoggerFactory.getLogger(OfferLetterService.class);

    private final OfferLetterRepository offerLetterRepository;

    @Autowired
    CandidateFeedbackRepository candidateFeedbackRepository;

    @Autowired
    AppProperties appProperties;

    public OfferLetterService(OfferLetterRepository offerLetterRepository) {
        this.offerLetterRepository = offerLetterRepository;
    }

    /**
     * Save a offerLetter.
     *
     * @param offerLetter the entity to save.
     * @return the persisted entity.
     */
    public OfferLetter save(OfferLetter offerLetter) throws ResourceNotFoundException, DataNotFoundException, DataIntegrityException {
        log.debug("Request to save OfferLetter : {}", offerLetter);
        OfferLetter f=offerLetterRepository.findByLevel(offerLetter.getEmailId());
        if(f!=null)
        {
                throw new ResourceNotFoundException(appProperties.getEmail());
        }else {
            offerLetter.setEmailId(offerLetter.getEmailId());
        }
        if(offerLetter.getCandidateFeedback()==null)
        {
            throw new DataIntegrityException(appProperties.getCandidatedetails());

        }else {
        CandidateFeedback feedback=candidateFeedbackRepository.getById(offerLetter.getCandidateFeedback().getId());
        if(feedback==null)
        {
            throw  new DataNotFoundException(appProperties.getFeedback());
        }else {
            offerLetter.setCandidateFeedback(feedback);
        }}

        return offerLetterRepository.save(offerLetter);
    }

    /**
     * Update a offerLetter.
     *
     * @param offerLetter the entity to save.
     * @return the persisted entity.
     */
    public OfferLetter update(OfferLetter offerLetter) throws DataNotFoundException {
        log.debug("Request to update OfferLetter : {}", offerLetter);
        OfferLetter offerLetter1=offerLetterRepository.getById(offerLetter.getId());

        if(offerLetter1==null)
        {
            throw new DataNotFoundException(appProperties.getData());
        }else {
            return offerLetterRepository.save(offerLetter);
        }

    }


    /**
     * Get all the offerLetters.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OfferLetter> findAll() throws DataNotFoundException {
        log.debug("Request to get all OfferLetters");
        List<OfferLetter> offerLetters=offerLetterRepository.findAll();
        if(offerLetters.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return offerLetters;
        }

    }

    /**
     * Get one offerLetter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public OfferLetter findOne(Long id) throws DataNotFoundException {
        OfferLetter offerLetter1=offerLetterRepository.getById(id);

        if(offerLetter1==null)
        {
            throw new DataNotFoundException(appProperties.getData());
        }else {
            return offerLetter1;
        }
    }

    /**
     * Delete the offerLetter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OfferLetter : {}", id);
        offerLetterRepository.deleteById(id);
    }
}
