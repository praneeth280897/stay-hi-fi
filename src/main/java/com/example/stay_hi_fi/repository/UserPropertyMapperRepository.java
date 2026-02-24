package com.example.stay_hi_fi.repository;

import com.example.stay_hi_fi.entity.UserPropertyMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPropertyMapperRepository extends JpaRepository<UserPropertyMapping,Integer> {

    Optional<UserPropertyMapping> findByUserId(Long userId);

}
