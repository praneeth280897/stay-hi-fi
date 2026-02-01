package com.example.stay_hi_fi.repository;

import com.example.stay_hi_fi.entity.PropertyMediaMapperEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyMediaDetailsRepository extends JpaRepository<PropertyMediaMapperEntity, Long> {
}
