package com.muhammet.controller;

import com.muhammet.dto.request.UserProfileRequestDto;
import com.muhammet.repository.entity.UserProfile;
import com.muhammet.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    /**
     *
     * @param page -> hangi sayfayı görmek istediğiniz
     * @param size -> bir sayfada kaç kayıt görmek istediğiniz belirtir. Bir kullanıcı kaç kayıt görmek istediğinizi döndürür. Nasıl
     *             Bir kısıtlama yapabiliyoruz.Onuda findall metoduna sınır olarak geçiyoruz.Adı pageable olan ve retun type page olan bir findall metodumuz
     *             var.
     *             Bu bizde pageable ister.
     * @param sortParamater -> hangi parametreye göre sıralama yapmak istediğimizi belirtir.(ad,id,userName)
     * @param sortDirection -> sıralama yönünü belirtir. (ASC,DESC)
     * @return
     */
    @GetMapping("/find-all-page")
    public ResponseEntity<Page<UserProfile>> findAll(int page, int size,String sortParamater,String sortDirection){
        return ResponseEntity.ok(userProfileService.findAll(page,size,sortParamater,sortDirection));
    }
}
