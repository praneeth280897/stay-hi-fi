package com.example.stay_hi_fi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_location",schema = "stay_hi_fi")
public class LocationEntity extends AuditEntity {

    @Id
    @Column(name = "id")
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

    @Column(name="state_abv")
    private String stateAbbreviation;

    @Column(name = "state")
    private String state;

    @Column(name="area")
    private String area;

     @OneToMany(mappedBy = "locationEntity",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
     private List<PropertyDetailsEntity> propertyDetailsEntityList;

}
