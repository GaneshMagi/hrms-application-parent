package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.BenefitsAndPerks;
import com.rbts.hrms.candidateonboarding.entity.CompanyProfile;
import com.rbts.hrms.candidateonboarding.entity.Priority;
import com.rbts.hrms.candidateonboarding.entity.Shifts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyProfileRepository extends JpaRepository<CompanyProfile,Long> {

    @Query("select c from CompanyProfile c where c.id = ?1")
    CompanyProfile getById(Long id);

    @Query("select c from CompanyProfile c where c.id = ?1")
    CompanyProfile getByProfileId(Long id);

    @Query("select c from CompanyProfile c order by c.profile asc")
    List<CompanyProfile> findAllData();

    @Query("select c from CompanyProfile c where c.isActive = true")
    List<CompanyProfile> getData();

   @Query("select c from CompanyProfile c where c.profile =?1")
   CompanyProfile getByProfileName(String name);
}

