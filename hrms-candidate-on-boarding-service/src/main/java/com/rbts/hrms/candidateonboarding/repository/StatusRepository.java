package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.Skill;
import com.rbts.hrms.candidateonboarding.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Status entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {


    @Query("select s from Status s where s.statusName = ?1")
    Status findByName(String name);

    @Query("select s from Status s where s.id = ?1")
    Status getById(Long id);

    @Query("select s from Status s where s.isActive = true")
    List<Status> getData();






    @Query("select s from Status s order by s.id asc")
    List<Status> findAllData();



}
