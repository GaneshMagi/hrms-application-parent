package com.rbts.hrms.candidateonboarding.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A InlineImages.
 */
@Entity
@Table(name = "inline_images")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
public class InlineImages extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inlineimagesSequenceGenerator")
    @SequenceGenerator(name = "inlineimagesSequenceGenerator", allocationSize = 1, initialValue = 1)
    private Long id;
    @Column(name = "image_url")
    private String imageUrl;


    @ManyToOne
    @JoinColumn(name = "notificationdetailsid_id" ,nullable = false)
    private NotificationDetails notificationDetailsId;


}
