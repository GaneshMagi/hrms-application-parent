package com.rbts.hrms.candidateonboarding.controller;

import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.Events;
import com.rbts.hrms.candidateonboarding.entity.PositionStatus;
import com.rbts.hrms.candidateonboarding.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventsResource {

    private final Logger log = LoggerFactory.getLogger(EventsResource.class);
    @Autowired
    EventsService eventsService;

    @Autowired
    AppProperties appProperties;

    /**
     * create new event
     * @param events
     * @return
     * @throws URISyntaxException
     * @throws DataIntegrityException
     * @throws DataalreadyexistsException
     */
    @PostMapping("/events")
    public ResponseEntity<Events> createEvent(@RequestBody Events events) throws URISyntaxException  {
        log.debug("REST request to save Status : {}", events);

        Events result = null;
        try {
            result = eventsService.save(events);
        } catch (DataalreadyexistsException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.created(new URI("/api/events")).body(result);
    }


    @PutMapping("/events/{id}")
    public ResponseEntity<Events> updateEvent(@RequestBody Events events,@PathVariable(name = "id")Long id){
        log.debug("REST request to save Status : {}", events);

        Events result = null;
        try {
            result = eventsService.update(events,id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(result);
    }


    /**
     *
     * @param id
     * @return event data
     */
    @GetMapping("/events/{id}")
    public ResponseEntity<Events> get(@PathVariable(name = "id")Long id){
        Events result = null;
        try {
            result = eventsService.getById(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(result);
    }


    /**
     *
     * @return list of events
     */
    @GetMapping("/events")
    public List<Events> get(){
        List<Events> result = null;
        try {
            result = eventsService.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    @GetMapping("/events/getAll")
    public List<Events> getAll(){
        List<Events> result = null;
        try {
            result = eventsService.getAllByName();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }



    @DeleteMapping("/events/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
        try {
            eventsService.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }


}
