package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.PositionProfileRequest;
import com.rbts.hrms.candidateonboarding.dto.PositionProfileResponse;
import com.rbts.hrms.candidateonboarding.entity.*;
import com.rbts.hrms.candidateonboarding.repository.CandidateDetailsRepository;
import com.rbts.hrms.candidateonboarding.repository.PositionProfileRepository;
import com.rbts.hrms.candidateonboarding.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PositionProfileServices {

    @Autowired
    PositionProfileRepository positionProfileRepository;
    @Autowired
    CandidateDetailsRepository candidateDetailsRepository;

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    AppProperties appProperties;
    /**
     * @param positionProfile to save entity
     * @return
     */

    public List<PositionProfileResponse> save(PositionProfileRequest positionProfile) throws DataIntegrityException, DataNotFoundException {

        List<PositionProfileResponse> list=new ArrayList<>();
        for(CandidateDetails c:positionProfile.getCandidateDetails())
        {
            PositionProfile p=convertRequest(positionProfile,c);
            positionProfileRepository.save(p);
            PositionProfileResponse positionProfileResponse = convertResponse(p);
            list.add(positionProfileResponse);

        }
        return list;


    }

    private PositionProfileResponse convertResponse(PositionProfile p) throws DataNotFoundException {
        PositionProfileResponse positionProfileResponse=new PositionProfileResponse();
        positionProfileResponse.setId(p.getId());
        positionProfileResponse.setIsActive(p.getIsActive());
        CandidateDetails candidateDetails=candidateDetailsRepository.getById(p.getCandidateDetails().getId());
        if(candidateDetails!=null)
        {
            positionProfileResponse.setCandidateDetails(candidateDetails);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }

        Position position=positionRepository.getById(p.getPosition().getId());
        if(position!=null)
        {
            positionProfileResponse.setPosition(position);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
        return positionProfileResponse;
    }

    private PositionProfile convertRequest(PositionProfileRequest positionProfile, CandidateDetails c) {
        PositionProfile positionProfile1=new PositionProfile();
        positionProfile1.setPosition(positionProfile.getPosition());
        positionProfile1.setCandidateDetails(c);
        positionProfile1.setIsActive(positionProfile.getIsActive());
        return positionProfile1;
    }

    /**
     * update positionProfile
     * @param id for update positionProfile
     * @param positionProfile
     * @return
     */

    public  List<PositionProfileResponse> update(Long id, PositionProfileRequest positionProfile) throws DataNotFoundException {

        List<PositionProfileResponse> list=new ArrayList<>();
        for(CandidateDetails c:positionProfile.getCandidateDetails())
        {
            PositionProfile p=convertRequest(positionProfile,c);
            p.setId(id);
            positionProfileRepository.save(p);
            PositionProfileResponse positionProfileResponse = convertResponse(p);
            list.add(positionProfileResponse);

        }
        return list;

    }
    /**
     *
     * @return all positionProfile details
     */

    public List<PositionProfile> getAll() throws DataNotFoundException {
        List <PositionProfile> list =positionProfileRepository.getData();
        if(list.isEmpty()){
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return list;
        }

    }
    /**
     *
     * @param id of positionProfile for retrieve
     * @return
     */

    public PositionProfile getById(Long id) throws DataNotFoundException {
        PositionProfile positionProfile=positionProfileRepository.getById(id);
        if(positionProfile == null){
            throw new DataNotFoundException(appProperties.getData());
        }else{
            return positionProfile;
        }
    }

    public void delete(Long id) throws DataNotFoundException {
        PositionProfile positionProfile=positionProfileRepository.getById(id);
        if(positionProfile!=null)
        {
           positionProfileRepository.delete(positionProfile);
        }else {
            throw new DataNotFoundException(appProperties.getData());
        }
    }

    public PositionProfile getByCandidateId(Long id) throws DataNotFoundException {
        PositionProfile positionProfile=positionProfileRepository.getByCandidateId(id);
        if(positionProfile!=null)
        {
           return positionProfile;
        }else {
           throw new DataNotFoundException(appProperties.getData());
        }
    }

    public List<PositionProfile> findAllData() throws DataNotFoundException {
        List<PositionProfile> positionProfiles=positionProfileRepository.findAllData();
        if(positionProfiles.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return positionProfiles;
        }
    }
}
