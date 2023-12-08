package com.muhammet.utility;

import com.muhammet.manager.ElasticSearchUserProfileManager;
import com.muhammet.mapper.UserProfileMapper;
import com.muhammet.repository.UserProfileRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
public class TestAndRun {

    private final UserProfileRepository repository;
    private final ElasticSearchUserProfileManager manager;
    //@PostConstruct
    public void init(){
       repository.findAll().forEach(u->{
           manager.update(UserProfileMapper.INSTANCE.toUserProfileRequestDto(u));
           System.out.println("gönderildi... "+ u.getUserName());
       });
    }
}
