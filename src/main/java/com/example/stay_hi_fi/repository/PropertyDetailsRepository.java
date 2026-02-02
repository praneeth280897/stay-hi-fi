package com.example.stay_hi_fi.repository;

import com.example.stay_hi_fi.entity.PropertyDetailsEntity;
import com.example.stay_hi_fi.response.PropertyDetailsResponse;
import com.example.stay_hi_fi.sevice.PropertyDetailsProjection;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

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

    @EntityGraph(value = "Property.leanDetails", type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = "SELECT p FROM PropertyDetailsEntity p",
            countQuery = "SELECT count(p.id) FROM PropertyDetailsEntity p")
    Page<PropertyDetailsProjection> findAllOptimized(Pageable pageable);

    @EntityGraph(value = "Property.leanDetails")
    @Query("SELECT p FROM PropertyDetailsEntity p")
    List<PropertyDetailsProjection> findDataOnly(Pageable pageable);

    @Query("SELECT count(p.id) FROM PropertyDetailsEntity p")
    long countOnly();

    @Query("SELECT p FROM PropertyDetailsEntity p " +
            "LEFT JOIN FETCH p.propertyLocationMapper l " +
            "LEFT JOIN FETCH l.location " +
            "LEFT JOIN FETCH p.mediaMapper " +
            "WHERE p.id = :id")
    Optional<PropertyDetailsEntity> findByIdOptimized(@Param("id") Long id);

    @EntityGraph(attributePaths = {"propertyLocationMapper.location"})
    @Query(value = "SELECT p FROM PropertyDetailsEntity p",
            countQuery = "SELECT count(p.id) FROM PropertyDetailsEntity p")
    Page<PropertyDetailsEntity> findAll(Specification<PropertyDetailsEntity> spec, Pageable pageable);
}