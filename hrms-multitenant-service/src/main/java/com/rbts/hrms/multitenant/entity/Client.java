package com.rbts.hrms.multitenant.entity;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Client  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator",allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "drive_name")
    private String driveName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "tenant_name")
    private String tenantName;

    @Column(name = "database_url")
    private String databaseUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "organization_name",nullable = false)
    private String organizationName;

    @Column(name = "domain_name")
    private String domainName;

    @Column(name = "contact_no",nullable = false, unique = true)
    private String contactNo;

    @Column(name = "referral_code")
    private String referralCode;

    @Column(name = "subscription_type")
    private String subscriptionType;

//   @Column(name = "created_by")
//   private String createdBy;
//
//   @Column(name = "created_on")
//   private Instant createdOn = Instant.now();
//
//    @Column(name = "modified_by")
//   private String modifiedBy;
//
//   @Column(name = "modified_on")
//   private Instant modifiedOn=Instant.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "range_id")
    private Range rangeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here


}
