package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.InlineImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InlineImages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InlineImagesRepository extends JpaRepository<InlineImages, Long> {


    @Query("select a from InlineImages a where a.id = ?1")
    InlineImages getById(Long id);
}
