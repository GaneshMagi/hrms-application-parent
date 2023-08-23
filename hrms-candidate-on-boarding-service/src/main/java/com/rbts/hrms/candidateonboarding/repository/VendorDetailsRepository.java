package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.Skill;
import com.rbts.hrms.candidateonboarding.entity.VendorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VendorDetailsRepository extends JpaRepository<VendorDetails,Long> {

    @Query("select v from VendorDetails v where v.id = ?1")
    VendorDetails getById(Long id);

    @Query("select v from VendorDetails v order by v.vendorName asc")
    List<VendorDetails> findAllData();

    @Query("select v from VendorDetails v where v.vendorName =?1")
    VendorDetails findByName(String name);

    @Query("select v from VendorDetails v where v.cin =?1")
    VendorDetails findByCin(String name);

    @Query("select v from VendorDetails v where v.gstn =?1")
    VendorDetails findByGstn(String name);


    @Query("select v from VendorDetails v where v.isActive = true")
    List<VendorDetails> getData();
}
