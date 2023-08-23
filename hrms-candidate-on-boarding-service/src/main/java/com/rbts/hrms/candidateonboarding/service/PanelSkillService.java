package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataIntegrityException;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.dto.PanelSkillRequest;
import com.rbts.hrms.candidateonboarding.dto.PanelSkillResponse;
import com.rbts.hrms.candidateonboarding.entity.PanelSkill;
import com.rbts.hrms.candidateonboarding.entity.Skill;
import com.rbts.hrms.candidateonboarding.mapper.PanelSkillMapper;
import com.rbts.hrms.candidateonboarding.repository.EmployeeRepository;
import com.rbts.hrms.candidateonboarding.repository.PanelDetailsRepository;
import com.rbts.hrms.candidateonboarding.repository.PanelSkillRepository;
import com.rbts.hrms.candidateonboarding.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service Implementation for managing {@link PanelSkill}.
 */
@Service
@Transactional
public class PanelSkillService {

    private final Logger log = LoggerFactory.getLogger(PanelSkillService.class);

    private final PanelSkillRepository panelSkillRepository;
    @Autowired
    PanelDetailsRepository panelDetailsRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PanelSkillMapper panelSkillMapper;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    AppProperties appProperties;

    public PanelSkillService(PanelSkillRepository panelSkillRepository) {
        this.panelSkillRepository = panelSkillRepository;
    }

    /**
     * Save a panelSkill.
     *
     * @param panelSkill the entity to save.
     * @return the persisted entity.
     */
    public List<PanelSkillResponse> save(PanelSkillRequest panelSkill) throws DataNotFoundException, DataIntegrityException, ResourceNotFoundException {

        List<PanelSkillResponse> list=new ArrayList<>();
        for(Skill s:panelSkill.getSkill())
        {
            log.debug("Request to save PanelSkill : {}", panelSkill);

            PanelSkill p=panelSkillMapper.convertRequest(panelSkill,s);
            panelSkillRepository.save(p);
            PanelSkillResponse pk = panelSkillMapper.convertResponse(p);
            list.add(pk);

        }

        return list;
    }


    /**
     * Update a panelSkill.
     *
     * @param panelSkill the entity to save.
     * @param id
     * @return the persisted entity.
     */
    public PanelSkillResponse update(PanelSkillRequest panelSkill, Long id) throws DataNotFoundException, DataIntegrityException, ResourceNotFoundException {
        log.debug("Request to update PanelSkill : {}", panelSkill);
        PanelSkillResponse pk = new PanelSkillResponse();
        for(Skill s:panelSkill.getSkill()) {
            PanelSkill p = panelSkillMapper.convertRequest(panelSkill,s);
            p.setId(id);
            panelSkillRepository.save(p);
            PanelSkill panel = panelSkillRepository.getById(p.getId());
             pk = panelSkillMapper.convertResponse(panel);
        }
        return pk;


    }

    /**
     * Partially update a panelSkill.
     *
     * @param panelSkill the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PanelSkill> partialUpdate(PanelSkill panelSkill) {
        log.debug("Request to partially update PanelSkill : {}", panelSkill);

        return panelSkillRepository
                .findById(panelSkill.getId())
                .map(panelSkillRepository::save);
    }

    /**
     * Get all the panelSkills.
     *
     * @return the list of entities.
     */
      @Transactional(readOnly = true)
     public List<PanelSkillResponse> findAll() throws DataNotFoundException {
        log.debug("Request to get all PanelSkills");
        List<PanelSkill> p = panelSkillRepository.findAllData();
        List<PanelSkillResponse> list=new ArrayList<>();
        if(p.isEmpty())
        {
            throw new DataNotFoundException(appProperties.getData());

        }else {
            for (PanelSkill skill : p) {
                PanelSkillResponse skill1 = panelSkillMapper.convertResponse(skill);
                list.add(skill1);
            }
        }
        return list;
    }

    /**
     * Get one panelSkill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
       @Transactional(readOnly = true)
      public PanelSkillResponse findOne(Long id) throws DataNotFoundException {
        log.debug("Request to get PanelSkill : {}", id);
        PanelSkill p=panelSkillRepository.getById(id);
        if(p==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            PanelSkillResponse response=panelSkillMapper.convertResponse(p);
            return response;
        }


    }

    @Transactional(readOnly = true)
    public PanelSkill findById(Long id) throws DataNotFoundException {
        log.debug("Request to get PanelSkill : {}", id);
        PanelSkill p=panelSkillRepository.getById(id);
        if(p==null)
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {

            //PanelSkillResponse response=panelSkillMapper.convertResponse(p);
            return p;
        }


    }
    /**
     * Delete the panelSkill by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PanelSkill : {}", id);
        panelSkillRepository.deleteById(id);
    }

    public List<PanelSkillResponse> findByName(String skill) throws ResourceNotFoundException, DataNotFoundException {
        String name=skill.toUpperCase();
        List<PanelSkillResponse> panel = new ArrayList<>();
        Skill s=skillRepository.findByName(name);
        if(s!=null) {
            Skill pt = new Skill();
            if (s.getSkillName().toUpperCase().contains(skill.toUpperCase())) {
                pt.setId(s.getId());
                pt.setSkillName(s.getSkillName());
            } else {
                System.out.println("******************");
            }
            Long data = pt.getId();
            List<PanelSkill> p = panelSkillRepository.findAllData();

            for (PanelSkill l : p) {
                   Skill sk = l.getSkill();
                    if (sk.getSkillName().equals(s.getSkillName().toUpperCase())) {
                        PanelSkill pk = panelSkillRepository.getById(l.getId());
                        PanelSkillResponse r = new PanelSkillResponse();
                        r.setId(l.getId());
                        r.setInterviewLevel(l.getInterviewLevel());
                        r.setPanelId(l.getPanelId());
                        r.setStatus(l.getStatus());
                        r.setCreatedBy(pk.getCreatedBy());
                        r.setLastModifiedBy(pk.getLastModifiedBy());
                        r.setCreationDate(pk.getCreationDate());
                        r.setLastModifiedDate(pk.getLastModifiedDate());
                        Set<Skill> skillSet = new HashSet<>();
                        Skill skill2 = skillRepository.findByName(pk.getSkill().getSkillName());
                        r.setSkill(skill2);
                        panel.add(r);

                    }

            }
        }else {

                throw  new ResourceNotFoundException(appProperties.getSkill1());

        }
        if(panel.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());
        }else {
            return panel;
        }


    }





}
