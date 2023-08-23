package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.controller.JobDescriptionResource;
import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.Employee;
import com.rbts.hrms.candidateonboarding.entity.JobDescription;
import com.rbts.hrms.candidateonboarding.entity.PanelDetails;
import com.rbts.hrms.candidateonboarding.entity.Position;
import com.rbts.hrms.candidateonboarding.repository.EmployeeRepository;
import com.rbts.hrms.candidateonboarding.repository.JobDescriptionRepository;
import com.rbts.hrms.candidateonboarding.repository.PanelDetailsRepository;
import com.rbts.hrms.candidateonboarding.repository.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PositionService {
    private final Logger log = LoggerFactory.getLogger(PositionService.class);

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    JobDescriptionRepository jobDescriptionRepository;

    @Autowired
    PanelDetailsRepository panelDetailsRepository;

    @Autowired
    AppProperties appProperties;

    @Autowired
    EmployeeRepository employeeRepository;


    /**
     *
     * @param position to save entity
     * @return
     */
    public Position save(Position position) throws DataIntegrityException, DataNotFoundException {
        if(position.getJobdescription()==null)
        {
         throw  new DataIntegrityException(appProperties.getJobdescription());
        }else {
            //check JobDescription is present
            JobDescription d=jobDescriptionRepository.getById(position.getJobdescription().getId());
            if(d==null)
            {
                throw  new DataNotFoundException(appProperties.getJd());
            }else {
                position.setJobdescription(d);
            }

            Employee employee=employeeRepository.getById(position.getEmployee().getId());
            if(employee!=null)
            {
                position.setEmployee(employee);
            }else {
                throw  new DataNotFoundException(appProperties.getEmployeedetails());
            }

            Set<PanelDetails> panelDetailsSet=new HashSet<>();
             for(PanelDetails panelDetails:position.getFirstLevelPanel() )
             {

                 PanelDetails panelDetails1=panelDetailsRepository.get(panelDetails.getId());
                 if(panelDetails1!=null)
                 {
                     panelDetailsSet.add(panelDetails1);

                 }else {
                     throw  new DataNotFoundException(appProperties.getPanelDetails1());
                 }
             }
            position.setFirstLevelPanel(panelDetailsSet);

            Set<PanelDetails> panelDetailsSet1=new HashSet<>();
            for(PanelDetails panelDetails:position.getSecondLevelPanel() )
            {
              log.debug("*************************");

                PanelDetails panelDetails1=panelDetailsRepository.get(panelDetails.getId());
                if(panelDetails1!=null)
                {

                    panelDetailsSet1.add(panelDetails1);

                }else {
                    throw  new DataNotFoundException(appProperties.getPanelDetails1());
                }
            }
            position.setSecondLevelPanel(panelDetailsSet1);
            return positionRepository.save(position);
        }


    }
    /**
     * update position
     * @param id for update position
     * @param position
     * @return
     */

    public Position update(Long id, Position position) throws DataNotFoundException {
        Position p=positionRepository.getById(id);
        if(p==null)
        {
           throw  new DataNotFoundException(appProperties.getData());
        }else {
            position.setId(id);
            return positionRepository.save(position);
        }

    }
    /**
     * @return all position details
     */

    public List<Position> getAll() throws  DataNotFoundException{
        List<Position> list= positionRepository.getData();
        if(list.isEmpty()){
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return list;
        }
    }
    /**
     *
     * @param id of position for retrieve
     * @return
     */
    public Position getById(Long id) throws  DataNotFoundException {
        Position position=positionRepository.getById(id);
        if(position==null){
            throw new DataNotFoundException(appProperties.getData());
        }else
        {
            return  position;
        }
    }

    /**
     * delete data from position
     * @param id
     */
    public void delete(Long id) throws DataNotFoundException {
        Position position=positionRepository.getById(id);
        if(position!=null)
        {
            positionRepository.delete(position);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    public List<Position> findAllData() throws DataNotFoundException {
        List<Position> positions=positionRepository.findAllData();
        if(positions.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return positions;
        }
    }

    public List<Position> getByPositionStatus(String status) throws DataNotFoundException {

        List<Position> positions=positionRepository.getByStatus(status);
        if(positions.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return positions;
        }
    }

    public List<Position> getByPosition(String status) throws DataNotFoundException {
        List<Position> positions=positionRepository.getByStatusClose(status);
        if(positions.isEmpty())
        {
         throw  new DataNotFoundException(appProperties.getData());
        }else {
            return positions;
        }

    }

    public List<Position> getByStatusName(String status) throws DataNotFoundException {
        List<Position> positions=positionRepository.getStatusByName(status);
        if(positions.isEmpty())
        {
         throw  new DataNotFoundException(appProperties.getData());
        }else {
            return positions;
        }
    }
}
