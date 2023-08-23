package com.rbts.hrms.candidateonboarding.mapper;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.PanelSkillRequest;
import com.rbts.hrms.candidateonboarding.dto.PanelSkillResponse;
import com.rbts.hrms.candidateonboarding.entity.*;
import com.rbts.hrms.candidateonboarding.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PanelSkillMapper {
    @Autowired
    SkillRepository skillRepository;
    @Autowired
    StatusRepository statusRepository;

    @Autowired
    InterviewLevelRepository interviewLevelRepository;

    @Autowired
    PanelDetailsRepository panelDetailsRepository;
    @Autowired
    PanelSkillRepository panelSkillRepository;

    @Autowired
    AppProperties appProperties;

    public PanelSkill convertRequest(PanelSkillRequest panelSkill,Skill s) throws DataNotFoundException, DataIntegrityException, ResourceNotFoundException {
        PanelSkill p = new PanelSkill();
        //check panel is null or not
        if(panelSkill.getPanelId()==null)
        {
            throw  new DataIntegrityException(appProperties.getPaneldata());
        }else {
            //check panel is already present in panel skill
         //   PanelSkill skill=panelSkillRepository.getByPanelId(panelSkill.getPanelId().getId());

                //check panel is present in panel details
                PanelDetails details=panelDetailsRepository.get(panelSkill.getPanelId().getId());
                if(details==null)
                {
                    throw new DataNotFoundException(appProperties.getPanel());
                }else {
                    p.setPanelId(details);
                }
            }
        //check interviwe level is null or not
        if(panelSkill.getInterviewLevel()==null)
        {
           throw  new DataIntegrityException(appProperties.getInterviewID());
        }else{
            //check interview level is already present in panel skill
        InterviewLevel level=interviewLevelRepository.getById(panelSkill.getInterviewLevel().getId());
        if(level==null)
        {
            throw new DataNotFoundException(appProperties.getInterviewleveldata());
        }else {
            p.setInterviewLevel(level);
        }}

        Status status=statusRepository.getById(panelSkill.getStatus().getId());
        p.setStatus(status);
        p.setSkill(s);
        return p;
    }


    public PanelSkillResponse convertResponse(PanelSkill p) {

        PanelSkillResponse pk = new PanelSkillResponse();
        BeanUtils.copyProperties(p,pk);
        PanelDetails d=panelDetailsRepository.get(p.getPanelId().getId());
        pk.setPanelId(p.getPanelId());
        return pk;
    }




}
