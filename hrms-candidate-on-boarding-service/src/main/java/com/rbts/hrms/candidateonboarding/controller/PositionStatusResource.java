package com.rbts.hrms.candidateonboarding.controller;

import com.rbts.hrms.candidateonboarding.customexception.*;
import com.rbts.hrms.candidateonboarding.entity.PositionStatus;
import com.rbts.hrms.candidateonboarding.entity.Status;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.service.PositionStatusService;
import org.checkerframework.checker.units.qual.A;
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

@RestController
@RequestMapping("/api")
public class PositionStatusResource {
    private final Logger log = LoggerFactory.getLogger(PositionStatusResource.class);
    @Autowired
    PositionStatusService positionStatusService;

    @Autowired
    AppProperties appProperties;

    @Autowired
    private PositionStatusService statusService;

    /**
     * save position status
     * @param status for save
     * @return
     * @throws URISyntaxException
     */


    @PostMapping("/position/status")
    public ResponseEntity<PositionStatus> createStatus(@RequestBody PositionStatus status) throws URISyntaxException, DataIntegrityException, DataalreadyexistsException ,URISyntaxException{
        log.debug("REST request to save Status : {}", status);

        PositionStatus result = positionStatusService.save(status);

        return ResponseEntity.created(new URI("/api/position/status")).body(result);
    }


    /**
     * update position status
     * @param status for update detials
     * @param id of position status
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/position/status/{id}")
    public ResponseEntity<PositionStatus> updateStatus(@RequestBody PositionStatus status, @PathVariable(name = "id") Long id ) throws URISyntaxException {
        log.debug("REST request to save Status : {}", status);

        PositionStatus result = null;
        try {
            result = positionStatusService.update(status,id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(result);
    }







    @GetMapping("/position/status/{id}")
    public ResponseEntity<PositionStatus> getStatus( @PathVariable(name = "id") Long id ) throws URISyntaxException {
        PositionStatus result = null;
        try {
            result = positionStatusService.get(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(result);
    }

    /**
     *
     * @return all position status
     * @throws URISyntaxException
     */

    @GetMapping("/position/status")
    public List<PositionStatus> getAllStatus( ) throws URISyntaxException {
        List<PositionStatus> result = null;
        try {
            result = positionStatusService.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    /**
     *
     * @return list of position status sort by its name
     * @throws URISyntaxException
     */

    @GetMapping("/position/status/getAll")
    public List<PositionStatus> findAllData( ) throws URISyntaxException {
        List<PositionStatus> result = null;
        try {
            result = positionStatusService.findAllData();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }



//    @GetMapping("/position/status/last-6-months")
//    public List<PositionStatus> generateLast6MonthsReport() throws URISyntaxException{
//        List<PositionStatus> result = null;
//        try{
//            result = positionStatusService.findAllData();
//        }catch (DataNotFoundException e){
//            throw new RuntimeException(e);
//        }
//        return statusService.getLast6MonthsStatuses();
//    }

    /**
     * delete data from position status
     * @param id
     */
    @DeleteMapping("/position/status/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            positionStatusService.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }

}
