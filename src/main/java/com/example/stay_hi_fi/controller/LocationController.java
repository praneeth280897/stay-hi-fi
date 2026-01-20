package com.example.stay_hi_fi.controller;

import com.example.stay_hi_fi.request.AddLocationRequestDTO;
import com.example.stay_hi_fi.response.LocationResponse;
import com.example.stay_hi_fi.sevice.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/get-all")
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        return  new ResponseEntity<>(locationService.getAllLocations(), HttpStatus.OK);
    }

    @PostMapping("/add-location")
    public ResponseEntity<String> addNewLocation(@RequestBody List<AddLocationRequestDTO> locationRequestDTO) {
        return new ResponseEntity<>(locationService.addNewLocation(locationRequestDTO),HttpStatus.OK);
    }
}
