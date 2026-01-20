package com.example.stay_hi_fi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PropertyDetailsResponse {

    private String moveInDate;
    private String maintenanceCharges;
    private String appartment;
    private String feasibleVisitDate;
    private String propertyType;
    private String furnishingType;
    private String rent;
    private String deposit;
    private String propertyDescription;
    private Boolean petFriendly = false;
    private String tenantPreference;
    private String mediaLinkUrl;
    private String location;
    private LocationResponse locationDetails;

}
