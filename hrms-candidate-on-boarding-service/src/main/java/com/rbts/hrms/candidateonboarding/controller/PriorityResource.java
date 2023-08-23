package com.rbts.hrms.candidateonboarding.controller;



import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.Priority;
import com.rbts.hrms.candidateonboarding.service.PriorityServices;
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
public class PriorityResource {
    private final Logger log = LoggerFactory.getLogger(BenefitsAndPerksResource.class);
    @Autowired
    PriorityServices priorityServices;

    @Autowired
    AppProperties appProperties;

    /**
     * add new priority
     *
     * @param priority to create priority
     * @return
     */


    @PostMapping("/priority")
    public ResponseEntity<Priority> createdPriority(@RequestBody Priority priority) throws URISyntaxException {
        log.debug("REST request to save Priority : {}", priority);
        Priority result=null;

        try {
            result=priorityServices.save(priority);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        } catch (DataalreadyexistsException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.created(new URI("/api/priority")).body(result);
    }

    /**
     * update priority
     *
     * @param id       priority id
     * @param priority update priority details
     * @return
     */
    @PutMapping("/priority/{id}")
    public Priority update(@PathVariable Long id, @RequestBody Priority priority) {

        try {
            priority.setId(id);
            return priorityServices.update(id, priority);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * list of all priority
     *
     * @return
     */

    @GetMapping("/priority/getall")
    public List<Priority> getAll() {

        try {
            return priorityServices.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     *
     * @return list of priority sort by its name
     */
    @GetMapping("/priority")
    public List<Priority> findAllData() {

        try {
            return priorityServices.findAllData();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * @param id the id of the priority to retrieve.
     * @return
     */
    @GetMapping("/priority/{id}")
    public Priority getById(@PathVariable(name = "id") Long id) {

        try {
            return priorityServices.getById(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @DeleteMapping("/priority/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            priorityServices.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
       return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }

}

