package com.rbts.hrms.candidateonboarding.controller;

import com.github.javafaker.App;
import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.JobType;
import com.rbts.hrms.candidateonboarding.service.JobTypeServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class JobTypeResource {
    private final Logger log = LoggerFactory.getLogger(JobTypeResource.class);

    @Autowired
    JobTypeServices jobTypeServices;

    @Autowired
    AppProperties appProperties;


    /**
     * add new jobtype
     * @param jobtype  to create jobtype
     * @return
     */

    @PostMapping("/jobtype")
    public ResponseEntity<JobType> createJobType(@RequestBody JobType jobtype) throws URISyntaxException {
        log.debug("REST request to save JobType : {}", jobtype);
        JobType result= null;

        try {
            result=jobTypeServices.save(jobtype);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        } catch (DataalreadyexistsException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.created(new URI("/api/jobtype")).body(result);
    }

    /**
     * update jobtype
     * @param id jobtype id
     * @param jobType update jobtype details
     * @return
     */
    @PutMapping("/jobtype/{id}")
    public JobType update(@PathVariable(name = "id") Long id, @RequestBody JobType jobType){

        try {
            return jobTypeServices.update(id,jobType);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * list of all jobtype
     * @return
     */

    @GetMapping("/jobtype/getall")
    public List<JobType> getAll(){

        try {
            return jobTypeServices.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return list of job type sort by name
     */
    @GetMapping("/jobtype")
    public List<JobType> getAllByName(){

        try {
            return jobTypeServices.getAllByName();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * @param id the id of the jobtype to retrieve.
     * @return
     */

    @GetMapping("/jobtype/{id}")
    public JobType getById(@PathVariable(name= "id") Long id){
        try{
            return jobTypeServices.getById(id);
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param id
     */
    @DeleteMapping("/jobtype/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            jobTypeServices.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }




}

