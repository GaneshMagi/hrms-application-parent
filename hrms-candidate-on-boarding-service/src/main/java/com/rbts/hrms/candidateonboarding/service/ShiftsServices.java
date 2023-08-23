package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.Shifts;
import com.rbts.hrms.candidateonboarding.repository.ShiftsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@Transactional
public class ShiftsServices {
    @Autowired
    ShiftsRepository shiftsRepository;
    @Autowired
    AppProperties appProperties;

    /**
     * save shifts details
     * @param shifts
     * @return
     * @throws DataIntegrityException
     * @throws DataalreadyexistsException
     */

    public Shifts save(Shifts shifts) throws DataIntegrityException, DataalreadyexistsException {

       if(shifts.getShiftName()==null)
       {
         throw new DataIntegrityException(appProperties.getShiftName());
       }else {
           Shifts shifts1=shiftsRepository.getByShiftname(shifts.getShiftName());
           if(shifts1!=null)
           {
               throw  new DataalreadyexistsException(appProperties.getShiftNamedata());
           }else {
               shiftsRepository.save(shifts) ;
           }
       }
        return shifts;

    }

    /**
     * update shifts details
     * @param id for update shift details
     * @param shifts
     * @return
     */
    public Shifts update(Long id, Shifts shifts) throws DataNotFoundException {
        System.out.println("&&&&&&&&&&&&&&&&"+id);
        Shifts s =shiftsRepository.getById(id);
        if(s==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
        shifts.setId(id);
        return shiftsRepository.save(shifts);
    }}

    /**
     * get all shifts details
     * @return
     * @throws DataNotFoundException
     */
    
    public List<Shifts> getAll() throws DataNotFoundException {
        List<Shifts> shifts=shiftsRepository.getData();
        if(shifts.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return shifts;
        }

    }

    /**
     * get details for single shifts
     * @param id
     * @return
     * @throws DataNotFoundException
     */

    public Shifts getById(Long id) throws DataNotFoundException {
        Shifts shifts=shiftsRepository.getById(id);
        if(shifts==null)
        {
            throw new DataNotFoundException(appProperties.getData());

        }else {
            return shifts;
        }
    }

    public void delete(Long id) throws DataNotFoundException {
        Shifts shifts=shiftsRepository.getById(id);
        if(shifts!=null)
        {
            shiftsRepository.delete(shifts);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    public List<Shifts> findAll() throws DataNotFoundException {
        List<Shifts> shifts=shiftsRepository.findAllData();
        if(shifts.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else{
           return shifts;
        }
    }
}
