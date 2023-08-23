package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.NotificationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the NotificationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationDetailsRepository extends JpaRepository<NotificationDetails, Long> {

    @Query("select i from NotificationDetails i order by i.id asc")
    List<NotificationDetails> findAllData();

    @Query("select i from NotificationDetails i where i.id=?1")
    NotificationDetails getById(Long id);
    @Query("select i from NotificationDetails i where i.notificationModuleId=?1")
    List<NotificationDetails> findByModuleId(long id);

}
