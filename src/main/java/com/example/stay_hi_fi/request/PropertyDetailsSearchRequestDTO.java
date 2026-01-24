package com.example.stay_hi_fi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDetailsSearchRequestDTO {

    private String propertyName;
    private Boolean petFriendly;
    private String moveInDate;
    private String propertyType;
    private Double rent;
    private String furnishingType;
    private LocationRequest location;
}
