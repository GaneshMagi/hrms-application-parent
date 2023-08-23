package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.Qualification;
import com.rbts.hrms.candidateonboarding.entity.Shifts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShiftsRepository extends JpaRepository<Shifts,Long> {

    @Query("select s from Shifts s order by s.shiftName asc")
    List<Shifts> findAllData();

    @Query("select s from Shifts s where s.shiftName =?1")
    Shifts getByShiftname(String name);


    @Query("select s from Shifts s where s.id =?1")
    Shifts getById(Long id);

    @Query("select s from Shifts s where s.isActive = true")
    List<Shifts> getData();
}
