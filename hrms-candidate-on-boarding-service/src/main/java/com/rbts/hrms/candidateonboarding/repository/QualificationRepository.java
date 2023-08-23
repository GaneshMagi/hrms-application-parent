package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.Priority;
import com.rbts.hrms.candidateonboarding.entity.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QualificationRepository extends JpaRepository<Qualification,Long> {

    @Query("select q from Qualification q order by q.name asc")
    List<Qualification> findAllData();

    @Query("select q from Qualification q where q.name =?1")
    Qualification findByName(String name);

    @Query("select q from Qualification q where q.id =?1")
    Qualification getById(Long id);

    @Query("select q from Qualification q where q.isActive = true")
    List<Qualification> getData();
}
