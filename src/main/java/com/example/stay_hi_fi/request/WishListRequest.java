package com.example.stay_hi_fi.request;

import lombok.Data;

@Data
public class WishListRequest {

    private Long userId;
    private String propertyId;
    private boolean wishlist;
}
