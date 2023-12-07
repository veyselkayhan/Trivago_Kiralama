package com.muhammet.graphql.query;

import com.muhammet.dto.request.UserProfileRequestDto;
import com.muhammet.graphql.model.UserProfileInput;
import com.muhammet.repository.entity.UserProfile;
import com.muhammet.service.UserProfileService;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserProfileQueryResolver {

    private final UserProfileService userProfileService;
    @QueryMapping
    public Iterable<UserProfile> findAll(){
        return userProfileService.findAll();
    }
    @QueryMapping
    public UserProfile findById(@Argument String id){
        return userProfileService.findById(id);
    }

    @MutationMapping
    public UserProfile saveUser(@Argument UserProfileInput input){
        userProfileService.save(UserProfileRequestDto.builder()
                        .userName(input.getUserName())
                        .authId(input.getAuthId())
                        .email(input.getEmail())
                        .phone(input.getPhone())
                        .name(input.getName())
                        .photo(input.getPhoto())
                .build());
        return new UserProfile(); 
    }
}
