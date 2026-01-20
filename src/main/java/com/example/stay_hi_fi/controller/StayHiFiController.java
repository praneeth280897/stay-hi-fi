package com.example.stay_hi_fi.controller;

import com.example.stay_hi_fi.request.AddLocationRequestDTO;
import com.example.stay_hi_fi.request.PropertyDetailsRequestDTO;
import com.example.stay_hi_fi.response.LocationResponse;
import com.example.stay_hi_fi.response.PropertyDetailsResponse;
import com.example.stay_hi_fi.sevice.StayHifiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/property")
public class StayHiFiController {

    @Autowired
    private StayHifiService stayHifiService;

    @PostMapping(value = "/add-location")
    public ResponseEntity<String> addLocation(@RequestBody AddLocationRequestDTO addLocationRequestDTO) {

        stayHifiService.addLocation(addLocationRequestDTO);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @GetMapping(value = "/get-all-locations")
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        return stayHifiService.getAllLocations();
    }

    @GetMapping(value = "/add-property-by-file")
    public String addLocationByFile(@RequestParam ("file")MultipartFile multipartFile) throws IOException {
        stayHifiService.addPropertyByFile(multipartFile);
        return  "SUCCESS";
    }

    @GetMapping(value = "/get/all/properties")
    public ResponseEntity<Page<PropertyDetailsResponse>> getAllPropertyDetails(@RequestParam(defaultValue = "10") int pageSize,
                                                                               @RequestParam(defaultValue = "0")int pageNumber) {
        return new ResponseEntity<>(stayHifiService.getAllPropertyDetails(pageNumber,pageSize),HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<String> addProperty(@RequestBody List<PropertyDetailsRequestDTO> propertyDetailsRequest) {
        return new ResponseEntity<>(stayHifiService.addProperty(propertyDetailsRequest),HttpStatus.OK);
    }
}
