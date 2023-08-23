package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;

import com.rbts.hrms.candidateonboarding.mapper.FeedbackMapper;
import com.rbts.hrms.candidateonboarding.repository.CandidateDetailsRepository;
import com.rbts.hrms.candidateonboarding.repository.CandidateFeedbackRepository;
import com.rbts.hrms.candidateonboarding.repository.PanelSkillRepository;
import com.rbts.hrms.candidateonboarding.repository.SkillRepository;
import com.rbts.hrms.candidateonboarding.dto.CandidateFeedbackResponse;
import com.rbts.hrms.candidateonboarding.dto.FeedbackRequest;
import com.rbts.hrms.candidateonboarding.dto.FeedbackResponse;
import com.rbts.hrms.candidateonboarding.entity.CandidateDetails;
import com.rbts.hrms.candidateonboarding.entity.CandidateFeedback;
import com.rbts.hrms.candidateonboarding.entity.InterviewLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CandidateFeedback}.
 */
@Service
@Transactional
public class CandidateFeedbackService {

    private final Logger log = LoggerFactory.getLogger(CandidateFeedbackService.class);

    private final CandidateFeedbackRepository candidateFeedbackRepository;
    @Autowired
    PanelSkillRepository panelSkillRepository;

    @Autowired
    SkillRepository skillRepository;
    @Autowired
    FeedbackMapper feedbackMapper;

    @Autowired
    CandidateDetailsRepository candidateDetailsRepository;

    @Autowired
    AppProperties appProperties;

    public CandidateFeedbackService(CandidateFeedbackRepository candidateFeedbackRepository) {
        this.candidateFeedbackRepository = candidateFeedbackRepository;
    }

    /**
     * Save a candidateFeedback.
     *
     * @param candidateFeedback the entity to save.
     * @return the persisted entity.
     */
    public FeedbackResponse save(FeedbackRequest candidateFeedback) throws DataNotFoundException, DataIntegrityException {
        log.debug("Request to save CandidateFeedback : {}", candidateFeedback);

        CandidateFeedback c=feedbackMapper.convertRequest(candidateFeedback);
        candidateFeedbackRepository.save(c);
        FeedbackResponse response=feedbackMapper.convertResponse(c);

        return response;
    }


    /**
     * Update a candidateFeedback.
     *
     * @param candidateFeedback the entity to save.
     * @return the persisted entity.
     */
    public FeedbackResponse update(FeedbackRequest candidateFeedback, long id) throws DataNotFoundException, DataIntegrityException {
        log.debug("Request to update CandidateFeedback : {}", candidateFeedback);
        CandidateFeedback c=feedbackMapper.convertRequest(candidateFeedback);
        c.setId(id);
        candidateFeedbackRepository.save(c);
        CandidateFeedback f=candidateFeedbackRepository.getById(id);
        FeedbackResponse response=feedbackMapper.convertResponse(f);

        return response;
    }


