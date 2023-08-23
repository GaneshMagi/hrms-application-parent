package com.rbts.hrms.candidateonboarding.controller;



import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.PanelSkillResponse;
import com.rbts.hrms.candidateonboarding.dto.PositionProfileRequest;
import com.rbts.hrms.candidateonboarding.dto.PositionProfileResponse;
import com.rbts.hrms.candidateonboarding.entity.PositionProfile;
import com.rbts.hrms.candidateonboarding.service.PositionProfileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class PositionProfileResource {
    @Autowired
    PositionProfileServices positionProfileServices;
    @Autowired
    AppProperties appProperties;

    /**
     * add new positionProfile
     * @param positionProfile  to create positionProfile
     * @return
     */

    @PostMapping("/positionProfile")
    public List<PositionProfileResponse> save(@RequestBody PositionProfileRequest positionProfile){
        List<PositionProfileResponse> responses=new ArrayList<>();
        try {
            responses= positionProfileServices.save(positionProfile);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return responses;


    }
    /**
     * update positionProfile
     * @param id positionProfile id
     * @param positionProfile update positionProfile details
     * @return
     */

    @PutMapping("/positionProfile/{id}")
    public List<PositionProfileResponse> update(@RequestBody PositionProfileRequest positionProfile,@PathVariable(name = "id") Long id){
        List<PositionProfileResponse> responses=new ArrayList<>();
        try {
            responses= positionProfileServices.update(id,positionProfile);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return responses;


    }
    /**
     * list of all positionProfile
     * @return
     */

    @GetMapping("/positionProfile/getall")
    public List<PositionProfile> getAll(){

        try {
            return positionProfileServices.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @return list of position profile sort by candidate name
     */


    @GetMapping("/positionProfile")
    public List<PositionProfile> findAllData(){

        try {
            return positionProfileServices.findAllData();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @param id the id of the positionProfile to retrieve.
     * @return
     */
    @GetMapping("/positionProfile/{id}")
    public PositionProfile getById(@PathVariable(name="id") Long id){
        try {
            return positionProfileServices.getById(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * de
     * @param id
     * @return
     */
    @DeleteMapping("/positionProfile/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            positionProfileServices.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }


    @GetMapping("/positionProfile/candidate/{id}")
    public PositionProfile getByCandidateId(@PathVariable(name="id") Long id){
        try {
            return positionProfileServices.getByCandidateId(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



}

