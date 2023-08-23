package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.BenefitsAndPerks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BenefitsAndPerksRepository extends JpaRepository<BenefitsAndPerks,Long> {

    @Query("select b from BenefitsAndPerks b where b.id =?1")
    BenefitsAndPerks getById(Long id);

    @Query("select b from BenefitsAndPerks b order by b.benefitsAndPerks asc")
    List<BenefitsAndPerks> findAllData();


    @Query("select b from BenefitsAndPerks b where b.isActive = true")
    List<BenefitsAndPerks> getData();




}
