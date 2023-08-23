package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class Attachment  extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachmentSequenceGenerator")
    @SequenceGenerator(name = "attachmentSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;

    @Column(name = "attachment_url")
    private String attachmentUrl;


    @ManyToOne
   @JoinColumn(name = "notificationdetailsid_id" ,nullable = false)
    private NotificationDetails notificationDetailsId;


}
