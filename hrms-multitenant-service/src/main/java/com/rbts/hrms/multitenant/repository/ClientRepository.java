package com.rbts.hrms.multitenant.repository;



import com.rbts.hrms.multitenant.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select c from Client c where c.email= ?1")
    Client findByEmail(String data);

    @Query("select c from Client c where c.domainName= ?1")
    Client findByDomainName(String data);

    @Query("select c from Client c where c.contactNo= ?1")
    Client findBycontactNo(String data);
    @Query("select c from Client c order by c.organizationName asc ")
    List<Client> findAllData();

}
