package com.example.stay_hi_fi.repository;

import com.example.stay_hi_fi.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity,Long> {

    Optional<LocationEntity> findByCityAndStateAndCountryAndArea(String city,String state,String country,String area);

    List<LocationEntity> findByAreaContainingIgnoreCaseOrCityContainingIgnoreCaseOrStateContainingIgnoreCase(
            String area,
            String city,
            String state
    );
     Optional<LocationEntity> findByCityAndStateAndCountry(
                String city,
                String state,
                String country
        );

    List<LocationEntity> findByIsMetroCity(Boolean isMetroCity);


}
