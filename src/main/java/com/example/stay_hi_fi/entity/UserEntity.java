package com.example.stay_hi_fi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_user")
@Data
public class UserEntity extends AuditEntity {

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "token")
    private String token;

    @Column(name = "expirationDate")
    private LocalDateTime expirationDate;
}
