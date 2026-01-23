package com.example.stay_hi_fi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_location",schema = "stay_hi_fi")
public class LocationEntity extends AuditEntity {

    private static final Long serialVersionID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "lat")
    private double latitude;

    @Column(name = "lon")
    private double longitude;

    @Column(name = "state_abv")
    private String stateAbbreviation;

    @Column(name = "state")
    private String state;

    @Column(name = "area")
    private String area;

    // One location can be used by many properties (via mapper)
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<PropertyLocationMapperEntity> propertyMappings;

}
