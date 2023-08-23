package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.CandidateDetails;
import com.rbts.hrms.candidateonboarding.entity.Position;
import com.rbts.hrms.candidateonboarding.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriorityRepository extends JpaRepository<Priority,Long> {

    @Query("select p from Priority p where p.priorityName =?1")
    Priority findByName(String name);

    @Query("select p from Priority p order by p.priorityName asc")
    List<Priority> findAllData();


    @Query("select p from Priority p where p.id = ?1")
    Priority getById(Long id);

    @Query("select p from Priority p where p.isActive = true")
    List<Priority> getData();
}
