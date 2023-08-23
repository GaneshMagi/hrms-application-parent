package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.PanelDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the PanelDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PanelDetailsRepository extends JpaRepository<PanelDetails, Long> {

    @Query("select p from PanelDetails p left join p.employee e where e.id = ?1")
    PanelDetails findByid(long id);
    @Query("select p from PanelDetails p where p.id = ?1")
    PanelDetails get(Long id);


    @Query("select p from PanelDetails p left join p.employee e order by e.empId asc")
    List<PanelDetails> getAlData();


    @Query("select p from PanelDetails p left join p.employee e where e.designation=?1")
    List<PanelDetails> getAllByDesignation(String designation);

}
