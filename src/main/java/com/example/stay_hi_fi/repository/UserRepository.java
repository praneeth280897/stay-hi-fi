package com.example.stay_hi_fi.repository;

import com.example.stay_hi_fi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

    Optional<UserEntity> findByEmail(String email);
}
