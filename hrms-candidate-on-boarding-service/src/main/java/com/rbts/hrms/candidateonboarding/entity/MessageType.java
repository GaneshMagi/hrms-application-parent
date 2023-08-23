package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A MessageType.
 */
@Entity
@Table(name = "message_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class MessageType extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messagetypeSequenceGenerator")
    @SequenceGenerator(name = "messagetypeSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "type" ,nullable = false,unique = true)
    private String type;


}
