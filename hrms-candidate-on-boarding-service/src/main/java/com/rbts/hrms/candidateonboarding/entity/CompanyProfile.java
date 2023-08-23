package com.rbts.hrms.candidateonboarding.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="CompanyProfile")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class CompanyProfile  extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyProfileSequenceGenerator")
    @SequenceGenerator(name = "companyProfileSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "profile",nullable = false,unique = true)
    private String profile;

    @Column(name = "logo")
    private String logo;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

}
