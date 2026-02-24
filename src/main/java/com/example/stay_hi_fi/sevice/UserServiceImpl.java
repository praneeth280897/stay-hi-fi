package com.example.stay_hi_fi.sevice;

import com.example.stay_hi_fi.entity.UserEntity;
import com.example.stay_hi_fi.entity.UserPropertyMapping;
import com.example.stay_hi_fi.repository.UserPropertyMapperRepository;
import com.example.stay_hi_fi.repository.UserRepository;
import com.example.stay_hi_fi.request.UserRequestDTO;
import com.example.stay_hi_fi.request.WishListRequest;
import com.example.stay_hi_fi.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPropertyMapperRepository userPropertyMapperRepository;

    public UserServiceImpl() {
        super();
    }

    @Override
    public String addToWishList(List<WishListRequest> wishListRequest) {

        UserPropertyMapping userProperty = new UserPropertyMapping();
        Optional<UserPropertyMapping> userPropertyMapping = userPropertyMapperRepository.findByUserId(wishListRequest.get(0).getUserId());
        List<String> addUserMapping = new ArrayList<>();
        List<String> deleteUserMapping = new ArrayList<>();
        List<String> properties = new ArrayList<>();
        Set<String> propertySet = new HashSet<>(properties);
        for (WishListRequest wish : wishListRequest) {
            if (wish.isWishlist()) {
                addUserMapping.add(wish.getPropertyId());
            } else {
                deleteUserMapping.add(wish.getPropertyId());
            }
        }
        String property = null;
        if(userPropertyMapping.isPresent()) {
            userProperty = userPropertyMapping.get();
            properties = userPropertyMapping.get().getPropertyId()!=null && userPropertyMapping.get().getPropertyId().contains(",") ? Arrays.asList(userPropertyMapping.get().getPropertyId().split(",")) : Collections.singletonList(userPropertyMapping.get().getPropertyId());
            propertySet.addAll(properties);
            propertySet.removeIf(deleteUserMapping::contains);
            propertySet.addAll(addUserMapping);
            properties = new ArrayList<>(propertySet);
            property = String.join(",", properties);
            userProperty.setPropertyId(property);
        }
        else  {
            userProperty.setUserId(wishListRequest.get(0).getUserId());
            userProperty.setPropertyId(wishListRequest.get(0).getPropertyId());
        }
        userPropertyMapperRepository.save(userProperty);
        return Constants.SUCCESS;
    }

    @Override
    public String signin(UserRequestDTO user) {

        Optional<UserEntity> userEntity = userRepository.findByEmail(user.getEmail());
        UserEntity userDetails= new UserEntity();
        if(userEntity.isPresent()){
            userDetails = userEntity.get();
            userDetails.setToken(user.getToken());
        } else {
            setUserDetails(user, userDetails);
        }
        userRepository.save(userDetails);
        return Constants.SUCCESS;
    }

    private static void setUserDetails(UserRequestDTO user, UserEntity userDetails) {
        userDetails.setEmail(user.getEmail());
        userDetails.setToken(user.getToken());
        userDetails.setDisplayName(user.getDisplayName());
        userDetails.setMobileNumber(user.getMobileNumber());
        userDetails.setExpirationDate(user.getExpirationDateTime());
        userDetails.setPhotoUrl(user.getPhotoUrl());
    }
}
