package com.rbts.hrms.candidateonboarding.service;

import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.Employee;
import com.rbts.hrms.candidateonboarding.entity.PositionType;
import com.rbts.hrms.candidateonboarding.repository.EmployeeRepository;
import com.rbts.hrms.candidateonboarding.repository.PositionTypeRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionTypeService {
    @Autowired
    PositionTypeRepository positionTypeRepository;

    @Autowired
    AppProperties appProperties;

    /**
     * create new postion type
     * @param type
     * @return
     */
    public PositionType save(PositionType type) throws DataIntegrityException, DataalreadyexistsException {
       if(type.getType()!=null)
       {
           PositionType type1=positionTypeRepository.getByName(type.getType());
           if(type1!=null)
           {
               throw  new DataalreadyexistsException(appProperties.getPositionType());
           }else {

               return  positionTypeRepository.save(type);
           }
       }else {
           throw  new DataIntegrityException(appProperties.getPositionType1());
       }
    }

    /**
     * update position type details
     * @param type
     * @param id
     * @return
     * @throws DataNotFoundException
     */
    public PositionType update(PositionType type, Long id) throws DataNotFoundException {

        PositionType positionType=positionTypeRepository.getById(id);
        if(positionType!=null)
        {
            type.setId(id);
           return positionTypeRepository.save(type);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    /**
     *
     * @param id
     * @return position type
     * @throws DataNotFoundException
     */
    public PositionType get(Long id) throws DataNotFoundException {

        PositionType positionType=positionTypeRepository.getById(id);
        if(positionType!=null)
        {
            return positionType;
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    /**
     *
     * @return all position type
     */
    public List<PositionType> getAll() throws DataNotFoundException {
        List<PositionType> list=positionTypeRepository.getAllData();
        if(list.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return list;
        }

    }

    public void delete(Long id) throws DataNotFoundException {
        PositionType positionType=positionTypeRepository.getById(id);
        if (positionType!=null)
        {
            positionTypeRepository.delete(positionType);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    public List<PositionType> findAll() throws DataNotFoundException {
        List<PositionType> positionTypes=positionTypeRepository.getAllData();
        if(positionTypes.isEmpty())
        {
            throw new DataNotFoundException(appProperties.getData());
        }else {
            return positionTypes;
        }
    }
}
