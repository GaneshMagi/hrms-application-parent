package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.InterviewLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the InterviewLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewLevelRepository extends JpaRepository<InterviewLevel, Long> {

    @Query("select i from InterviewLevel i where i.levelName = ?1")
    InterviewLevel findByLevel(String level);

    @Query("select i from InterviewLevel i where i.id = ?1")
    InterviewLevel getById(Long id);

    @Query("select e from InterviewLevel e order by e.preference asc")
    List<InterviewLevel> findAllData();




}
