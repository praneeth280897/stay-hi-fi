package com.example.stay_hi_fi.controller;

import com.example.stay_hi_fi.request.UserRequestDTO;
import com.example.stay_hi_fi.request.WishListRequest;
import com.example.stay_hi_fi.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody UserRequestDTO user){
        return new ResponseEntity<String>(userService.signin(user), HttpStatus.OK);
    }

    @PostMapping("/add/wishlist")
    public ResponseEntity<String> signup(@RequestBody List<WishListRequest> wishListRequest){
        return new ResponseEntity<>(userService.addToWishList(wishListRequest),HttpStatus.OK);
    }
}
