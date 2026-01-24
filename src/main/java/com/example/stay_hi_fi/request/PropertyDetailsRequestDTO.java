package com.example.stay_hi_fi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PropertyDetailsRequestDTO {

    private String moveInDate;
    private String maintenanceCharges;
    private String propertyName;
    private String feasibleVisitDate;
    private String propertyType;
    private String furnishingType;
    private Double rent;
    private String deposit;
    private String propertyDescription;
    private Boolean petFriendly = false;
    private String tenantPreference;
    private String mediaLinkUrl;
    private AddLocationRequestDTO locationDetails;
}
