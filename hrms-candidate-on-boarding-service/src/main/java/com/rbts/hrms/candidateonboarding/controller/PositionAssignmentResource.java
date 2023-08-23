package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.PositionAssignmentResponse;
import com.rbts.hrms.candidateonboarding.entity.PositionAssignment;
import com.rbts.hrms.candidateonboarding.service.PositionAssignmentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class PositionAssignmentResource {

    @Autowired
    PositionAssignmentServices positionAssignmentServices;
    @Autowired
    AppProperties appProperties;


    /**
     * add new positionAssignment
     * @param positionAssignment  to create positionAssignment
     * @return
     */

    @PostMapping("/positionAssignment")
    public PositionAssignmentResponse save(@RequestBody   PositionAssignment positionAssignment, HttpServletRequest request){

        String tenantId = request.getHeader("X-TenantID");
        try {
            return positionAssignmentServices.save(positionAssignment,tenantId);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * update positionAssignment
     * @param id positionAssignment id
     * @param positionAssignment update positionAssignment details
     * @return
     */

    @PutMapping("/positionAssignment/{id}")
    public PositionAssignmentResponse update(@PathVariable Long id, @RequestBody PositionAssignment positionAssignment, HttpServletRequest request){
        String tenantId = request.getHeader("X-TenantID");
        try {
            return positionAssignmentServices.update(id,positionAssignment,tenantId);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * list of all positionAssignment
     * @return
     */

    @GetMapping("/positionAssignment/getall")
    public List<PositionAssignmentResponse> getAll(HttpServletRequest request){
        String tenantId = request.getHeader("X-TenantID");
        try {
            return positionAssignmentServices.getAll(tenantId);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @param request
     * @return list of  position assignment sort by position name
     */
    @GetMapping("/positionAssignment")
    public List<PositionAssignmentResponse> findAllData(HttpServletRequest request){
        String tenantId = request.getHeader("X-TenantID");
        try {
            return positionAssignmentServices.findAllData(tenantId);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * list of aLast 6 months report positionAssignment
     * @return
     */

    @GetMapping("/positionAssignments/{userId}/{employeeId}")
    public List<PositionAssignment> findPositionAssignments(@PathVariable Long userId, @PathVariable String employeeId) {
        return positionAssignmentServices.findData(userId, employeeId);
    }



//    @GetMapping("/count")
//    public Long countPositionAssignments(
//        @RequestParam("userId") int userId,
//        @RequestParam("empId") int empId
//    ) {
//        Date sixMonthsAgo = positionAssignmentServices.getSixMonthsAgo();
//        return positionAssignmentServices.countPositionAssignmentsLast6Months(userId, empId,sixMonthsAgo);
//    }




    /**
     *
     * @param id the id of the positionAssignment to retrieve.
     * @return
     */

    @GetMapping("/positionAssignment/{id}")
    public PositionAssignmentResponse getById(@PathVariable(name ="id") Long id, HttpServletRequest request){
        String tenantId = request.getHeader("X-TenantID");
        try{
            return positionAssignmentServices.getById(id,tenantId);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     * delete Position Assignment details
     * @param id
     * @return
     */
    @DeleteMapping("/positionAssignment/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            positionAssignmentServices.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }


}
