package com.rbts.hrms.candidateonboarding.controller;



import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.Position;
import com.rbts.hrms.candidateonboarding.service.PositionService;
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
public class PositionResource {
    private final Logger log = LoggerFactory.getLogger(PositionResource.class);

    @Autowired
    PositionService positionService;

    @Autowired
    AppProperties appProperties;

    /**
     * add new position
     * @param position  to create position
     * @return
     */

    @PostMapping("/position")
    public ResponseEntity<Position> createdPosition(@RequestBody Position position) throws URISyntaxException {
        log.debug("REST request to save Position : {}", position);
        Position result=null;

        try {
            result=positionService.save(position);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.created(new URI("/api/position")).body(result);
    }

    /**
     * update position
     * @param id position id
     * @param position update position details
     * @return
     */

    @PutMapping("/position/{id}")
    public Position update(@PathVariable Long id, @RequestBody Position position){

        try {
            return positionService.update(id,position);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * list of all position
     *
     * @return
     */

    @GetMapping("/position/getall")
    public List<Position> getAll(){

        try {
            return  positionService.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     *
     * @return list of position sort by name
     */
    @GetMapping("/position")
    public List<Position> findAllData(){

        try {
            return  positionService.findAllData();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }


    }


    /**
     *
     * @param id the id of the position to retrieve.
     * @return
     */

    @GetMapping("/position/{id}")
    public Position getById(@PathVariable(name="id" ) Long id){

        try {
            return  positionService.getById(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     *
     * @param status
     * @return list of position that status is open and sort by open date
     */
    @GetMapping("/position/status/get/{status}")
    public List<Position> getByPositionStatus(@PathVariable(name="status" ) String status){

        try {
            return  positionService.getByPositionStatus(status);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @param status
     * @return list of position that status is close and sort by close date
     */

    @GetMapping("/position/status/close/{status}")
    public List<Position> getByPosition(@PathVariable(name="status" ) String status){

        try {
            return  positionService.getByPosition(status);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @param status
     * @return list of position that status on-hold and cancelled
     */
    @GetMapping("/position/status/name/{status}")
    public List<Position> getByStatusName(@PathVariable(name="status" ) String status){

        try {
            return  positionService.getByStatusName(status);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



    /**
     * delete position details
     * @param id
     */

    @DeleteMapping("/position/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
        try {
            positionService.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }


}
