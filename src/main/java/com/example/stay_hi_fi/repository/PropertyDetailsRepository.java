package com.example.stay_hi_fi.repository;

import com.example.stay_hi_fi.entity.PropertyDetailsEntity;
import com.example.stay_hi_fi.sevice.PropertyDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT p FROM PropertyDetailsEntity p " +
            "LEFT JOIN FETCH p.propertyLocationMapper l " +
            "LEFT JOIN FETCH l.location " +
            "LEFT JOIN FETCH p.mediaMapper ")
    List<PropertyDetailsProjection> findAllOptimized(Pageable pageable);

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

    @Query("SELECT p FROM PropertyDetailsEntity p" +
            " LEFT JOIN FETCH p.propertyLocationMapper mapper" +
            " LEFT JOIN FETCH mapper.location loc" +
            " LEFT JOIN FETCH p.mediaMapper" +
            " WHERE loc.city = :city")
    List<PropertyDetailsProjection> findByStateOptimized(@Param("city") String city,Pageable pageable);

    @EntityGraph(attributePaths = {"propertyLocationMapper.location"})
    @Query(value = "SELECT p FROM PropertyDetailsEntity p",
            countQuery = "SELECT count(p.id) FROM PropertyDetailsEntity p")
    Page<PropertyDetailsEntity> findAll(Specification<PropertyDetailsEntity> spec, Pageable pageable);
}