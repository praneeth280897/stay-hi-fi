package com.example.stay_hi_fi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "stay_hi_fi",name = "tbl_property_details")
@Data
@NamedEntityGraph(
        name = "Property.fullDetails",
        attributeNodes = {
                @NamedAttributeNode("propertyLocationMapper"),
                @NamedAttributeNode(value = "propertyLocationMapper", subgraph = "location-subgraph"),
                @NamedAttributeNode("mediaMapper")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "location-subgraph",
                        attributeNodes = @NamedAttributeNode("location")
                )
        }
)
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
    private Double rent;

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

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL)
    private PropertyLocationMapperEntity propertyLocationMapper;

    @OneToMany(mappedBy = "propertyDetails", fetch = FetchType.LAZY)
    private List<PropertyMediaMapperEntity> mediaMapper;
}
