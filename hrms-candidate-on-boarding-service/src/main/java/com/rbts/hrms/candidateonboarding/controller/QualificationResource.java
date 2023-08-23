package com.rbts.hrms.candidateonboarding.controller;



import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.Qualification;
import com.rbts.hrms.candidateonboarding.service.QualificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QualificationResource {
    private final Logger log = LoggerFactory.getLogger(QualificationResource.class);

    @Autowired
    QualificationService qualificationService;

    @Autowired
    AppProperties appProperties;

    /**
     * add new qualification
     * @param qualification  to create qualification
     * @return
     */

    @PostMapping("/qualification")
    public ResponseEntity<Qualification> createdQualification(@RequestBody Qualification qualification) throws URISyntaxException {
        log.debug("REST request to save Qualification : {}", qualification);
        Qualification result=null;

        try {
           result=qualificationService.save(qualification);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataalreadyexistsException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.created(new URI("/api/qualification")).body(result);
    }


    /**
     * update qualification
     * @param id qualification id
     * @param qualification update qualification details
     * @return
     */
    @PutMapping("/qualification/{id}")
    public Qualification update(@PathVariable Long id, @RequestBody Qualification qualification){
        qualification.setId(id);
        return qualificationService.update(id, qualification);
    }

    /**
     * list of all qualification
     * @return
     */

    @GetMapping("/qualification/getall")
    public List<Qualification> getAll(){
        try {
            return  qualificationService.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return list of  qualification sort by it name
     */
    @GetMapping("/qualification")
    public List<Qualification> findAll(){
        try {
            return  qualificationService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param id the id of the qualification to retrieve.
     * @return
     */

    @GetMapping("/qualification/{id}")
    public Qualification getById(@PathVariable(name = "id") Long id){
        try {
            return  qualificationService.getById(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * delete qualification details
     * @param id
     * @return
     */

    @DeleteMapping("/qualification/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            qualificationService.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }
}
