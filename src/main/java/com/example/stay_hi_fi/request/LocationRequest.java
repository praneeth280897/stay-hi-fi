package com.example.stay_hi_fi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
    private Long id;
    private String city;
    private String country;
    private String state;
    private String postalCode;
    private String stateAbv;
    private Double latitude;
    private Double longitude;
    private String area;
    private String locationUrl;
}
