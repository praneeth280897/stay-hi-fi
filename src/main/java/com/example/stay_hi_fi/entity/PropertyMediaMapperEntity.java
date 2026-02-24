package com.example.stay_hi_fi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "stay_hi_fi",name = "tbl_property_media_mapper")
@Data
public class PropertyMediaMapperEntity extends AuditEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    @ToString.Exclude
    private PropertyDetailsEntity propertyDetails;

}
