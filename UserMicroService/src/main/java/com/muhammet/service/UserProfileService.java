package com.muhammet.service;

import com.muhammet.dto.request.GetProfileByTokenRequestDto;
import com.muhammet.dto.request.UpdateProfiliRequestDto;
import com.muhammet.dto.request.UserProfileSaveRequestDto;
import com.muhammet.dto.response.UserProfileResponseDto;
import com.muhammet.exception.ErrorType;
import com.muhammet.exception.UserException;
import com.muhammet.manager.ElasticSearchUserProfileManager;
import com.muhammet.mapper.UserProfileMapper;
import com.muhammet.repository.UserProfileRepository;
import com.muhammet.repository.entity.UserProfile;
import com.muhammet.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository repository;
    private final JwtTokenManager jwtTokenManager;
    private final CacheManager cacheManager;
    private final ElasticSearchUserProfileManager manager;
    public UserProfile save(UserProfileSaveRequestDto dto) {
        UserProfile result = repository.save(UserProfileMapper.INSTANCE.fromDto(dto));
        Objects.requireNonNull(cacheManager.getCache("UserProfile-findAll")).clear();
        manager.save(UserProfileMapper.INSTANCE.toUserProfileRequestDto(result));
        return result;
//        return repository.save(UserProfile.builder()
//                        .userName(dto.getUserName())
//                        .authId(dto.getAuthId())
//                .build());
    }



    public UserProfileResponseDto getProfileByToken(GetProfileByTokenRequestDto dto) {
        /**
         * Kullanıcı token bilgisini gönderiyor, jwtManager ile token bilgisini doğruluyor ve içinden
         * kişinin authId bilgisini almaya çalışıyoruz.
         */
        Optional<Long> authId = jwtTokenManager.getIdByToken(dto.getToken());
        if (authId.isEmpty())
            throw new UserException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty())
            throw new UserException(ErrorType.USER_NOT_FOUND);
        return UserProfileMapper.INSTANCE.toUserProfileResponseDto(userProfile.get());
    }

    public Boolean updateProfile(UpdateProfiliRequestDto dto) {
        Optional<Long> authId = jwtTokenManager.getIdByToken(dto.getToken());
        if (authId.isEmpty())
            throw new UserException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty())
            throw new UserException(ErrorType.USER_NOT_FOUND);
        UserProfile updateProfile = userProfile.get();
        updateProfile.setName(dto.getName());
        updateProfile.setEmail(dto.getEmail());
        updateProfile.setPhoto(dto.getPhoto());
        updateProfile.setPhone(dto.getPhone());
        repository.save(updateProfile);
        Objects.requireNonNull(cacheManager.getCache("UserProfile-findAll")).clear();
        manager.update(UserProfileMapper.INSTANCE.toUserProfileRequestDto(updateProfile));
        return true;
    }

    /**
     * Burada method icin bir cache olusturup bunun redis tarafından kayıt edilmesini sağlayacağız.
     * redis bu methodu şu şekilde kayıt edecek.
     * KEY                      VALUE
     * getUpperName:muhammet -> MUHAMMET
     * getUpperName:canan    -> CANAN
     * @param username
     * @return
     */


    @Cacheable(value = "getuppername")
    public String getUpperName(String username) {
        String result = username.toUpperCase();
        try {
            Thread.sleep(4000L);
        } catch (Exception exception) {
            System.out.println("Hata Olustu");
            return "Error : " + exception;
        }
        return result;
    }

    public void clearKey(String key){
       Objects.requireNonNull(cacheManager.getCache("getuppername")).evict(key);
    }

    @Cacheable(value = "UserProfile-findAll")
    public List<UserProfile> getAllUserProfile() {
        return repository.findAll();
    }

    public Page<UserProfile> findAll(int page, int size, String sortParamater, String sortDirection) {
        Pageable pageable;
        if (sortParamater != null && !sortParamater.isEmpty()) {
            /**
             * Sıralama için bir nesne yaratmak
             * Sort nesnesi gerekli.
             * Sort.Direction -> ASC(a....z,0.....9),DESC(z.........a,9.....0)
             */
            Sort sort = Sort.by(Sort.Direction.fromString(sortDirection == null ? "ASC" : sortDirection), sortParamater);
            pageable = PageRequest.of(page, size, sort);
        } else
            pageable = Pageable.ofSize(size).withPage(page);
        return repository.findAll(pageable);
    }
}
