package com.rbts.hrms.candidateonboarding.mapper;

import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.CandidateResponse;
import com.rbts.hrms.candidateonboarding.dto.FeedbackRequest;
import com.rbts.hrms.candidateonboarding.dto.FeedbackResponse;
import com.rbts.hrms.candidateonboarding.dto.PanelSkillResponse;
import com.rbts.hrms.candidateonboarding.entity.*;
import com.rbts.hrms.candidateonboarding.repository.*;
import com.rbts.hrms.candidateonboarding.service.PanelSkillService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FeedbackMapper {

    @Autowired
    CandidateDetailsRepository candidateDetailsRepository;
    @Autowired
    PanelDetailsRepository panelDetailsRepository;

    @Autowired
    NotificationTemplateRepository notificationTemplateRepository;

    @Autowired
    NotificationDetailsRepository notificationDetailsRepository;

    @Autowired
    InterviewLevelRepository interviewLevelRepository;

    @Autowired
    CandidateMapper candidateMapper;
    @Autowired
    PanelSkillMapper panelSkillMapper;

    @Autowired
    AppProperties appProperties;

    @Autowired
    PanelSkillService panelSkillService;

    public FeedbackResponse convertResponse(CandidateFeedback candidateFeedback) throws DataNotFoundException {
        FeedbackResponse c = new FeedbackResponse();
        BeanUtils.copyProperties(candidateFeedback, c);
        CandidateDetails details = candidateDetailsRepository.findByEmail(candidateFeedback.getCandidateId().getEmail());
        CandidateResponse response = candidateMapper.convertResponse(details);
        c.setCandidateId(response);
        Set<PanelDetails> panelDetailsSet=new HashSet<>();
        for(PanelDetails p:candidateFeedback.getPanelDetails())
        {
            PanelDetails panelDetails=panelDetailsRepository.getById(p.getId());
            if(panelDetails!=null)
            {
                panelDetailsSet.add(panelDetails);
            }else
            {
                throw  new DataNotFoundException(appProperties.getData());
            }
        }
        c.setPanelDetails(panelDetailsSet);

        return c;
    }


    public CandidateFeedback convertRequest(FeedbackRequest candidateFeedback) throws DataNotFoundException, DataIntegrityException {
        CandidateFeedback c=new CandidateFeedback();
        BeanUtils.copyProperties(candidateFeedback,c);
        c.setTechnicalFeedback(candidateFeedback.getTechnicalFeedback());
        //check interview level is null or not
        if(candidateFeedback.getInterviewLevelId()==null)
        {
            throw new DataIntegrityException(appProperties.getInterviewID());
        }else {
            InterviewLevel in=interviewLevelRepository.getById(candidateFeedback.getInterviewLevelId().getId());
            if(in==null)
            {
                throw  new DataNotFoundException(appProperties.getInterviewleveldata());
            }else {
                c.setInterviewLevelId(in);
            }
        }

        //check CandidateDetails is null or not
        if(candidateFeedback.getCandidateId()==null)
        {
            throw new DataIntegrityException(appProperties.getCandidatedetails());
        }else {
            //check candidate is present in candidate table
            CandidateDetails details = candidateDetailsRepository.getById(candidateFeedback.getCandidateId().getId());
            if (details == null) {
                throw new DataNotFoundException(appProperties.getCandidate1());
            } else {
                details.setId(candidateFeedback.getCandidateId().getId());
                String status = candidateFeedback.getFinalStatus() + "" + "for" + candidateFeedback.getInterviewLevelId().getLevelName();
                details.setFeedbackStatus(status);
                candidateDetailsRepository.save(details);
            }
            c.setCandidateId(details);
        }

        //check panelskill is null or not
          if(candidateFeedback.getPanelDetails()==null) {
              throw new DataIntegrityException(appProperties.getPaneldata());
          }
//          }else {
//              Set<PanelDetails> panelDetailsSet=new HashSet<>();
//              for(PanelDetails p:candidateFeedback.getPanelDetails()) {
//                  PanelDetails panelDetails = panelDetailsRepository.getById(p.getId());
//                  if(panelDetails!=null)
//                  {
//                    panelDetailsSet.add(panelDetails);
//                  }else {
//                   throw  new DataNotFoundException(appProperties.getData());
//                  }
//
//              }
//          }

        return c;
    }

}
