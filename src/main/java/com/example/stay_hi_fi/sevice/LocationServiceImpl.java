package com.example.stay_hi_fi.sevice;


import com.example.stay_hi_fi.entity.LocationEntity;
import com.example.stay_hi_fi.repository.LocationRepository;
import com.example.stay_hi_fi.request.AddLocationRequestDTO;
import com.example.stay_hi_fi.response.LocationResponse;
import com.example.stay_hi_fi.response.MetroCitiesResponseDTO;
import com.example.stay_hi_fi.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService{

    private static final String SYSTEM= "SYS";
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<LocationResponse> getAllLocations(String searchTerm) {

        List<LocationEntity> locationEntities = new ArrayList<>();
        if(searchTerm!=null) {
            return getLocationResponses(locationRepository.findByAreaContainingIgnoreCaseOrCityContainingIgnoreCaseOrStateContainingIgnoreCase(searchTerm,searchTerm,searchTerm));
        }
        return getLocationResponses(locationRepository.findAll());
    }

    private List<LocationResponse> getLocationResponses(List<LocationEntity> locationEntities) {
        List<LocationResponse> locations = new ArrayList<>();
        for(LocationEntity locationEntity : locationEntities) {
            LocationResponse location = new LocationResponse();
            location.setId(locationEntity.getId());
            location.setStateAbv(locationEntity.getStateAbbreviation());
            location.setLatitude(locationEntity.getLatitude());
            location.setLongitude(locationEntity.getLongitude());
            location.setPostalCode(locationEntity.getPostalCode());
            location.setCountry(locationEntity.getCountry());
            location.setCity(locationEntity.getCity());
            location.setArea(locationEntity.getArea());
            location.setState(locationEntity.getState());
            location.setPropertiesCount(locationEntity.getPropertyMappings().stream().count());
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
                location.setCountry(addLocationRequestDTO.getCountry());
                location.setPostalCode(addLocationRequestDTO.getPostalCode());
                location.setState(addLocationRequestDTO.getState());
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

    @Override
    @Transactional(readOnly = true)
    public List<MetroCitiesResponseDTO> getMetroCities() {
        List<LocationEntity> metroCities = locationRepository.findByIsMetroCity(Boolean.TRUE);
        List<MetroCitiesResponseDTO> response = new ArrayList<>();
        for(LocationEntity locationEntity : metroCities) {
            MetroCitiesResponseDTO metroCity = new MetroCitiesResponseDTO();
            metroCity.setCityIdentity("Metro City");
            metroCity.setName(locationEntity.getCity());
            metroCity.setPropertiesCount((long) locationEntity.getPropertyMappings().size());
            response.add(metroCity);
        }

        return response;
    }

    @Override
    public String editLocation(AddLocationRequestDTO locationRequestDTO) {
        Optional<LocationEntity> locationEntity = locationRepository.findById(locationRequestDTO.getId());

        if(locationEntity.isPresent()) {
            LocationEntity location = locationEntity.get();
            location.setIsMetroCity(locationRequestDTO.getIsMetroCity());
            location.setArea(locationRequestDTO.getArea());
            location.setCity(locationRequestDTO.getCity());
            location.setPostalCode(locationRequestDTO.getPostalCode());
            location.setCountry(locationRequestDTO.getCountry());
            location.setLatitude(locationRequestDTO.getLatitude()!=null?locationRequestDTO.getLatitude():0);
            location.setLongitude(locationRequestDTO.getLongitude()!=null?locationRequestDTO.getLongitude():0);
            location.setState(locationRequestDTO.getState());
            location.setStateAbbreviation(locationRequestDTO.getStateAbv());
            location.setUpdatedBy(SYSTEM);
            location.setUpdatedDate(new Date());
            locationRepository.save(location);
        }
        return "SUCCESS";
    }
}
