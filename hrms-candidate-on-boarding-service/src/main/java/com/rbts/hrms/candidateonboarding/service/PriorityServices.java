package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.Priority;
import com.rbts.hrms.candidateonboarding.repository.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PriorityServices {
    @Autowired
    PriorityRepository priorityRepository;

    @Autowired
    AppProperties appProperties;

    /**
     *
     * @param priority to save entity
     * @return
     */

    public Priority save(Priority priority) throws DataIntegrityException, DataalreadyexistsException {
        if(priority.getPriorityName()==null){
            throw new DataIntegrityException(appProperties.getPrioritynamedata());
        } else{
            Priority p= priorityRepository.findByName(priority.getPriorityName());
            if(p==null){
                priorityRepository.save(priority);
            }else{
                throw  new DataalreadyexistsException(appProperties.getPriorityname());
            }
        }
        return priority;

    }

    /**
     * update priority
     * @param id for update priority
     * @param priority
     * @return
     */

    public Priority update(Long id, Priority priority) throws DataNotFoundException {

        Priority p=priorityRepository.getById(id);
        if(p==null)
        {
            throw  new DataNotFoundException(appProperties.getPriorityname());
        }else {
            priority.setId(id);
            return priorityRepository.save(priority);
        }
    }

    /**
     *
     * @return all priority details
     */

    public List<Priority> getAll() throws  DataNotFoundException{
        List<Priority> list= priorityRepository.getData();
        if(list.isEmpty()){
            throw  new DataNotFoundException(appProperties.getData());
        }else{
            return list;
        }
    }

    /**
     *
     * @param id of priority for retrieve
     * @return
     */
    public Priority getById(Long id)  throws  DataNotFoundException{
        Priority priority= priorityRepository.getById(id);
        if (priority==null){
            throw new DataNotFoundException(appProperties.getData());
        }else {
            return priority;
        }
    }

    public void delete(Long id) throws DataNotFoundException {
        Priority priority=priorityRepository.getById(id);
        if(priority!=null)
        {
            priorityRepository.delete(priority);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    public List<Priority> findAllData() throws DataNotFoundException {
        List<Priority> priorities=priorityRepository.findAllData();
        if(priorities.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return priorities;
        }

    }
}
