package com.rbts.hrms.candidateonboarding.controller;

import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.PositionStatus;
import com.rbts.hrms.candidateonboarding.entity.PositionType;
import com.rbts.hrms.candidateonboarding.service.PositionTypeService;
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
public class PositionTypeResource {

    private final Logger log = LoggerFactory.getLogger(PositionTypeResource.class);
    @Autowired
    PositionTypeService positionTypeService;

    @Autowired
    AppProperties appProperties;

    /**
     * create new position type
     * @param type for create position type
     * @return
     * @throws URISyntaxException
     * @throws DataIntegrityException
     * @throws DataalreadyexistsException
     */

    @PostMapping("/position/type")
    public ResponseEntity<PositionType> createType(@RequestBody PositionType type) throws URISyntaxException {
        log.debug("REST request to save type : {}", type);
        PositionType result = null;
        try {
            result = positionTypeService.save(type);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        } catch (DataalreadyexistsException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.created(new URI("/api/position/type")).body(result);
    }

    /**
     * update position type details
     * @param type
     * @param id for update
     * @return
     * @throws URISyntaxException
     * @throws DataIntegrityException
     * @throws DataalreadyexistsException
     */
    @PutMapping("/position/type/{id}")
    public ResponseEntity<PositionType> updateType(@RequestBody PositionType type, @PathVariable(name = "id") Long id)  {
        log.debug("REST request to update Status : {}", type);

        PositionType result = null;
        try {
            result = positionTypeService.update(type,id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(result);
    }

    /**
     * get position type
     * @param id
     * @return
     * @throws URISyntaxException
     * @throws DataIntegrityException
     * @throws DataalreadyexistsException
     */
    @GetMapping("/position/type/{id}")
    public ResponseEntity<PositionType> getType(@PathVariable(name = "id") Long id)  {

        PositionType result = null;
        try {
            result = positionTypeService.get(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(result);
    }


    /**
     * Get all position type
     * @return
     * @throws URISyntaxException
     * @throws DataIntegrityException
     * @throws DataalreadyexistsException
     */
    @GetMapping("/position/type/getall")
    public List<PositionType> getAllType() throws DataNotFoundException {
        List<PositionType> result = positionTypeService.getAll();

        return result;
    }


    /**
     *
     * @return list of position type sort by it name
     * @throws DataNotFoundException
     */

    @GetMapping("/position/type")
    public List<PositionType> findAll() throws DataNotFoundException {
        List<PositionType> result = positionTypeService.findAll();

        return result;
    }
    /**
     * delete position details
     * @param id
     * @return
     */
    @DeleteMapping("/position/type/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        try {
            positionTypeService.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }




}
