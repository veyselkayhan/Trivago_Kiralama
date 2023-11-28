package com.muhammet.service;

import com.muhammet.dto.request.GetProfileByTokenRequestDto;
import com.muhammet.dto.request.UpdateProfiliRequestDto;
import com.muhammet.dto.request.UserProfileSaveRequestDto;
import com.muhammet.dto.response.UserProfileResponseDto;
import com.muhammet.exception.ErrorType;
import com.muhammet.exception.UserException;
import com.muhammet.mapper.UserProfileMapper;
import com.muhammet.repository.UserProfileRepository;
import com.muhammet.repository.entity.UserProfile;
import com.muhammet.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository repository;
    private final JwtTokenManager jwtTokenManager;
    public UserProfile save(UserProfileSaveRequestDto dto){
        return repository.save(UserProfileMapper.INSTANCE.fromDto(dto));
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
        if(authId.isEmpty())
            throw new UserException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if(userProfile.isEmpty())
            throw new UserException(ErrorType.USER_NOT_FOUND);
        return UserProfileMapper.INSTANCE.toUserProfileResponseDto(userProfile.get());
    }

    public Boolean updateProfile(UpdateProfiliRequestDto dto) {
        Optional<Long> authId = jwtTokenManager.getIdByToken(dto.getToken());
        if(authId.isEmpty())
            throw new UserException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> userProfile = repository.findOptionalByAuthId(authId.get());
        if(userProfile.isEmpty())
            throw new UserException(ErrorType.USER_NOT_FOUND);
        UserProfile updateProfile = userProfile.get();
        updateProfile.setName(dto.getName());
        updateProfile.setEmail(dto.getEmail());
        updateProfile.setPhoto(dto.getPhoto());
        updateProfile.setPhone(dto.getPhone());
        repository.save(updateProfile);
        return true;
    }
}
