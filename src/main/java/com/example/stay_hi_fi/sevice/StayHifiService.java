package com.example.stay_hi_fi.sevice;

import com.example.stay_hi_fi.entity.AddImagesRequestDTO;
import com.example.stay_hi_fi.request.AddLocationRequestDTO;
import com.example.stay_hi_fi.request.PropertyDetailsRequestDTO;
import com.example.stay_hi_fi.request.PropertyDetailsSearchRequestDTO;
import com.example.stay_hi_fi.response.LocationResponse;
import com.example.stay_hi_fi.response.PaginationResponseDTO;
import com.example.stay_hi_fi.response.PropertyDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StayHifiService {

    public String addLocation(AddLocationRequestDTO addLocationRequestDTO);

    ResponseEntity<List<LocationResponse>> getAllLocations();

    void addPropertyByFile(MultipartFile multipartFile) throws IOException;

    PaginationResponseDTO<PropertyDetailsResponse> getAllPropertyDetails(int pageNumber, int pageSize,String state);

    String addProperty(List<PropertyDetailsRequestDTO> propertyDetailsRequest);

    PaginationResponseDTO<PropertyDetailsResponse> searchPropertyBy(PropertyDetailsSearchRequestDTO propertyDetailsSearchRequestDTO, int pageNumber, int pageSize);

    String addImages(List<AddImagesRequestDTO> addImagesRequestDTO);

    PropertyDetailsResponse getPropertyDetailsById(long id);
}
