package com.muhammet.controller;

import com.muhammet.dto.request.UserProfileRequestDto;
import com.muhammet.repository.entity.UserProfile;
import com.muhammet.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/elastic-user-profile")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    @PostMapping("/save")
    public ResponseEntity<Void>save(@RequestBody UserProfileRequestDto dto){
        userProfileService.save(dto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/update")
    public ResponseEntity<Void>update(@RequestBody UserProfileRequestDto dto){
        userProfileService.update(dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/find-all")
    public ResponseEntity<Iterable<UserProfile>> findAll(){
        return ResponseEntity.ok(userProfileService.findall());
    }
}
