package com.muhammet.graphql.query;

import com.muhammet.repository.UserProfileRepository;
import com.muhammet.repository.entity.UserProfile;
import com.muhammet.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserProfileQueryResolver  {

    private final UserProfileService userProfileService;
    @QueryMapping
    public Iterable<UserProfile>findAll(){
        return userProfileService.findall();
    }
    @QueryMapping
    public UserProfile findById(String id){
        return userProfileService.findById(id);
    }
}
