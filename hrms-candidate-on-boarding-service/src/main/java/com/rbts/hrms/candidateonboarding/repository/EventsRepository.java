package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.CandidateDetails;
import com.rbts.hrms.candidateonboarding.entity.Events;
import com.rbts.hrms.candidateonboarding.entity.Position;
import com.rbts.hrms.candidateonboarding.entity.PositionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventsRepository extends JpaRepository<Events,Long> {

    @Query("select e from Events e where e.id = ?1")
    Events getById(Long id);

    @Query("select e from Events e where e.name = ?1")
    Events getByName(String name);

    @Query("select e from Events e where e.isActive = true")
    List<Events> getData();

    @Query("select e from Events e order by e.name asc")
    List<Events> getAllData();

}
