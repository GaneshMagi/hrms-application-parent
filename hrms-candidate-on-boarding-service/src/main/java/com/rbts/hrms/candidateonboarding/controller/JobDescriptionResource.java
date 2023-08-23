package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.JobDescription;
import com.rbts.hrms.candidateonboarding.service.JobDescriptionServices;
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
public class JobDescriptionResource {
    private final Logger log = LoggerFactory.getLogger(JobDescriptionResource.class);

    @Autowired
    JobDescriptionServices jobDescriptionServices;
    @Autowired
    AppProperties appProperties;

    /**
     * create new JobDescription
     * @param jobDescription for the  JobDescription
     * @return
     */
    @PostMapping("/jd")
    public ResponseEntity<JobDescription> createdJobDescrption(@RequestBody JobDescription jobDescription) throws URISyntaxException {
        log.debug("REST request to save JobDescription : {}", jobDescription);
        JobDescription result = null;
        try {
            result=jobDescriptionServices.save(jobDescription);
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.created(new URI("/api/jd")).body(result);

    }


    /**
     * update details of JobDescription
     * @param id for uodate JobDescription
     * @param jobDescription
     * @return
     */
    @PutMapping("/jd/{id}")
    public JobDescription update(@PathVariable Long id, @RequestBody JobDescription jobDescription){

        try {
            return jobDescriptionServices.update(id, jobDescription);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * return all JobDescription list
     * @return
     */
    @GetMapping("/jd/getall")
    public List<JobDescription> getAll(){
        try {
            return  jobDescriptionServices.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * @return list of jd sor by salary range
     */
    @GetMapping("/jd")
    public List<JobDescription> getAllData(){
        try {
            return  jobDescriptionServices.getAllData();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/jd/{id}")
    public JobDescription getById(@PathVariable(name = "id") Long id){
        try {
            return  jobDescriptionServices.getById(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * delete  data from
     * @param id
     */
    @DeleteMapping("/jd/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            jobDescriptionServices.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }


}
