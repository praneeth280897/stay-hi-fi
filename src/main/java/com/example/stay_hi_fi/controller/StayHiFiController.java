package com.example.stay_hi_fi.controller;

import com.example.stay_hi_fi.entity.AddImagesRequestDTO;
import com.example.stay_hi_fi.request.PropertyDetailsRequestDTO;
import com.example.stay_hi_fi.request.PropertyDetailsSearchRequestDTO;
import com.example.stay_hi_fi.response.PaginationResponseDTO;
import com.example.stay_hi_fi.response.PropertyDetailsResponse;
import com.example.stay_hi_fi.sevice.StayHifiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
@CrossOrigin(originPatterns = "*")
@RequestMapping(value = "/property")
public class StayHiFiController {

    @Autowired
    private StayHifiService stayHifiService;

    @GetMapping(value = "/add-property-by-file")
    public String addLocationByFile(@RequestParam ("file")MultipartFile multipartFile) throws IOException {
        stayHifiService.addPropertyByFile(multipartFile);
        return  "SUCCESS";
    }

    @GetMapping(value = "/get")
        public ResponseEntity<PaginationResponseDTO<PropertyDetailsResponse>> getAllPropertyDetails(@RequestParam(defaultValue = "10",value = "size") int pageSize,
                                                                                                @RequestParam(defaultValue = "0",value = "page")int pageNumber,
                                                                                                    @RequestParam (value = "city", required = false)String city) {
        PaginationResponseDTO<PropertyDetailsResponse> response =
                stayHifiService.getAllPropertyDetails(pageNumber, pageSize,city);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> addProperty(@RequestBody List<PropertyDetailsRequestDTO> propertyDetailsRequest) {
        return new ResponseEntity<>(stayHifiService.addProperty(propertyDetailsRequest),HttpStatus.OK);
    }

    @PostMapping(value = "/search")
    public ResponseEntity<PaginationResponseDTO<PropertyDetailsResponse>> searchPropertyBy(@RequestBody PropertyDetailsSearchRequestDTO propertyDetailsSearchRequestDTO,
                                                                                        @RequestParam(defaultValue = "10",value = "size") int pageSize,
                                                                                           @RequestParam(defaultValue = "0",value = "page")int pageNumber) {
        PaginationResponseDTO<PropertyDetailsResponse> response =
                stayHifiService.searchPropertyBy(propertyDetailsSearchRequestDTO,pageNumber,pageSize);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/add-image")
    public String addImages(@RequestBody List<AddImagesRequestDTO> addImagesRequestDTO) {
        return stayHifiService.addImages(addImagesRequestDTO);
    }

    @GetMapping(value = "/get-by-id")
    public ResponseEntity<PropertyDetailsResponse> getPropertyById(@RequestParam(value = "id") long id) {
        return new ResponseEntity<>(stayHifiService.getPropertyDetailsById(id),HttpStatus.OK);
    }
}
