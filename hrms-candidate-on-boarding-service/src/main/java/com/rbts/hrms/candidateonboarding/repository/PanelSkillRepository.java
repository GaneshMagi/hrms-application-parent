package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.PanelSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the PanelSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PanelSkillRepository extends JpaRepository<PanelSkill, Long> {


    @Query("select c from PanelSkill c where c.id = ?1")
    PanelSkill getById(Long id);

    @Query("select c from PanelSkill c left join c.panelId pi where pi.id = ?1")
    PanelSkill getByPanelId(Long id);


    @Query("select c from PanelSkill c left join c.panelId pi left join pi.employee e order by e.emailId asc")
    List<PanelSkill> findAllData();


}
