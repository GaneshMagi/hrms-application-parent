package com.rbts.hrms.candidateonboarding.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="benefits_and_perks")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class BenefitsAndPerks  extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "benefitsAndPerksSequenceGenerator")
    @SequenceGenerator(name = "benefitsAndPerksSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "benefits_and_perks",nullable = false,unique = true)
    private String benefitsAndPerks;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

}
