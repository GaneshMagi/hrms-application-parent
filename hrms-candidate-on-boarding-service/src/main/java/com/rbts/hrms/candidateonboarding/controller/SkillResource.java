package com.rbts.hrms.candidateonboarding.controller;


import com.rbts.hrms.candidateonboarding.customexception.AppProperties;
import com.rbts.hrms.candidateonboarding.customexception.DataNotFoundException;
import com.rbts.hrms.candidateonboarding.customexception.ResourceNotFoundException;
import com.rbts.hrms.candidateonboarding.entity.Skill;
import com.rbts.hrms.candidateonboarding.exception.BadRequestAlertException;
import com.rbts.hrms.candidateonboarding.repository.SkillRepository;
import com.rbts.hrms.candidateonboarding.service.SkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.redberyl.domain.Skill}.
 */
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class SkillResource {

    private final Logger log = LoggerFactory.getLogger(SkillResource.class);

    private static final String ENTITY_NAME = "candidateonboardingSkill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    AppProperties appProperties;
    private final SkillService skillService;

    private final SkillRepository skillRepository;

    public SkillResource(SkillService skillService, SkillRepository skillRepository) {
        this.skillService = skillService;
        this.skillRepository = skillRepository;
    }

    /**
     * {@code POST  /skills} : Create a new skill.
     *
     * @param skill the skill to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skill, or with status {@code 400 (Bad Request)} if the skill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skills")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skill);
        if (skill.getId() != null) {
            try {
                throw new BadRequestAlertException("A new skill cannot already have an ID", ENTITY_NAME, "idexists");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        Skill result = null;
        try {
            result = skillService.save(skill);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity
            .created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skills/:id} : Updates an existing skill.
     *
     * @param id the id of the skill to save.
     * @param skill the skill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skill,
     * or with status {@code 400 (Bad Request)} if the skill is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skills/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable(value = "id", required = false) final Long id, @RequestBody Skill skill)
        throws URISyntaxException {
        log.debug("REST request to update Skill : {}, {}", id, skill);
        if (skill.getId() == null) {
            try {
                throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }
        if (!Objects.equals(id, skill.getId())) {
            try {
                throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }

        if (!skillRepository.existsById(id)) {
            try {
                throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
            } catch (BadRequestAlertException e) {
                throw new RuntimeException(e);
            }
        }

        Skill result = skillService.update(skill);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skill.getId().toString()))
            .body(result);
    }



    /**
     * {@code GET  /skills} : get all the skills.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skills in body.
     */
    @GetMapping("/skills")
    public List<Skill> getAllSkills() {
        log.debug("REST request to get all Skills");
        try {
            return skillService.findAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return list of skill sort by name
     */
    @GetMapping("/skills/getAll")
    public List<Skill> findAll() {
        log.debug("REST request to get all Skills");
        try {
            return skillService.getAll();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@code GET  /skills/:id} : get the "id" skill.
     *
     * @param id the id of the skill to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skill, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skills/{id}")
    public Skill getSkill(@PathVariable Long id) {
        log.debug("REST request to get Skill : {}", id);
        Skill skill = null;
        try {
            skill = skillService.findOne(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return skill;
    }

    /**
     * {@code DELETE  /skills/:id} : delete the "id" skill.
     *
     * @param id the id of the skill to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skills/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        try {
            skillService.delete(id);
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appProperties.getDelete());
    }



}
