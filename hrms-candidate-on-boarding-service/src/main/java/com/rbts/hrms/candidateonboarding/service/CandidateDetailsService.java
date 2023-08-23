package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.CandidateRequest;
import com.rbts.hrms.candidateonboarding.dto.CandidateResponse;
import com.rbts.hrms.candidateonboarding.entity.CandidateDetails;
import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.mapper.CandidateMapper;
import com.rbts.hrms.candidateonboarding.repository.CandidateDetailsRepository;
import com.rbts.hrms.candidateonboarding.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing {@link CandidateDetails}.
 */
@Service
@Transactional
public class CandidateDetailsService {

    private final Logger log = LoggerFactory.getLogger(CandidateDetailsService.class);

    private final CandidateDetailsRepository candidateDetailsRepository;
    @Autowired
    AppProperties appProperties;
    @Autowired
    CandidateMapper candidateMapper;
    @Autowired
    SkillRepository skillRepository;

    public CandidateDetailsService(CandidateDetailsRepository candidateDetailsRepository) {
        this.candidateDetailsRepository = candidateDetailsRepository;
    }

    /**
     * Save a candidateDetails.
     *
     * @param candidateDetails the entity to save.
     * @return the persisted entity.
     */
    public CandidateResponse save(CandidateRequest candidateDetails) throws ResourceNotFoundException {
        log.debug("Request to save CandidateDetails : {}", candidateDetails);

        CandidateDetails candidate=candidateMapper.convertRequest(candidateDetails);
        candidateDetailsRepository.save(candidate);
        CandidateResponse response=candidateMapper.convertResponse(candidate);
        return response;
    }


    /**
     * Update a candidateDetails.
     *
     * @param candidateDetails the entity to save.
     * @param id
     * @return the persisted entity.
     */
    public CandidateResponse update(CandidateRequest candidateDetails, Long id) {
        log.debug("Request to update CandidateDetails : {}", candidateDetails);
        CandidateDetails candidate=candidateMapper.convertRequest1(candidateDetails);
        candidate.setId(id);
        candidateDetailsRepository.save(candidate);
        CandidateResponse response=candidateMapper.convertResponse(candidate);
        return response;

    }


    /**
     * Get all the candidateDetails.
     *
     * @return the list of entities.
     */

    public List<CandidateResponse> findAll() throws DataNotFoundException {
        log.debug("Request to get all CandidateDetails");
       List<CandidateDetails> list=candidateDetailsRepository.findAllData();
        List<CandidateResponse> fList=new ArrayList<>();
        CandidateResponse response=new CandidateResponse();
        if(list.isEmpty())
        {
            throw new DataNotFoundException(appProperties.getData());
         }
        else {
            for (CandidateDetails details:list)
            {
                response=candidateMapper.convertResponse(details);
                fList.add(response);

            }
        }

        return fList;
    }

    /**
     * Get one candidateDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    public CandidateResponse findOne(Long id) throws DataNotFoundException {
        log.debug("Request to get CandidateDetails : {}", id);
        CandidateDetails details=candidateDetailsRepository.getById(id);
        if(details==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else
        {
            CandidateResponse response=candidateMapper.convertResponse(details);

            return response ;
        }


    }

    /**
     * Delete the candidateDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CandidateDetails : {}", id);
        candidateDetailsRepository.deleteById(id);
    }
}
