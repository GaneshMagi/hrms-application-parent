package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.City;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.CityRepository;
import com.rbts.hrms.candidateonboarding.service.CityService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CityResource {

    private final Logger log = LoggerFactory.getLogger(CityResource.class);

    private static final String ENTITY_NAME = "candidateonboardingCity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
    AppProperties appProperties;
    private final CityService cityService;

    private final CityRepository cityRepository;

    public CityResource(CityService cityService, CityRepository cityRepository) {
        this.cityService = cityService;
        this.cityRepository = cityRepository;
    }

    /**
     * {@code POST  /cities} : Create a new city.
     *
     * @param city the city to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new city, or with status {@code 400 (Bad Request)} if the city has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cities")
    public ResponseEntity<City> createCity(@RequestBody City city) throws URISyntaxException {
        log.debug("REST request to save City : {}", city);
        if (city.getId() != null) {
            try {
                throw new BadRequestAlertException("A new city cannot already have an ID", ENTITY_NAME, "idexists");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        City result = null;
        try {
            result = cityService.save(city);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DataIntegrityException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cities/:id} : Updates an existing city.
     *
     * @param id the id of the city to save.
     * @param city the city to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated city,
     * or with status {@code 400 (Bad Request)} if the city is not valid,
     * or with status {@code 500 (Internal Server Error)} if the city couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cities/{id}")
    public ResponseEntity<City> updateCity(@PathVariable(value = "id", required = false) final Long id, @RequestBody City city)
        throws URISyntaxException {
        log.debug("REST request to update City : {}, {}", id, city);
        if (city.getId() == null) {
            try {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Objects.equals(id, city.getId())) {
            try {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }

        if (!cityRepository.existsById(id)) {
            try {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }

        City result = cityService.update(city);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, city.getId().toString()))
            .body(result);
    }



    /**
     * {@code GET  /cities} : get all the cities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cities in body.
     */
    @GetMapping("/cities")
    public List<City> getAllCities( ) {
        log.debug("REST request to get all Cities");

        try {
            return cityService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @return list of city  sort by name
     */
    @GetMapping("/getAll/cities")
    public List<City> getAllCitiesByName( ) {
        log.debug("REST request to get all Cities");

        try {
            return cityService.findAllCities();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * {@code GET  /cities/:id} : get the "id" city.
     *
     * @param id the id of the city to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the city, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cities/{id}")
    public City getCity(@PathVariable Long id) {
        log.debug("REST request to get City : {}", id);
        City city = null;

        try {

            city = cityService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return city;
    }

    /**
     * {@code DELETE  /cities/:id} : delete the "id" city.
     *
     * @param id the id of the city to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable Long id)  {
        log.debug("REST request to delete City : {}", id);
        try {
            cityService.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }






}
