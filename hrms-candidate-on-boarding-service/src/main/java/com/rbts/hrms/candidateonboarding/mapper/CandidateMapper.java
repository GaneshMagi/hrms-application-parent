package com.rbts.hrms.candidateonboarding.mapper;

import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.CandidateRequest;
import com.rbts.hrms.candidateonboarding.dto.CandidateResponse;
import com.rbts.hrms.candidateonboarding.entity.CandidateDetails;
import com.rbts.hrms.candidateonboarding.entity.City;
import com.rbts.hrms.candidateonboarding.entity.Status;
import com.rbts.hrms.candidateonboarding.repository.CandidateDetailsRepository;
import com.rbts.hrms.candidateonboarding.repository.CityRepository;
import com.rbts.hrms.candidateonboarding.repository.SkillRepository;
import com.rbts.hrms.candidateonboarding.repository.StatusRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CandidateMapper {


    @Autowired
    SkillRepository skillRepository;

    @Autowired
    CandidateDetailsRepository candidateDetailsRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    AppProperties appProperties;
    public CandidateDetails convertRequest1(CandidateRequest candidateDetails) {
        CandidateDetails details=new CandidateDetails();
        BeanUtils.copyProperties(candidateDetails, details);
        return details;
    }



    public CandidateDetails convertRequest(CandidateRequest candidateDetails) throws ResourceNotFoundException {
        CandidateDetails details=new CandidateDetails();
        City city=cityRepository.getById(candidateDetails.getCity().getId());
        details.setCity(city);
        CandidateDetails details1=candidateDetailsRepository.findByEmail(candidateDetails.getEmail());
           if(details1!=null)
           {
                  throw new ResourceNotFoundException(appProperties.getEmail());
            }else {
               CandidateDetails details2 = candidateDetailsRepository.findByContactNo(candidateDetails.getContactNo());
            if (details2!=null) {
                    throw new ResourceNotFoundException(appProperties.getContactNo());
            } else {

                BeanUtils.copyProperties(candidateDetails, details);
            }
        }
        return details;
    }

    public CandidateResponse convertResponse(CandidateDetails candidate1) {
        CandidateResponse details=new CandidateResponse();
        BeanUtils.copyProperties(candidate1, details);
        Status t=statusRepository.findByName(candidate1.getStatusId().getStatusName());
        CandidateDetails d=candidateDetailsRepository.getById(candidate1.getId());
        details.setStatusId(t);
        details.setLastModifiedDate(d.getLastModifiedDate());
        details.setLastModifiedBy(d.getLastModifiedBy());
        return details;

    }
}
