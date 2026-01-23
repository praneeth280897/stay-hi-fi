package com.example.stay_hi_fi.sevice;

import com.example.stay_hi_fi.entity.LocationEntity;
import com.example.stay_hi_fi.entity.PropertyDetailsEntity;
import com.example.stay_hi_fi.repository.LocationRepository;
import com.example.stay_hi_fi.repository.PropertyDetailsRepository;
import com.example.stay_hi_fi.request.AddLocationRequestDTO;
import com.example.stay_hi_fi.request.PropertyDetailsRequestDTO;
import com.example.stay_hi_fi.response.LocationResponse;
import com.example.stay_hi_fi.response.PaginationResponseDTO;
import com.example.stay_hi_fi.response.PropertyDetailsResponse;
import com.example.stay_hi_fi.util.Constants;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StayHiFiServiceImpl implements StayHifiService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PropertyDetailsRepository propertyDetailsRepository;

    @Override
    public String addLocation(AddLocationRequestDTO addLocationRequestDTO) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setCity(addLocationRequestDTO.getCity());
        locationEntity.setCountry(addLocationRequestDTO.getCountry());
        locationEntity.setState(addLocationRequestDTO.getState());
        locationEntity.setLongitude(addLocationRequestDTO.getLongitude());
        locationEntity.setPostalCode(addLocationRequestDTO.getPostalCode());
        locationEntity.setStateAbbreviation(addLocationRequestDTO.getStateAbv());
        locationEntity.setCreatedBy("9999");
        locationRepository.save(locationEntity);
        return null;
    }

    @Override
    public ResponseEntity<List<LocationResponse>> getAllLocations() {

        List<LocationEntity> locations = locationRepository.findAll();
        List<LocationResponse> response = new ArrayList<>();
        for (LocationEntity location : locations) {
            LocationResponse locationResponse = new LocationResponse();
            locationResponse.setCity(location.getCity());
            locationResponse.setCountry(location.getCountry());
            locationResponse.setLongitude(location.getLongitude());
            locationResponse.setState(location.getState());
            locationResponse.setPostalCode(location.getPostalCode());
            locationResponse.setLatitude(location.getLatitude());
            locationResponse.setStateAbv(location.getState());
            response.add(locationResponse);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public void addPropertyByFile(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter formatter = new DataFormatter();
        List<PropertyDetailsEntity> propertyDetails = new ArrayList<>();
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            PropertyDetailsEntity propertyDetailsEntity = new PropertyDetailsEntity();
            LocationEntity locationEntity = new LocationEntity();
            locationEntity.setId(1L);
            propertyDetailsEntity.setLocationEntity(locationEntity);
            propertyDetailsEntity.setPropertyName(row.getCell(2).getStringCellValue());
            propertyDetailsEntity.setLocation(row.getCell(3).getStringCellValue());
            propertyDetailsEntity.setFeasibleVisitDate(row.getCell(4).getStringCellValue());
            propertyDetailsEntity.setPropertyType(row.getCell(5).getStringCellValue());
            propertyDetailsEntity.setRent(formatter.formatCellValue(row.getCell(7)));
            propertyDetailsEntity.setFurnishingType(row.getCell(6).getStringCellValue());
            propertyDetailsEntity.setDeposit(formatter.formatCellValue(row.getCell(8)));
            propertyDetailsEntity.setMaintenanceCharges(formatter.formatCellValue(row.getCell(10)));
            propertyDetailsEntity.setMoveInDate(row.getCell(11).getStringCellValue());
            propertyDetailsEntity.setPropertyDescription(row.getCell(12).getStringCellValue());
            propertyDetailsEntity.setTenantPreference(row.getCell(14).getStringCellValue());
            propertyDetailsEntity.setMediaLinkUrl(row.getCell(15).getStringCellValue());
            propertyDetailsEntity.setPetFriendly(row.getCell(13).getStringCellValue().equalsIgnoreCase("YES"));
            propertyDetails.add(propertyDetailsEntity);
        }
        workbook.close();
        propertyDetailsRepository.saveAll(propertyDetails);
    }

    @Override
    public PaginationResponseDTO<PropertyDetailsResponse> getAllPropertyDetails(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PropertyDetailsEntity> propertyDetailsList = propertyDetailsRepository.findAll(pageable);
        Page<PropertyDetailsResponse> propertyDetails = propertyDetailsList.map(this::setPropertyResponse);

        return new PaginationResponseDTO(propertyDetails);
    }

    private PropertyDetailsResponse setPropertyResponse(PropertyDetailsEntity propertyDetailsEntity) {
        PropertyDetailsResponse propertyDetailsResponse = new PropertyDetailsResponse();

        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setCity(propertyDetailsEntity.getLocationEntity().getCity());
        locationResponse.setId(propertyDetailsEntity.getLocationEntity().getId());
        locationResponse.setLongitude(propertyDetailsEntity.getLocationEntity().getLongitude());
        locationResponse.setCountry(propertyDetailsEntity.getLocationEntity().getCountry());
        locationResponse.setCity(propertyDetailsEntity.getLocationEntity().getCity());
        locationResponse.setState(propertyDetailsEntity.getLocationEntity().getState());
        locationResponse.setPostalCode(propertyDetailsEntity.getLocationEntity().getPostalCode());
        locationResponse.setArea(propertyDetailsEntity.getLocationEntity().getArea());
        locationResponse.setLatitude(propertyDetailsEntity.getLocationEntity().getLatitude());
        locationResponse.setStateAbv(propertyDetailsEntity.getLocationEntity().getStateAbbreviation());
        propertyDetailsResponse.setLocationDetails(locationResponse);
        propertyDetailsResponse.setId(propertyDetailsEntity.getId());
        propertyDetailsResponse.setTenantPreference(propertyDetailsEntity.getTenantPreference());
        propertyDetailsResponse.setDeposit(propertyDetailsEntity.getDeposit());
        propertyDetailsResponse.setPropertyName(propertyDetailsEntity.getPropertyName());
        propertyDetailsResponse.setPropertyType(propertyDetailsEntity.getPropertyType());
        propertyDetailsResponse.setPropertyDescription(propertyDetailsEntity.getPropertyDescription());
        propertyDetailsResponse.setLocation(propertyDetailsEntity.getLocation());
        propertyDetailsResponse.setFurnishingType(propertyDetailsEntity.getFurnishingType());
        propertyDetailsResponse.setFeasibleVisitDate(propertyDetailsEntity.getFeasibleVisitDate());
        propertyDetailsResponse.setMaintenanceCharges(propertyDetailsEntity.getMaintenanceCharges());
        propertyDetailsResponse.setRent(propertyDetailsEntity.getRent());
        propertyDetailsResponse.setMediaLinkUrl(propertyDetailsEntity.getMediaLinkUrl());
        propertyDetailsResponse.setPetFriendly(propertyDetailsEntity.getPetFriendly());
        propertyDetailsResponse.setFeasibleVisitDate(propertyDetailsEntity.getFeasibleVisitDate());
        propertyDetailsResponse.setDeposit(propertyDetailsEntity.getDeposit());
        propertyDetailsResponse.setMoveInDate(propertyDetailsEntity.getMoveInDate());
        return propertyDetailsResponse;
    }

    @Override
    public String addProperty(List<PropertyDetailsRequestDTO> propertyDetailsRequest) {

        List<PropertyDetailsEntity> properties = new ArrayList<>();
        for (PropertyDetailsRequestDTO property : propertyDetailsRequest) {
            PropertyDetailsEntity propertyDetailsEntity = new PropertyDetailsEntity();
            propertyDetailsEntity.setPropertyType(property.getPropertyType());
            propertyDetailsEntity.setPropertyDescription(property.getPropertyDescription());
            propertyDetailsEntity.setDeposit(property.getDeposit());
            propertyDetailsEntity.setLocation(property.getLocation());
            propertyDetailsEntity.setPropertyName(property.getPropertyName());
            propertyDetailsEntity.setPetFriendly(property.getPetFriendly());
            propertyDetailsEntity.setMediaLinkUrl(property.getMediaLinkUrl());
            propertyDetailsEntity.setTenantPreference(property.getTenantPreference());
            propertyDetailsEntity.setMoveInDate(property.getMoveInDate());
            propertyDetailsEntity.setFurnishingType(property.getFurnishingType());
            propertyDetailsEntity.setFeasibleVisitDate(property.getFeasibleVisitDate());
            propertyDetailsEntity.setMaintenanceCharges(property.getMaintenanceCharges());
            propertyDetailsEntity.setRent(property.getRent());
            Optional<LocationEntity> location = locationRepository.findById(property.getLocationDetails().getId());
            if(location.isPresent()) {
                propertyDetailsEntity.setLocationEntity(location.get());
            } else {
                new ResponseEntity<>("Enter Correct Location",HttpStatus.BAD_REQUEST);
            }
            properties.add(propertyDetailsEntity);
        }
        propertyDetailsRepository.saveAll(properties);
        return Constants.SUCCESS;
    }
}
