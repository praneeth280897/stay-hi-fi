package com.example.stay_hi_fi.sevice;

import com.example.stay_hi_fi.entity.*;
import com.example.stay_hi_fi.repository.LocationRepository;
import com.example.stay_hi_fi.repository.PropertyDetailsRepository;
import com.example.stay_hi_fi.repository.PropertyLocationMapperEntityRepository;
import com.example.stay_hi_fi.repository.PropertyMediaDetailsRepository;
import com.example.stay_hi_fi.request.AddLocationRequestDTO;
import com.example.stay_hi_fi.request.PropertyDetailsRequestDTO;
import com.example.stay_hi_fi.request.PropertyDetailsSearchRequestDTO;
import com.example.stay_hi_fi.response.LocationResponse;
import com.example.stay_hi_fi.response.PaginationResponseDTO;
import com.example.stay_hi_fi.response.PropertyDetailsResponse;
import com.example.stay_hi_fi.util.Constants;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StayHiFiServiceImpl implements StayHifiService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PropertyDetailsRepository propertyDetailsRepository;

    @Autowired
    private PropertyLocationMapperEntityRepository propertyLocationMapperEntityRepository;

    @Autowired
    private PropertyMediaDetailsRepository propertyMediaDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

        List<PropertyLocationMapperEntity> propertyLocationMapperEntities = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        List<PropertyDetailsEntity> propertyDetails = new ArrayList<>();
        for (int i = 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            PropertyDetailsEntity propertyDetailsEntity = new PropertyDetailsEntity();
            Optional<LocationEntity> location = locationRepository.findById(1L);
            propertyDetailsEntity.setPropertyName(row.getCell(2).getStringCellValue());
            propertyDetailsEntity.setLocation(row.getCell(3).getStringCellValue());
            propertyDetailsEntity.setFeasibleVisitDate(row.getCell(4).getStringCellValue());
            propertyDetailsEntity.setPropertyType(row.getCell(5).getStringCellValue());
            propertyDetailsEntity.setRent(Double.valueOf(row.getCell(7).getNumericCellValue()));
            propertyDetailsEntity.setFurnishingType(row.getCell(6).getStringCellValue());
            propertyDetailsEntity.setDeposit(formatter.formatCellValue(row.getCell(8)));
            propertyDetailsEntity.setMaintenanceCharges(formatter.formatCellValue(row.getCell(10)));
            propertyDetailsEntity.setMoveInDate(row.getCell(11).getStringCellValue());
            propertyDetailsEntity.setPropertyDescription(row.getCell(12).getStringCellValue());
            propertyDetailsEntity.setTenantPreference(row.getCell(14).getStringCellValue());
            propertyDetailsEntity.setMediaLinkUrl(row.getCell(15).getStringCellValue());
            propertyDetailsEntity.setPetFriendly(row.getCell(13).getStringCellValue().equalsIgnoreCase("YES"));
            propertyDetails.add(propertyDetailsEntity);

            PropertyLocationMapperEntity propertyLocationMapperEntity = new PropertyLocationMapperEntity();
            propertyLocationMapperEntity.setLocation(location.get());
            propertyLocationMapperEntity.setProperty(propertyDetailsEntity);
            propertyDetailsEntity.setPropertyLocationMapper(propertyLocationMapperEntity);
            propertyLocationMapperEntities.add(propertyLocationMapperEntity);

        }
        workbook.close();
        propertyDetailsRepository.saveAll(propertyDetails);
        propertyLocationMapperEntityRepository.saveAll(propertyLocationMapperEntities);
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
        propertyDetailsResponse.setLocationDetails(locationResponse);
        propertyDetailsResponse.setId(propertyDetailsEntity.getId());
        propertyDetailsResponse.setTenantPreference(propertyDetailsEntity.getTenantPreference());
        propertyDetailsResponse.setDeposit(propertyDetailsEntity.getDeposit());
        propertyDetailsResponse.setPropertyName(propertyDetailsEntity.getPropertyName());
        propertyDetailsResponse.setPropertyType(propertyDetailsEntity.getPropertyType());
        propertyDetailsResponse.setPropertyDescription(propertyDetailsEntity.getPropertyDescription());
        propertyDetailsResponse.setFurnishingType(propertyDetailsEntity.getFurnishingType());
        propertyDetailsResponse.setFeasibleVisitDate(propertyDetailsEntity.getFeasibleVisitDate());
        propertyDetailsResponse.setMaintenanceCharges(propertyDetailsEntity.getMaintenanceCharges());
        propertyDetailsResponse.setRent(propertyDetailsEntity.getRent());
        propertyDetailsResponse.setMediaLinkUrl(propertyDetailsEntity.getMediaLinkUrl());
        propertyDetailsResponse.setPetFriendly(propertyDetailsEntity.getPetFriendly());
        propertyDetailsResponse.setFeasibleVisitDate(propertyDetailsEntity.getFeasibleVisitDate());
        propertyDetailsResponse.setDeposit(propertyDetailsEntity.getDeposit());
        propertyDetailsResponse.setMoveInDate(propertyDetailsEntity.getMoveInDate());
        if(!propertyDetailsEntity.getPropertyMediaUrl().isEmpty()) {
            propertyDetailsResponse.setImages(propertyDetailsEntity.getPropertyMediaUrl().stream().map(PropertyMediaMapperEntity::getUrl).collect(Collectors.toList()));
        }
        propertyDetailsResponse.setLocationDetails(mapLocation(propertyDetailsEntity.getPropertyLocationMapper()));
        return propertyDetailsResponse;
    }

    private LocationResponse mapLocation(PropertyLocationMapperEntity propertyLocationMapper) {
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setId(propertyLocationMapper.getLocation().getId());
        locationResponse.setLongitude(propertyLocationMapper.getLongitude());
        locationResponse.setLocationUrl(propertyLocationMapper.getLocationUrl());
        locationResponse.setArea(propertyLocationMapper.getArea());
        locationResponse.setStateAbv(propertyLocationMapper.getLocation().getState());
        locationResponse.setCity(propertyLocationMapper.getLocation().getCity());
        locationResponse.setCountry(propertyLocationMapper.getLocation().getCountry());
        locationResponse.setPostalCode(propertyLocationMapper.getLocation().getPostalCode());
        locationResponse.setState(propertyLocationMapper.getLocation().getState());
        locationResponse.setArea(propertyLocationMapper.getArea());
        locationResponse.setLatitude(propertyLocationMapper.getLatitude() != null ? propertyLocationMapper.getLatitude() : null);
        locationResponse.setLongitude(propertyLocationMapper.getLongitude() != null ? propertyLocationMapper.getLongitude() : null);
        return locationResponse;
    }

    @Override
    public String addProperty(List<PropertyDetailsRequestDTO> propertyDetailsRequest) {

        List<PropertyLocationMapperEntity> propertyLocationMapperEntities = new ArrayList<>();
        List<PropertyDetailsEntity> properties = new ArrayList<>();
        for (PropertyDetailsRequestDTO property : propertyDetailsRequest) {
            PropertyLocationMapperEntity propertyLocationMapperEntity = new PropertyLocationMapperEntity();

            PropertyDetailsEntity propertyDetailsEntity = new PropertyDetailsEntity();
            propertyDetailsEntity.setPropertyType(property.getPropertyType());
            propertyDetailsEntity.setPropertyDescription(property.getPropertyDescription());
            propertyDetailsEntity.setDeposit(property.getDeposit());
            propertyDetailsEntity.setPropertyName(property.getPropertyName());
            propertyDetailsEntity.setPetFriendly(property.getPetFriendly());
            propertyDetailsEntity.setMediaLinkUrl(property.getMediaLinkUrl());
            propertyDetailsEntity.setTenantPreference(property.getTenantPreference());
            propertyDetailsEntity.setMoveInDate(property.getMoveInDate());
            propertyDetailsEntity.setFurnishingType(property.getFurnishingType());
            propertyDetailsEntity.setFeasibleVisitDate(property.getFeasibleVisitDate());
            propertyDetailsEntity.setMaintenanceCharges(property.getMaintenanceCharges());
            propertyDetailsEntity.setRent(property.getRent());
            Optional<LocationEntity> location = locationRepository.findByCityAndStateAndCountryAndArea(property.getLocationDetails().getCity(), property.getLocationDetails().getState(), property.getLocationDetails().getCountry(), property.getLocationDetails().getArea());
            LocationEntity locationEntity = new LocationEntity();
            if (location.isPresent()) {
                locationEntity = location.get();
            } else {
                locationEntity.setArea(property.getLocationDetails().getArea());
                locationEntity.setLatitude(property.getLocationDetails().getLatitude());
                locationEntity.setLongitude(property.getLocationDetails().getLongitude());
                locationEntity.setState(property.getLocationDetails().getState());
                locationEntity.setCountry(property.getLocationDetails().getCountry());
                locationEntity.setCountry(property.getLocationDetails().getCountry());
                locationEntity.setCity(property.getLocationDetails().getCity());
                locationEntity.setPostalCode(property.getLocationDetails().getPostalCode());
                locationEntity.setStateAbbreviation(property.getLocationDetails().getStateAbv());
                locationRepository.save(locationEntity);
            }
            propertyLocationMapperEntity.setLocation(locationEntity);
            propertyLocationMapperEntity.setProperty(propertyDetailsEntity);
            propertyLocationMapperEntity.setArea(property.getLocationDetails().getArea());
            propertyLocationMapperEntity.setLatitude(property.getLocationDetails().getLatitude());
            propertyLocationMapperEntity.setLongitude(property.getLocationDetails().getLongitude());
            propertyLocationMapperEntity.setLocationUrl(property.getLocationDetails().getLocationUrl());
            propertyLocationMapperEntity.setLocation(locationEntity);
            propertyLocationMapperEntities.add(propertyLocationMapperEntity);
            properties.add(propertyDetailsEntity);
        }
        propertyDetailsRepository.saveAll(properties);
        propertyLocationMapperEntityRepository.saveAll(propertyLocationMapperEntities);
        return Constants.SUCCESS;
    }

    @Override
    public PaginationResponseDTO<PropertyDetailsResponse> searchPropertyBy(PropertyDetailsSearchRequestDTO requestDTO, int pageNumber, int pageSize) {
        Specification<PropertyDetailsEntity> spec = getSearchQuery(requestDTO);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<PropertyDetailsEntity> propertyDetailsList = propertyDetailsRepository.findAll(spec, pageable);
        Page<PropertyDetailsResponse> propertyDetails = propertyDetailsList.map(this::setPropertyResponse);
        return new PaginationResponseDTO(propertyDetails);
    }

    private Specification<PropertyDetailsEntity> getSearchQuery(PropertyDetailsSearchRequestDTO dto) {
        return (root, query, cb) -> {

            Join<PropertyDetailsEntity, PropertyLocationMapperEntity> mapperJoin =
                    root.join("propertyLocationMapper", JoinType.LEFT);

            Join<PropertyLocationMapperEntity, LocationEntity> locationJoin =
                    mapperJoin.join("location", JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();

            if (dto.getRent() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("rent"), dto.getRent()));
            }

            if (dto.getPetFriendly() != null) {
                predicates.add(cb.equal(root.get("petFriendly"), dto.getPetFriendly()));
            }

            if (dto.getPropertyName() != null) {
                predicates.add(cb.like(cb.lower(root.get("propertyName")),
                        "%" + dto.getPropertyName().toLowerCase() + "%"));
            }

            if (dto.getPropertyType() != null) {
                predicates.add(cb.equal(root.get("propertyType"), dto.getPropertyType()));
            }

            if (dto.getFurnishingType() != null) {
                predicates.add(cb.equal(root.get("furnishingType"), dto.getFurnishingType()));
            }

            if (dto.getLocation() != null && dto.getLocation().getId() != null) {
                predicates.add(cb.equal(locationJoin.get("id"), dto.getLocation().getId()));
            }

//            if(dto.getLocation() != null)

            query.distinct(true);
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Override
    public String addImages(List<AddImagesRequestDTO> addImagesRequestDTO) {

        List<PropertyMediaMapperEntity> propertyMediaMapperEntities = new ArrayList<>();
        for(AddImagesRequestDTO addImages:addImagesRequestDTO){

            for(String url:addImages.getUrl()){
                PropertyMediaMapperEntity propertyMediaMapperEntity = new PropertyMediaMapperEntity();
                PropertyDetailsEntity propertyDetailsEntity = propertyDetailsRepository.findById(addImages.getPropertyId()).get();
                propertyMediaMapperEntity.setPropertyDetails(propertyDetailsEntity);
                propertyMediaMapperEntity.setUrl(url);
                propertyMediaMapperEntities.add(propertyMediaMapperEntity);
            }
        }
        propertyMediaDetailsRepository.saveAll(propertyMediaMapperEntities);
        return "SUCCESS";
    }
}
