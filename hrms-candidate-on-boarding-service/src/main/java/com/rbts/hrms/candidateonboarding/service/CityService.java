package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.City;
import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link City}.
 */
@Service
@Transactional
public class CityService {

    private final Logger log = LoggerFactory.getLogger(CityService.class);

    private final CityRepository cityRepository;

    @Autowired
    AppProperties appProperties;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * Save a city.
     *
     * @param city the entity to save.
     * @return the persisted entity.
     */
    public City save(City city) throws ResourceNotFoundException, DataIntegrityException {
        log.debug("Request to save City : {}", city);
        if(city.getCityName()==null)
        {
            throw  new DataIntegrityException(appProperties.getCityName());
        }else{
        City city1=cityRepository.findByName(city.getCityName());
        if(city1!=null)
        {
                throw  new ResourceNotFoundException(appProperties.getCity());
        }else{
            city=cityRepository.save(city);
        }}

        return city;
    }

    /**
     * Update a city.
     *
     * @param city the entity to save.
     * @return the persisted entity.
     */
    public City update(City city) {
        log.debug("Request to update City : {}", city);
        return cityRepository.save(city);
    }


    /**
     * Get all the cities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<City> findAll() throws  DataNotFoundException {
        log.debug("Request to get all Cities");
        List<City>  city=cityRepository.getData();
        if(city!=null)
        {

        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
        return city;
    }

    /**
     * Get one city by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
   // @Transactional(readOnly = true)
    public City findOne(Long id) throws DataNotFoundException {
        City city= cityRepository.getById(id);
        System.out.println("!!!!!!!!!!!!!!!");
       if(city==null)
       {
           System.out.println("!!!!!!!!!111111!!!!!!");
           throw  new DataNotFoundException(appProperties.getData());
       }else {
           return city;


       }


    }

    /**
     * Delete the city by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) throws DataNotFoundException {
        log.debug("Request to delete City : {}", id);
        City city=cityRepository.getById(id);
        if(city!=null)
        {
            cityRepository.deleteById(city.getId());
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }

    }

    public List<City> findAllCities() throws DataNotFoundException {
        List<City> cities=cityRepository.getAllData();
        if(cities.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else
        {
            return cities;
        }
    }
}
