package com.muhammet.service;

import com.muhammet.dto.request.UserProfileRequestDto;
import com.muhammet.mapper.UserProfileMapper;
import com.muhammet.repository.UserProfileRepository;
import com.muhammet.repository.entity.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service

public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }


    public void save(UserProfileRequestDto dto) {
        userProfileRepository.save(UserProfileMapper.INSTANCE.fromDto(dto));
    }

    public void update(UserProfileRequestDto dto) {
        Optional<UserProfile> userProfile = userProfileRepository.findOptionalByUserId(dto.getId());
        if (userProfile.isEmpty()) {
            userProfileRepository.save(UserProfileMapper.INSTANCE.fromDto(dto));
        } else {
            UserProfile profile = userProfile.get();
            profile.setPhoto(dto.getPhoto());
            profile.setName(dto.getName());
            profile.setPhoto(dto.getPhoto());
            profile.setEmail(dto.getEmail());
            profile.setState(dto.getState());
            userProfileRepository.save(profile);
        }
    }

    public Iterable<UserProfile> findall() {
        return userProfileRepository.findAll();
    }


    public UserProfile findById(String id) {
        return userProfileRepository.findById(id).orElse(null);
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
        return userProfileRepository.findAll(pageable);
    }
}
