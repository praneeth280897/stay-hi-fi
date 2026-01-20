package com.example.stay_hi_fi.repository;

import com.example.stay_hi_fi.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity,Long> {

    Optional<LocationEntity> findByCityAndStateAndCountryAndArea(String city,String state,String country,String area);
}
