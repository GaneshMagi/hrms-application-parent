package com.rbts.hrms.candidateonboarding.controller;



import com.github.javafaker.App;
import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.VendorDetails;
import com.rbts.hrms.candidateonboarding.service.VendorDetailsServices;
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
public class VendorDetailsResource {
    private final Logger log = LoggerFactory.getLogger(BenefitsAndPerksResource.class);
    @Autowired
    VendorDetailsServices vendorDetailsServices;
    @Autowired
    AppProperties appProperties;

    /**
     * add new vendorDetails
     * @param vendorDetails  to create vendorDetails
     * @return
     */

    @PostMapping("/vendor")
    public ResponseEntity<VendorDetails> createdVendorDetails(@RequestBody VendorDetails vendorDetails) throws URISyntaxException {
        log.debug("REST request to save VendorDetails : {}", vendorDetails);
        VendorDetails result= null;

        try {
            result=vendorDetailsServices.save(vendorDetails);
        } catch (DataalreadyexistsException | DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.created(new URI("/api/vendor")).body(result);
    }
    /**
     * update vendorDetails
     * @param id vendorDetails id
     * @param vendorDetails update vendorDetails details
     * @return
     */

    @PutMapping("/vendor/{id}")
    public VendorDetails update(@PathVariable Long id, @RequestBody VendorDetails vendorDetails){

        try {
            return vendorDetailsServices.update(id,vendorDetails);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    /**
     * list of all vendorDetails
     * @return
     */

    @GetMapping("/vendor/getall")
    public List<VendorDetails> getAll(){

        try {
            return vendorDetailsServices.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @return list of vendor sort by it name
     */
    @GetMapping("/vendor")
    public List<VendorDetails> findAll(){

        try {
            return vendorDetailsServices.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     *
     * @param id the id of the vendorDetails to retrieve.
     * @return
     */
    @GetMapping("/vendor/{id}")
    public VendorDetails getById(@PathVariable(name="id") Long id){

        try {
            return vendorDetailsServices.getById(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/vendor/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            vendorDetailsServices.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }
}
