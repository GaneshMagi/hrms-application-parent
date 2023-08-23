package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.BenefitsAndPerks;
import com.rbts.hrms.candidateonboarding.service.BenefitsAndPerksServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.zip.DataFormatException;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class BenefitsAndPerksResource {
    @Autowired
    BenefitsAndPerksServices benefitsAndPerksServices;
    private final Logger log = LoggerFactory.getLogger(BenefitsAndPerksResource.class);

    /**
     * {@code POST  /Benefits} : Create a new benefitsAndPerks.
     *
     * @param benefitsAndPerks the benefitsAndPerks to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new benefitsAndPerks, or with status {@code 400 (Bad Request)} if the benefitsAndPerks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @Autowired
    AppProperties appProperties;
    @PostMapping("/benefits")
    public ResponseEntity<BenefitsAndPerks> createdBenefitsAndPerks(@RequestBody BenefitsAndPerks benefitsAndPerks)  throws URISyntaxException{
        log.debug("REST request to save BenefitsAndPerks : {}", benefitsAndPerks);
     BenefitsAndPerks result=null;

        try {
            result=benefitsAndPerksServices.save(benefitsAndPerks);

        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.created(new URI("/api/benefits")).body(result);

    }

    /**
     * {@code PUT  /employees/:id} : Updates an existing benefitsAndPerks.
     *
     * @param id               the id of the benefitsAndPerks to save.
     * @param benefitsAndPerks the benefitsAndPerks to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated benefitsAndPerks,
     * or with status {@code 400 (Bad Request)} if the benefitsAndPerks is not valid,
     * or with status {@code 500 (Internal Server Error)} if the benefitsAndPerks couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PutMapping("/benefits/{id}")
    public BenefitsAndPerks update(@PathVariable Long id, @RequestBody BenefitsAndPerks benefitsAndPerks) {

        try {
            return benefitsAndPerksServices.update(id, benefitsAndPerks);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * {@code GET  /benefitsAndPerks} : get all the benefitsAndPerks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of benefitsAndPerks in body.
     */

    @GetMapping("/benefits/getall")
    public List<BenefitsAndPerks> getAll() {

        try {
            return benefitsAndPerksServices.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @GetMapping("/benefits/{id}")
    public BenefitsAndPerks getById(@PathVariable(name="id") Long id){
        try {
            return benefitsAndPerksServices.getById(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * delete
     * @param id
     */
    @DeleteMapping("/benefits/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            benefitsAndPerksServices.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
         return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }

    /**
     *
     * @return list Benefits And Perks sort by its name
     */
    @GetMapping("/benefits")
    public List<BenefitsAndPerks> getAllName() {

        try {
            return benefitsAndPerksServices.getAllByName();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
