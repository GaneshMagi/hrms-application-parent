package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.Position;
import com.rbts.hrms.candidateonboarding.entity.PositionProfile;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PositionRepository extends JpaRepository<Position,Long> {

    @Query("select p from Position p order by p.name asc")
    List<Position> findAllData();

    @Query("select p from Position p where p.id = ?1")
    Position getById(Long id);

    @Query("select p from Position p where p.isActive = true")
    List<Position> getData();

    @Query("select p from Position p left join p.positionStatus ps  where ps.statusName = ?1 order by p.openOn desc")
    List<Position> getByStatus(String status);

    @Query("select p from Position p left join p.positionStatus ps  where ps.statusName = ?1 order by p.closeOn desc")
    List<Position> getByStatusClose(String status);

    @Query("select p from Position p left join p.positionStatus ps  where ps.statusName = ?1")
    List<Position> getStatusByName(String status);

}
