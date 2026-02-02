package com.example.stay_hi_fi.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(schema = "stay_hi_fi",name = "tbl_property_loc_mapper")
@Data
public class PropertyLocationMapperEntity extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    @ToString.Exclude
    private LocationEntity location;

    // Join to property table
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false, unique = true)
    @ToString.Exclude
    private PropertyDetailsEntity property;

    @Column(name = "lat")
    private Double latitude;

    @Column(name = "lon")
    private Double longitude;

    @Column(name = "area")
    private String area;

    @Column(name="location_url")
    private String locationUrl;

}
