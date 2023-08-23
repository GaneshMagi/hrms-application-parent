package com.rbts.hrms.candidateonboarding.service;

import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.DataalreadyexistsException;
import com.rbts.hrms.candidateonboarding.entity.Events;
import com.rbts.hrms.candidateonboarding.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {
    @Autowired
    EventsRepository eventsRepository;

    @Autowired
    AppProperties appProperties;

    /**
     * save event details
     * @param events
     * @return
     */
    public Events save(Events events) throws DataalreadyexistsException, DataIntegrityException {
        if(events.getName()==null)
        {
            throw  new DataIntegrityException(appProperties.getEvent1());
        }else {
            Events events1=eventsRepository.getByName(events.getName());
            if(events1!=null)
            {
                throw  new DataalreadyexistsException(appProperties.getEvent());
            }else {
                return  eventsRepository.save(events);

            }
        }

    }

    /**
     * update details of event
     * @param events
     * @param id of event
     * @return
     * @throws DataNotFoundException
     */
    public Events update(Events events, Long id) throws DataNotFoundException {
        Events events1=eventsRepository.getById(id);
        if(events1!=null)
        {
            events.setId(id);
           return eventsRepository.save(events);
        }else {
            throw new DataNotFoundException(appProperties.getData());
        }
    }

    /**
     *
     * @param id
     * @return event details
     */
    public Events getById(Long id) throws DataNotFoundException {
        Events events=eventsRepository.getById(id);
        if(events!=null)
        {
            return events;
        }else {
           throw  new DataNotFoundException(appProperties.getData());
        }
    }

    /**
     *
     * @return list of event
     */
    public List<Events> getAll() throws DataNotFoundException {
        List<Events> list=eventsRepository.getData();
        if(list.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return  list;
        }
    }

    public void delete(Long id) throws DataNotFoundException {
        Events events=eventsRepository.getById(id);
        if(events!=null)
        {
            eventsRepository.delete(events);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }
    }

    public List<Events> getAllByName() throws DataNotFoundException {
        List<Events> events=eventsRepository.getAllData();
        if(events.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return events;
        }
    }
}

