package com.example.stay_hi_fi.sevice;

import com.example.stay_hi_fi.request.AddLocationRequestDTO;
import com.example.stay_hi_fi.response.LocationResponse;

import java.util.List;

public interface LocationService {

    List<LocationResponse> getAllLocations();

    String addNewLocation(List<AddLocationRequestDTO> locationRequestDTO);
}
