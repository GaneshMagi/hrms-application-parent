package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.JobDescription;
import com.rbts.hrms.candidateonboarding.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobTypeRepository extends JpaRepository<JobType,Long> {



    @Query("select j from JobType j order by j.jobType asc")
    List<JobType> findAllData();

    @Query("select j from JobType j where j.jobType=?1")
    JobType findByName(String name);

    @Query("select j from JobType j where j.id=?1")
    JobType getById(Long id);

    @Query("select j from JobType j where j.isActive = true")
    List<JobType> getData();
}
