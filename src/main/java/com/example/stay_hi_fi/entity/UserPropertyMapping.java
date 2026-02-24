package com.example.stay_hi_fi.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl_user_property_mapping")
@Data
public class UserPropertyMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "property_id")
    private String propertyId;
}
