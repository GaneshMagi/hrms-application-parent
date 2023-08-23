package com.rbts.hrms.candidateonboarding.repository;

import com.rbts.hrms.candidateonboarding.entity.BenefitsAndPerks;
import com.rbts.hrms.candidateonboarding.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the City entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select c from City c where c.cityName = ?1")
    City findByName(String city);

    @Query("select c from City c where c.id = ?1")
    City getById(Long id);

    @Query("select c from City c order by c.cityName asc")
    List<City> getAllData();

    @Query("select c from City c where c.isActive = true")
    List<City> getData();


}
