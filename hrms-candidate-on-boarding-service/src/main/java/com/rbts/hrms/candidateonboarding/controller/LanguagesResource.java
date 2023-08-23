package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.Languages;
import com.rbts.hrms.candidateonboarding.service.LanguagesServices;
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
public class LanguagesResource {

    private final Logger log = LoggerFactory.getLogger(LanguagesResource.class);
    @Autowired
    LanguagesServices languagesServices;


    @Autowired
    AppProperties appProperties;


    /**
     * create new languages
     * @param languages the languages to create.
     * @return
     */
    @PostMapping("/languages")
    public ResponseEntity<Languages> CreatedLanguages(@RequestBody Languages languages) throws URISyntaxException {
        log.debug("REST request to save Languages : {}", languages);
        Languages result= null;
        try {
           result=languagesServices.save(languages);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        } catch (DataalreadyexistsException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.created(new URI("/api/languages")).body(result);
    }

    /**
     * update languages details
     * @param id
     * @param languages the languages to update.
     * @return
     */
    @PutMapping("/languages/{id}")
    public Languages update(@PathVariable Long id, @RequestBody Languages languages){
        return languagesServices.update(id,languages);
    }

    /**
     * get all languages details
     * @return
     */
    @GetMapping("/languages/getall")
    public List<Languages> getAll(){
        try {
            return languagesServices.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return list of  languages sort by name
     */
    @GetMapping("/languages")
    public List<Languages> findAllData(){
        try {
            return languagesServices.findAllData();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * get one language details
     * @return
     */
    @GetMapping("/languages/{id}")
    public Languages getById(@PathVariable(name = "id") Long id){
        try {
            return languagesServices.getById(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * delete languages
     * @param id of languages  for delete
     */
    @DeleteMapping("/languages/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            languagesServices.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }



}

