package com.example.stay_hi_fi.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    private Long id;
    private String city;
    private String country;
    private String state;
    private String postalCode;
    private String stateAbv;
    private Double latitude;
    private Double longitude;
    private String area;
}
