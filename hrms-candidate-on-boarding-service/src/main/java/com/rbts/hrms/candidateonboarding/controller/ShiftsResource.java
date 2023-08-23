package com.rbts.hrms.candidateonboarding.controller;



import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.Shifts;
import com.rbts.hrms.candidateonboarding.service.ShiftsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ShiftsResource {
    @Autowired
    ShiftsServices shiftsServices;

    @Autowired
    AppProperties appProperties;
    /**
     * create new shifts
     * @param shifts the shifts create
     * @return
     */

    @PostMapping("/shifts")
    public Shifts save(@RequestBody Shifts shifts){
        try {
            return shiftsServices.save(shifts);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        } catch (DataalreadyexistsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * update shifts details
     * @param id for update shifts details
     * @param shifts
     * @return
     */
    @PutMapping("/shifts/{id}")
    public Shifts update(@PathVariable Long id, @RequestBody Shifts shifts){

        try {
            return shiftsServices.update(id,shifts);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * get all shifts details
     * @return
     */
    @GetMapping("/shifts/getall")
    public List<Shifts> getAll(){
        try {
            return shiftsServices.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return list of shifts sort by it name
     */

    @GetMapping("/shifts")
    public List<Shifts> findAll(){
        try {
            return shiftsServices.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }




    /**
     * return data of one shift
     * @param id of shift
     * @return
     */

    @GetMapping("/shifts/{id}")
    public Shifts getAll(@PathVariable(name = "id") Long id){
        try {
            return shiftsServices.getById(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/shifts/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            shiftsServices.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }
}
