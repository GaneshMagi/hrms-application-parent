package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the MessageType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageTypeRepository extends JpaRepository<MessageType, Long> {
    @Query("select i from MessageType i where i.type = ?1")
    MessageType findByType(String level);

    @Query("select i from MessageType i where i.id = ?1")
    MessageType getById(Long id);


    @Query("select m from MessageType m order by m.type asc")
    List<MessageType> getAllData();

}
