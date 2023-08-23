package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.BenefitsAndPerks;
import com.rbts.hrms.candidateonboarding.entity.JobDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobDescriptionRepository extends JpaRepository<JobDescription,Long> {

    @Query("select j from JobDescription j order by j.salaryRange asc")
    List<JobDescription> findAllData();

    @Query("select j from JobDescription j where j.id=?1")
    JobDescription getById(Long id);

    @Query("select j from JobDescription j where j.isActive = true")
    List<JobDescription> getData();

    @Query("select j from JobDescription j order by j.name asc")
    List<JobDescription> findByName();

}
