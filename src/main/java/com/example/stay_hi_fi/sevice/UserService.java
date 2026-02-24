package com.example.stay_hi_fi.sevice;

import com.example.stay_hi_fi.request.UserRequestDTO;
import com.example.stay_hi_fi.request.WishListRequest;

import java.util.List;

public interface UserService {
    String signin(UserRequestDTO user);

    String addToWishList(List<WishListRequest> wishListRequest);
}
