package com.example.stay_hi_fi.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserRequestDTO {
    private String email;
    private String displayName;
    private String token;
    private String photoUrl;
    private String mobileNumber;
    private LocalDateTime expirationDateTime;
}
