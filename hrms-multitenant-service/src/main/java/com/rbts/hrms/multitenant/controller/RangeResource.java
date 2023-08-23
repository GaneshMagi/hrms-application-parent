package com.rbts.hrms.multitenant.controller;



import com.rbts.hrms.multitenant.customexception.ResourceNotFoundException;
import com.rbts.hrms.multitenant.entity.Range;
import com.rbts.hrms.multitenant.repository.RangeRepository;
import com.rbts.hrms.multitenant.service.RangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class RangeResource {

    private final Logger log = LoggerFactory.getLogger(RangeResource.class);

    private static final String ENTITY_NAME = "multitenantRange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RangeService rangeService;

    private final RangeRepository rangeRepository;

    public RangeResource(RangeService rangeService, RangeRepository rangeRepository) {
        this.rangeService = rangeService;
        this.rangeRepository = rangeRepository;
    }

    /**
     * {@code POST  /ranges} : Create a new range.
     *
     * @param range the range to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new range, or with status {@code 400 (Bad Request)} if the range has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ranges")
    public ResponseEntity<Range> createRange(@RequestBody Range range) throws URISyntaxException{
        log.debug("REST request to save Range : {}", range);

        Range result = null;
        try {
            result = rangeService.save(range);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/ranges/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /ranges/:id} : Updates an existing range.
     *
     * @param id the id of the range to save.
     * @param range the range to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated range,
     * or with status {@code 400 (Bad Request)} if the range is not valid,
     * or with status {@code 500 (Internal Server Error)} if the range couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ranges/{id}")
    public ResponseEntity<Range> updateRange(@PathVariable(value = "id", required = false) final Long id, @RequestBody Range range)
            throws URISyntaxException {
        log.debug("REST request to update Range : {}, {}", id, range);
        range.setId(id);
        Range result = rangeService.update(range);
        return ResponseEntity
            .ok()
            .body(result);
    }


    /**
     * {@code GET  /ranges} : get all the ranges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ranges in body.
     */
    @GetMapping("/ranges")
    public List<Range> getAllRanges() {
        log.debug("REST request to get all Ranges");
        try {
            return rangeService.findAll();
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code GET  /ranges/:id} : get the "id" range.
     *
     * @param id the id of the range to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the range, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ranges/{id}")
    public Range getRange(@PathVariable Long id) throws ResourceNotFoundException {
        log.debug("REST request to get Range : {}", id);
        Range range = rangeService.findOne(id);

        return range;
    }

    /**
     * {@code DELETE  /ranges/:id} : delete the "id" range.
     *
     * @param id the id of the range to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ranges/{id}")
    public ResponseEntity<Void> deleteRange(@PathVariable Long id) {
        log.debug("REST request to delete Range : {}", id);
        rangeService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
