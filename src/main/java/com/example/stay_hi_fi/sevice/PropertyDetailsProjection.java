package com.example.stay_hi_fi.sevice;

import com.example.stay_hi_fi.entity.PropertyLocationMapperEntity;
import com.example.stay_hi_fi.entity.PropertyMediaMapperEntity;

import java.util.List;

public interface PropertyDetailsProjection {

    Long getId();
    String getMoveInDate();
    String getMaintenanceCharges();
    String getPropertyName();
    String getFeasibleVisitDate();
    String getPropertyType();
    String getFurnishingType();
    Double getRent();
    String getDeposit();
    String getPropertyDescription();
    Boolean getPetFriendly();
    String getTenantPreference();
    String getMediaLinkUrl();

    // Relationships
    PropertyLocationMapperEntity getPropertyLocationMapper();
    List<PropertyMediaMapperEntity> getMediaMapper();
}
