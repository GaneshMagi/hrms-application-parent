package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A NotificationDetails.
 */
@Entity
@Table(name = "notification_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class NotificationDetails extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notificationdetailsSequenceGenerator")
    @SequenceGenerator(name = "notificationdetailsSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;


    @Column(name = "message")
    private String message;

    @Column(name = "from_mail")
    private String fromMail;

    @Column(name = "to_mail")
    private String toMail;

    @Column(name = "cc")
    private String cc;

    @Column(name = "bcc")
    private String bcc;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;


    @Column(name = "attachment_path")
    private String attachment_path;

    @Column(name = "module")
    private String module;

    @Column(name = "notification_module_id")
    private Long notificationModuleId;



    @ManyToOne
    private NotificationTemplate  templateId;


}
