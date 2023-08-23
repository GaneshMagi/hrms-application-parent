package com.rbts.hrms.candidateonboarding.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.security.PrivateKey;

@Entity
@Table (name="vendor_details")
@Data
public class VendorDetails extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "vendordetailsSequenceGenerator")
    @SequenceGenerator(name = "vendordetailsSequenceGenerator", allocationSize = 1, initialValue = 1)
    private long id;

    @Column(name="vendor_name",nullable = false,unique = true)
    private String vendorName;

    @Column(name="contact_person",nullable = false)
    private  String contactPerson;

    @Column(name="cin",nullable = false,unique = true)
    private String cin;
    @Column(name="gstn",nullable = false,unique = true)
    private String gstn;

    @Column(name="address")
    private String address;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "contact_no",nullable = false, unique = true)
    private String contactNo;
    @Column(name = "alt_contact_no")
    private String altContactNo;

}
