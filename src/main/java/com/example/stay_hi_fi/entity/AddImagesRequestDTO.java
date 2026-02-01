package com.example.stay_hi_fi.entity;

import lombok.Data;

import java.util.List;

@Data
public class AddImagesRequestDTO {

    private List<String> url;
    private Long propertyId;
}
