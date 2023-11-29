package com.muhammet.controller;

import com.muhammet.dto.request.GetProfileByTokenRequestDto;
import com.muhammet.dto.request.UpdateProfiliRequestDto;
import com.muhammet.dto.request.UserProfileSaveRequestDto;
import com.muhammet.dto.response.UserProfileResponseDto;
import com.muhammet.repository.entity.UserProfile;
import com.muhammet.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.muhammet.constants.RestApiUrls.*;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/upper-name")
    public String getUpperName(String username) {
        return userProfileService.getUpperName(username);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserProfile>> getAllUserProfile(){
        return ResponseEntity.ok(userProfileService.getAllUserProfile());
    }

    @PostMapping("/clear-key")
    public ResponseEntity<Void>clearKey(String key){
        userProfileService.clearKey(key);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getmessage")
    public String getMessage() {
        return "Bu UserProfile Servistir";
    }


    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody @Valid UserProfileSaveRequestDto dto) {
        UserProfile user = userProfileService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get-profile")
    public ResponseEntity<UserProfileResponseDto> getProfileByToken(@RequestBody @Valid GetProfileByTokenRequestDto dto) {
        return ResponseEntity.ok(userProfileService.getProfileByToken(dto));
    }

    @PostMapping("/update-profile")
    public ResponseEntity<Boolean> updateProfile(@RequestBody UpdateProfiliRequestDto dto) {
        return ResponseEntity.ok(userProfileService.updateProfile(dto));
    }



}
