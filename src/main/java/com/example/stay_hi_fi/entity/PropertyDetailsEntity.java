package com.example.stay_hi_fi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl_property_details",schema = "stay_hi_fi")
@Data
public class PropertyDetailsEntity extends AuditEntity {

    private static final Long serialVersionID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "move_in_date")
    private String moveInDate;

    @Column(name = "maintenance_charges")
    private String maintenanceCharges;

    @Column(name = "property_name")
    private String propertyName;

    @Column(name = "feasible_visit_date")
    private String feasibleVisitDate;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "furnishing_type")
    private String furnishingType;

    @Column(name = "rent")
    private String rent;

    @Column(name = "deposit")
    private String deposit;

    @Column(name = "property_description")
    private String propertyDescription;

    @Column(name = "pet_friendly")
    private Boolean petFriendly = false;

    @Column(name = "tenant_preference")
    private String tenantPreference;

    @Column(name = "media_link")
    private String mediaLinkUrl;

    @Column(name = "location")
    private String location;

     @ManyToOne()
     @JoinColumn(name = "location_id", nullable = false)
     private LocationEntity locationEntity;
}
