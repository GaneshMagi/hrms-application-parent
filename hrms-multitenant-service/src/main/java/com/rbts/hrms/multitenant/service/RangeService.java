package com.rbts.hrms.multitenant.service;



import com.rbts.hrms.multitenant.customexception.AppProperties;
import com.rbts.hrms.multitenant.customexception.ResourceNotFoundException;
import com.rbts.hrms.multitenant.entity.Range;
import com.rbts.hrms.multitenant.repository.RangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing {@link Range}.
 */
@Service
@Transactional
public class RangeService {

    private final Logger log = LoggerFactory.getLogger(RangeService.class);

    private final RangeRepository rangeRepository;


    public RangeService(RangeRepository rangeRepository) {
        this.rangeRepository = rangeRepository;
    }

    @Autowired
    AppProperties appProperties;

    /**
     * Save a range.
     *
     * @param range the entity to save.
     * @return the persisted entity.
     */
    public Range save(Range range) throws ResourceNotFoundException {
        log.debug("Request to save Range : {}", range);
        Range r=rangeRepository.findByName(range.getEmpRange());
        if(r==null)
        {
            range=rangeRepository.save(range);
        }else
        {
            throw  new ResourceNotFoundException(appProperties.getRangeCreate());
        }
        return range ;
    }

    /**
     * Update a range.
     *
     * @param range the entity to save.
     * @return the persisted entity.
     */
    public Range update(Range range) {
        log.debug("Request to update Range : {}", range);
        return rangeRepository.save(range);
    }
    

    /**
     * Get all the ranges.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Range> findAll() throws ResourceNotFoundException {
        log.debug("Request to get all Ranges");
        List<Range> r=rangeRepository.findAllData();

        if(r.isEmpty())
        {
                throw new ResourceNotFoundException(appProperties.getRange());
        }else {

            return r;
        }

    }

    /**
     * Get one range by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Range findOne(Long id) throws ResourceNotFoundException {
        log.debug("Request to get Range : {}", id);
       Range r=rangeRepository.findOne(id);
       Range range = new Range();
        if(r==null)
        {
                throw  new ResourceNotFoundException(appProperties.getRange());
        }else {
            return r;

        }

    }

    /**
     * Delete the range by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Range : {}", id);
        rangeRepository.deleteById(id);
    }
}
