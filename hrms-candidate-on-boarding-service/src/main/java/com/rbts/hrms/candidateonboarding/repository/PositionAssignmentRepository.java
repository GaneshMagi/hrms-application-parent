package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.PositionAssignment;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PositionAssignmentRepository extends JpaRepository<PositionAssignment,Long> {

    @Query("select p from PositionAssignment p left join p.positionId pp order by pp.name asc")
    List<PositionAssignment> findAllData();

    @Query("select p from PositionAssignment p where p.id = ?1")
    PositionAssignment getById(Long id);

    @Query("select p from PositionAssignment p where p.isActive = true")
    List<PositionAssignment> getData();

//       @Query("select p, pp.employee, pp.positionStatus " + "from PositionAssignment p " + "left join p.positionId pp " + "left join pp.employee e " +
//           "left join pp.positionStatus ps " + "where p.localDate >= :sixMonthsAgo " +
//           "order by pp.localDate asc")
//       List<PositionAssignment> findPositionAssignmentsWithDetails(@Param("sixMonthsAgo") LocalDate sixMonthsAgo);

    @Query("select p from PositionAssignment p left join p.positionId pp left join pp.employee pe where p.userId = ?1  and pe.empId=?2 ")
    List<PositionAssignment> findData(Long userId, String employeeId);


//        @Query("select count(p) from PositionAssignment p left join p.positionId pp left join p.employee e where p.userId = :userId AND
//        e.empId = :empId and p.localDate >= :sixMonthsAgo")
//        Long countByUserIdAndEmpIdAndPositionStatusLast6Months(@Param("userId") int userId, @Param("empId") int empId ,
//            @Param("sixMonthsAgo") Date sixMonthsAgo
//        );


}


