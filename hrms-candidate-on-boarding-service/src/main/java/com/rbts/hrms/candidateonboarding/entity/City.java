package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A City.
 */
@Entity
@Table(name = "city")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class City  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator_city", allocationSize = 1, initialValue = 2000)
    private Long id;

    @Column(name = "city_name" ,unique = true,nullable = false)
    private String cityName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;



}
