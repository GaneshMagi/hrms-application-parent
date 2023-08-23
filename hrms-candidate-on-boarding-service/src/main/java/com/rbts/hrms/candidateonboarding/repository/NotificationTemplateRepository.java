package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the NotificationTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {

    @Query("select i from NotificationTemplate i where i.name = ?1")
    NotificationTemplate findByname(String name);

    @Query("select i from NotificationTemplate i where i.id = ?1")
    NotificationTemplate getById(Long id);

    @Query("select i from NotificationTemplate i order by i.name asc")
    List<NotificationTemplate> findAllData();

}
