package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.JobType;
import com.rbts.hrms.candidateonboarding.entity.Languages;
import org.aspectj.lang.JoinPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LanguagesRepository extends JpaRepository<Languages,Long> {

    @Query("select l from Languages l order by l.language asc")
    List<Languages> findAllData();

    @Query("select l from Languages l where l.language=?1")
    Languages findByName(String name);

    @Query("select l from Languages l where l.id=?1")
    Languages getById(Long id);

    @Query("select l from Languages l where l.isActive = true")
    List<Languages> getData();

}
