package com.example.stay_hi_fi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyDetailsResponse {

    private Long id;
    private String moveInDate;
    private String maintenanceCharges;
    private String propertyName;
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
