package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.Employee;
import com.rbts.hrms.candidateonboarding.entity.PanelDetails;
import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.Status;
import com.rbts.hrms.candidateonboarding.repository.EmployeeRepository;
import com.rbts.hrms.candidateonboarding.repository.PanelDetailsRepository;
import com.rbts.hrms.candidateonboarding.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PanelDetails}.
 */
@Service
@Transactional
public class PanelDetailsService {

    private final Logger log = LoggerFactory.getLogger(PanelDetailsService.class);

    private final PanelDetailsRepository panelDetailsRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    AppProperties appProperties;

    public PanelDetailsService(PanelDetailsRepository panelDetailsRepository) {
        this.panelDetailsRepository = panelDetailsRepository;
    }

    /**
     * Save a panelDetails.
     *
     * @param panelDetails the entity to save.
     * @return the persisted entity.
     */
    public PanelDetails save(PanelDetails panelDetails) throws ResourceNotFoundException, DataNotFoundException, DataIntegrityException {
        log.debug("Request to save PanelDetails : {}", panelDetails);
      //check for not accepting employee id as null
        if(panelDetails.getEmployee()==null)
        {
            throw  new DataIntegrityException(appProperties.getEmpdata());
        }else {
            PanelDetails panel =panelDetailsRepository.findByid(panelDetails.getEmployee().getId());
            //check same employee is alreday present in panel or not
            if (panel != null) {
                throw new ResourceNotFoundException(appProperties.getPaneldetails());
            } else {
                Status status = statusRepository.getReferenceById(panelDetails.getStatusId().getId());
                panelDetails.setStatusId(status);
                Employee e = employeeRepository.getById(panelDetails.getEmployee().getId());
                if (e == null) {
                    throw new DataNotFoundException(appProperties.getEmployeedata());

                } else {
                    panelDetails.setEmployee(e);
                }
            }

            panelDetails = panelDetailsRepository.save(panelDetails);
        }

        return panelDetails;
    }

    /**
     * Update a panelDetails.
     *
     * @param panelDetails the entity to save.
     * @return the persisted entity.
     */
    public PanelDetails update(PanelDetails panelDetails) {
        log.debug("Request to update PanelDetails : {}", panelDetails);
        return panelDetailsRepository.save(panelDetails);
    }



    /**
     * Get all the panelDetails.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PanelDetails> findAll() throws DataNotFoundException {
        log.debug("Request to get all PanelDetails");
        List<PanelDetails> panelDetails=panelDetailsRepository.getAlData();
        if(panelDetails.isEmpty())
        {
          throw  new DataNotFoundException(appProperties.getData());
        }else {
            return panelDetails;
        }



    }

    /**
     * Get one panelDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public PanelDetails findOne(Long id) throws DataNotFoundException {

        PanelDetails details=panelDetailsRepository.get(id);
        if(details==null)
        {
            throw new DataNotFoundException(appProperties.getData());
        }else {
            return details;
        }


    }

    /**
     * Delete the panelDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PanelDetails : {}", id);
        panelDetailsRepository.deleteById(id);
    }

    public List<PanelDetails> findByDesignation(String designation) throws DataNotFoundException {

        List<PanelDetails> panelDetails=panelDetailsRepository.getAllByDesignation(designation);
        if(panelDetails.isEmpty())
        {throw  new DataNotFoundException(appProperties.getData());

        }else {
            return panelDetails;
        }
    }
}
