package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.CandidateFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the CandidateFeedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateFeedbackRepository extends JpaRepository<CandidateFeedback, Long> {

    @Query("select c from CandidateFeedback c left join fetch c.candidateId cc where cc.id = ?1 order  by c.id asc")
    List<CandidateFeedback> findByCandidateId(long candidate_id);


    @Query("select c from CandidateFeedback c where c.id = ?1")
    CandidateFeedback getById(Long id);

//    @Query("select c from CandidateFeedback c left join fetch c.interviewLevelId cc where cc.preference = ?1 ")
//    CandidateFeedback findbyInterviewlevel(Integer i);

    @Query("select c from CandidateFeedback c left join fetch c.candidateId cc left join c.interviewLevelId i where cc.id = ?1 order by i.preference   ")
    List<CandidateFeedback> findByInterviewLevel(long candidate_id);

}
