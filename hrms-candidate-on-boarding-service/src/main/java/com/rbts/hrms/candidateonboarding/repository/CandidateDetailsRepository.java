package com.rbts.hrms.candidateonboarding.repository;


import com.rbts.hrms.candidateonboarding.entity.CandidateDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the CandidateDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateDetailsRepository extends JpaRepository<CandidateDetails, Long> {


    @Query("select c from CandidateDetails c where c.email = ?1")
    CandidateDetails  findByEmail(String email);

    @Query("select c from CandidateDetails c where c.contactNo = ?1")
    CandidateDetails  findByContactNo(String contactNo);


    @Query("select c from CandidateDetails c where c.id = ?1")
    CandidateDetails  getById(Long id);

    @Query("select c from CandidateDetails c order by c.fullName asc")
    List<CandidateDetails> findAllData();



    @Query("select c from CandidateDetails c order by c.lastModifiedDate desc")
    List<CandidateDetails> findData();
}
