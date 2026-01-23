package com.example.stay_hi_fi.sevice;


import com.example.stay_hi_fi.entity.LocationEntity;
import com.example.stay_hi_fi.repository.LocationRepository;
import com.example.stay_hi_fi.request.AddLocationRequestDTO;
import com.example.stay_hi_fi.response.LocationResponse;
import com.example.stay_hi_fi.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService{

    private static final String SYSTEM= "SYS";
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<LocationResponse> getAllLocations() {

        List<LocationResponse> locations = new ArrayList<>();
        List<LocationEntity> locationEntities = locationRepository.findAll();
        for(LocationEntity locationEntity :locationEntities) {
            LocationResponse location = new LocationResponse();
            location.setStateAbv(locationEntity.getStateAbbreviation());
            location.setLatitude(locationEntity.getLatitude());
            location.setLongitude(locationEntity.getLongitude());
            location.setPostalCode(locationEntity.getPostalCode());
            location.setCountry(locationEntity.getCountry());
            location.setCity(locationEntity.getCity());
            locations.add(location);
        }
        return locations;
    }

    @Override
    public String addNewLocation(List<AddLocationRequestDTO> locationRequestDTO) {

            List<LocationEntity> locationEntities = new ArrayList<>();
        for(AddLocationRequestDTO addLocationRequestDTO:locationRequestDTO) {
            Boolean locationIsPresent = locationRepository.findByCityAndStateAndCountryAndArea(addLocationRequestDTO.getCity(),addLocationRequestDTO.getState(),addLocationRequestDTO.getCountry(),addLocationRequestDTO.getArea()).isPresent();
            if(!locationIsPresent) {
                LocationEntity location = new LocationEntity();
                location.setLongitude(addLocationRequestDTO.getLongitude());
                location.setCountry(addLocationRequestDTO.getCountry());
                location.setPostalCode(addLocationRequestDTO.getPostalCode());
                location.setState(addLocationRequestDTO.getState());
                location.setLatitude(addLocationRequestDTO.getLatitude());
                location.setCity(addLocationRequestDTO.getCity());
                location.setStateAbbreviation(addLocationRequestDTO.getStateAbv());
                location.setCreatedBy(SYSTEM);
                location.setArea(addLocationRequestDTO.getArea());
                locationEntities.add(location);
            } else {
                return "Location Already Exist with Area: "+""+addLocationRequestDTO.getArea()+" City: "+addLocationRequestDTO.getCity();
            }
        }
        locationRepository.saveAll(locationEntities);
        return Constants.SUCCESS;
    }
}
