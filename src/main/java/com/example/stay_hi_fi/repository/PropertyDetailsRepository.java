package com.example.stay_hi_fi.repository;

import com.example.stay_hi_fi.entity.PropertyDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyDetailsRepository extends JpaRepository<PropertyDetailsEntity, Long> {

    @Query(value = "SELECT *, ( " +
            "    6371 * acos( " +
            "        cos(radians(:lat)) * " +
            "        cos(radians(p.lat)) * " +
            "        cos(radians(p.lon) - radians(:lng)) + " +
            "        sin(radians(:lat)) * " +
            "        sin(radians(p.lat)) " +
            "    ) " +
            ") AS distance " +
            "FROM tbl_property_details p " +
            "HAVING distance <= :radius " +
            "ORDER BY distance",
            nativeQuery = true)
    List<PropertyDetailsEntity> findNearbyProperties(
            @Param("lat") double lat,
            @Param("lng") double lon,
            @Param("radius") double radiusInKm);
}