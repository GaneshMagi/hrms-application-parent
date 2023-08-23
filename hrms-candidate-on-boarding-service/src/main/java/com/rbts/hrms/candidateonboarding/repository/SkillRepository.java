package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.Shifts;
import com.rbts.hrms.candidateonboarding.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Skill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {



    @Query("select k from Skill k where k.skillName = ?1")
    Skill findByName(String skill);

    @Query("select k from Skill k where k.id = ?1")
    Skill getById(Long skill);

    @Query("select k from Skill k where k.skillName = ?1")
    List<Skill> findByName1(String skill);

    @Query("select k from Skill k  order by k.skillName asc")
    List<Skill> getAllData();

    @Query("select s from Skill s where s.isActive = true")
    List<Skill> getData();

}
