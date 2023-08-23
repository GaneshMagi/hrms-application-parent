package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A NotificationTemplate.
 */
@Entity
@Table(name = "notification_template")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class NotificationTemplate extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationtemplateSequenceGenerator")
    @SequenceGenerator(name = "notificationtemplateSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "name", nullable = false,unique = true)
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "velocity_file")
    private String velocityFile;


    @ManyToOne
    private MessageType messageId;

    @ManyToOne
    private Status statusId;
    @OneToOne
    private Events events;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;




}
