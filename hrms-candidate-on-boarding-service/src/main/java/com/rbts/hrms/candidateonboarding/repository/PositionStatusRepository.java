package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.CandidateDetails;
import com.rbts.hrms.candidateonboarding.entity.PositionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PositionStatusRepository extends JpaRepository<PositionStatus,Long> {

    @Query("select s from PositionStatus s where s.id = ?1")
    PositionStatus getById(Long id);

    @Query("select s from PositionStatus s where s.statusName = ?1")
    PositionStatus getByName(String name);


    @Query("select s from PositionStatus s order by  s.statusName asc ")
    List<PositionStatus> findAllData();
}
