package com.example.stay_hi_fi.repository;

import com.example.stay_hi_fi.entity.PropertyDetailsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyDetailsRepository extends JpaRepository<PropertyDetailsEntity, Long>, JpaSpecificationExecutor<PropertyDetailsEntity> {

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

    @EntityGraph(value = "Property.fullDetails", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT p FROM PropertyDetailsEntity p")
    Page<PropertyDetailsEntity> findAllOptimized(Pageable pageable);
}