    /**
     * Get all the candidateFeedbacks.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FeedbackResponse> findAll() throws DataNotFoundException {
        log.debug("Request to get all CandidateFeedbacks");
        List<CandidateFeedback> cf=candidateFeedbackRepository.findAll();
        List<FeedbackResponse> list=new ArrayList<>();
        if(cf.isEmpty()){
            throw new DataNotFoundException(appProperties.getData());
          }else {
            for(CandidateFeedback f:cf)
            {
                FeedbackResponse response=feedbackMapper.convertResponse(f);
                list.add(response);
            }
        }

        return list;
    }

    /**
     * Get one candidateFeedback by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public FeedbackResponse findOne(Long id) throws DataNotFoundException {
        log.debug("Request to get CandidateFeedback : {}", id);
        CandidateFeedback f=candidateFeedbackRepository.getById(id);
        if(f==null)
        {
            throw  new DataNotFoundException(appProperties.getFeedback());
        }else {
            FeedbackResponse response=feedbackMapper.convertResponse(f);
            return response ;
        }


    }

    /**
     * Delete the candidateFeedback by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CandidateFeedback : {}", id);
        candidateFeedbackRepository.deleteById(id);
    }

    /**
     * get all feedback for all candidate
     * @return
     */
    public List<CandidateFeedbackResponse> findAllDetails() throws DataNotFoundException {


        List<CandidateDetails> t=candidateDetailsRepository.findData();
        List<CandidateFeedbackResponse> t1=new ArrayList<>();
        for(CandidateDetails c:t)
        {
            CandidateDetails data=candidateDetailsRepository.getById(c.getId());


            List<CandidateFeedback>  list=candidateFeedbackRepository.findByInterviewLevel(data.getId());

            CandidateFeedbackResponse details=new CandidateFeedbackResponse();
            details.setId(data.getId());
            details.setCity(data.getCity());
            details.setFeedbackStatus(data.getFeedbackStatus());
            details.setCreatedBy(data.getCreatedBy());
            details.setLastModifiedBy(data.getLastModifiedBy());
            details.setCreationDate(data.getCreationDate());
            details.setLastModifiedDate(data.getLastModifiedDate());
            details.setEmail(data.getEmail());
            details.setAltContactNo(data.getAltContactNo());
            details.setBirthDate(data.getBirthDate());
            details.setContactNo(data.getContactNo());
            details.setCurrentCompanyName(data.getCurrentCompanyName());
            details.setExperienceInYears(data.getExperienceInYears());
            details.setFullName(data.getFullName());
            details.setGender(data.getGender());
            details.setLinkedInUrl(data.getLinkedInUrl());
            details.setPassingYear(data.getPassingYear());
            details.setProfileReferance(data.getProfileReferance());
            details.setProfileScannedOn(data.getProfileScannedOn());
            details.setQualification(data.getQualification());
            details.setResumeUrl(data.getResumeUrl());
            details.setStatusId(data.getStatusId());
            details.setWhatsappNo(data.getWhatsappNo());
            details.setAddress(data.getAddress());
//            String[] s =data.getSkill();
//            ArrayList<Skill> skill = new ArrayList<Skill>();
//            Skill skill1 = null;
//            for (String data2 : s) {
//                String data1 = data2.toUpperCase();
//                skill1 = skillRepository.findByName(data1);
//
//
//            }
//            skill.add(skill1);
//            details.setSkills(skill);

            details.setSkill(data.getSkill());
            List<FeedbackResponse> responses=new ArrayList<>();
            for(CandidateFeedback cf:list)
            {
                FeedbackResponse fs=feedbackMapper.convertResponse(cf);
                responses.add(fs);
            }
            details.setCandidateFeedback(responses);
            t1.add(details);



        }

        return t1;
    }

    /**
     * get all feedback for one candidate
     * @param id pass candidate id
     * @return
     */
    public CandidateFeedbackResponse findByIdDetails(Long id) throws DataNotFoundException {


            CandidateDetails data=candidateDetailsRepository.getById(id);


            List<CandidateFeedback>  list=candidateFeedbackRepository.findByInterviewLevel(data.getId());

            CandidateFeedbackResponse details=new CandidateFeedbackResponse();
            details.setId(data.getId());
            details.setCity(data.getCity());
            details.setCreatedBy(data.getCreatedBy());
            details.setLastModifiedBy(data.getLastModifiedBy());
            details.setCreationDate(data.getCreationDate());
            details.setLastModifiedDate(data.getLastModifiedDate());
            details.setEmail(data.getEmail());
            details.setAltContactNo(data.getAltContactNo());
            details.setBirthDate(data.getBirthDate());
            details.setContactNo(data.getContactNo());
            details.setCurrentCompanyName(data.getCurrentCompanyName());
            details.setExperienceInYears(data.getExperienceInYears());
            details.setFullName(data.getFullName());
            details.setGender(data.getGender());
            details.setLinkedInUrl(data.getLinkedInUrl());
            details.setPassingYear(data.getPassingYear());
            details.setProfileReferance(data.getProfileReferance());
            details.setProfileScannedOn(data.getProfileScannedOn());
            details.setQualification(data.getQualification());
            details.setResumeUrl(data.getResumeUrl());
            details.setStatusId(data.getStatusId());
            details.setWhatsappNo(data.getWhatsappNo());
            details.setSkill(data.getSkill());
            details.setAddress(data.getAddress());
//            String[] s =data.getSkill();
//            ArrayList<Skill> skill = new ArrayList<Skill>();
//            Skill skill1 = null;
//            for (String data2 : s) {
//                String data1 = data2.toUpperCase();
//                skill1 = skillRepository.findByName(data1);
//
//
//            }
//            skill.add(skill1);
//            details.setSkills(skill);
            List<FeedbackResponse> responses=new ArrayList<>();
            for(CandidateFeedback cf:list)
            {
                FeedbackResponse fs=feedbackMapper.convertResponse(cf);
                responses.add(fs);
            }
            details.setCandidateFeedback(responses);

        return details;
    }


    public List<InterviewLevel> getByInterviewLevel(Long candidateId) {

        List<CandidateFeedback> feedback = candidateFeedbackRepository.findByCandidateId(candidateId);
        if (feedback != null) {
            ArrayList<InterviewLevel> level = new ArrayList<>();
            for (CandidateFeedback f : feedback) {
                InterviewLevel l = f.getInterviewLevelId();
                level.add(l);
            }
            return level;
        } else {
            try {
                throw new ResourceNotFoundException(appProperties.getLevel());
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
