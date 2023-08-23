package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.PositionAssignment;
import com.rbts.hrms.candidateonboarding.entity.PositionProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PositionProfileRepository extends JpaRepository<PositionProfile,Long> {

    @Query("select p from PositionProfile p left join p.candidateDetails pc order by pc.fullName asc")
     List<PositionProfile> findAllData();

    @Query("select p from PositionProfile p where p.id = ?1")
    PositionProfile getById(Long id);

    @Query("select p from PositionProfile p where p.isActive = true")
    List<PositionProfile> getData();

    @Query("select p from PositionProfile p left join p.candidateDetails cp where cp.id = ?1")
    PositionProfile getByCandidateId(Long id);
}
