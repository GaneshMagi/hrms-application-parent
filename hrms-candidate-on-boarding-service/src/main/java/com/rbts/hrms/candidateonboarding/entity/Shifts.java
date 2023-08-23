package com.rbts.hrms.candidateonboarding.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "shifts")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Shifts  extends Auditable<String> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "shiftsSequenceGenerator")
    @SequenceGenerator(name = "shiftsSequenceGenerator", allocationSize = 1, initialValue = 1)
    private long id;

    @Column(name="shift_name",nullable = false ,unique = true)
    private String shiftName;

    @Column(name="shift_time")
    private String shiftTime;
    @Column(name="time_zone")
    private String timeZone ;


    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

}
