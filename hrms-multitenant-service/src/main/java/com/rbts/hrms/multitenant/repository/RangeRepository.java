package com.rbts.hrms.multitenant.repository;



import com.rbts.hrms.multitenant.entity.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Range entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RangeRepository extends JpaRepository<Range, Long> {


    @Query("select r from Range r where r.empRange= ?1")
    Range findByName(String data);
    @Query("select r from Range r where r.id = ?1")
    Range findOne(Long data);
    @Query("select r from Range r order by r.preference asc")
    List<Range> findAllData();




}
