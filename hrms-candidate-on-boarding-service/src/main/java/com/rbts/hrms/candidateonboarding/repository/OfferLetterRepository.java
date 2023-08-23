package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.OfferLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OfferLetter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferLetterRepository extends JpaRepository<OfferLetter, Long> {

    @Query("select o from OfferLetter o where o.emailId = ?1")
    OfferLetter findByLevel(String email);

    @Query("select o from OfferLetter o where o.id = ?1")
    OfferLetter getById(Long id);
}
