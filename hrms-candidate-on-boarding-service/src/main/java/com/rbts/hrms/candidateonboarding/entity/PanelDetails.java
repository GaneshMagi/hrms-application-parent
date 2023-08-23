package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * A PanelDetails.
 */
@Entity
@Table(name = "panel_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class PanelDetails extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paneldetailsSequenceGenerator")
    @SequenceGenerator(name = "paneldetailsSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;


    @ManyToOne
    @JoinColumn (name="status_id_id")
    private Status statusId;

    @ManyToOne
    @JoinColumn (name="employee_id",nullable = false)
    @JsonIgnoreProperties(value = {  "panelDetailsIds" }, allowSetters = true)
    private Employee employee;
//    @ManyToOne
//    @JoinColumn(name = "position_id") // Set the column name to reference the Position
//    private Position position;


}
