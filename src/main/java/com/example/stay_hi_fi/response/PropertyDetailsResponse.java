package com.example.stay_hi_fi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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
    private Double rent;
    private String deposit;
    private String propertyDescription;
    private Boolean petFriendly = false;
    private String tenantPreference;
    private String mediaLinkUrl;
    private List<String> images;
    private List<String> videos;
    private LocationResponse locationDetails;

}
