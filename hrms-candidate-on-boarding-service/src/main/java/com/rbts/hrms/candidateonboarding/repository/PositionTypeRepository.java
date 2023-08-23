package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.PositionStatus;
import com.rbts.hrms.candidateonboarding.entity.PositionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PositionTypeRepository extends JpaRepository<PositionType,Long> {

    @Query("select t from PositionType t where t.id = ?1")
    PositionType getById(Long id);

    @Query("select t from PositionType t where t.type = ?1")
    PositionType getByName(String name);


    @Query("select t from PositionType t order by  t.type asc ")
    List<PositionType> getAllData();


}
