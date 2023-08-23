package com.rbts.hrms.candidateonboarding.service;


import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.Skill;
import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.repository.SkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Skill}.
 */
@Service
@Transactional
public class SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillService.class);

    private final SkillRepository skillRepository;
    @Autowired
    AppProperties appProperties;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    /**
     * Save a skill.
     *
     * @param skill the entity to save.
     * @return the persisted entity.
     */
    public Skill save(Skill skill) throws ResourceNotFoundException {
        log.debug("Request to save Skill : {}", skill);
      Skill s=skillRepository.findByName(skill.getSkillName());
      if(s!=null)
      {
              throw  new ResourceNotFoundException(appProperties.getSkill());
      }else {
        skill=skillRepository.save(skill);
      }
        return skill;
    }

    /**
     * Update a skill.
     *
     * @param skill the entity to save.
     * @return the persisted entity.
     */
    public Skill update(Skill skill) {
        log.debug("Request to update Skill : {}", skill);
        return skillRepository.save(skill);
    }



    /**
     * Get all the skills.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Skill> findAll() throws DataNotFoundException {
        log.debug("Request to get all Skills");
        List<Skill> skill=skillRepository.getData();
        if(skill.isEmpty())
        {
            throw  new DataNotFoundException(appProperties.getData());

        }else {
            return skillRepository.getAllData();
        }


    }

    /**
     * Get one skill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Skill findOne(Long id) throws DataNotFoundException {
        Skill skill=skillRepository.getById(id);
        if(skill==null)
        {
            throw  new DataNotFoundException(appProperties.getData());

        }else {
            return skillRepository.getById(id);
        }


    }

    /**
     * Delete the skill by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) throws DataNotFoundException {
        Skill skill=skillRepository.getById(id);
        if(skill!=null)
        {
            skillRepository.delete(skill);
        }else {
            throw  new DataNotFoundException(appProperties.getData());
        }

    }

    public List<Skill> getAll() throws DataNotFoundException {
        List<Skill> skills=skillRepository.getData();

        if(skills.isEmpty())
        {
            throw new DataNotFoundException(appProperties.getData());
        }else
        {
            return skills;
        }
    }
}
