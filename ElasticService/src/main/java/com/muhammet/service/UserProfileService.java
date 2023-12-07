package com.muhammet.service;

import com.muhammet.dto.request.UserProfileRequestDto;
import com.muhammet.mapper.UserProfileMapper;
import com.muhammet.repository.UserProfileRepository;
import com.muhammet.repository.entity.UserProfile;
import lombok.RequiredArgsConstructor;
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

    public void save(UserProfileRequestDto dto){
        userProfileRepository.save(UserProfileMapper.INSTANCE.fromDto(dto));
    }

    public void update(UserProfileRequestDto dto){
        Optional<UserProfile> userProfile =  userProfileRepository.findOptionalByUserId(dto.getId());
        if(userProfile.isEmpty()){
            userProfileRepository.save(UserProfileMapper.INSTANCE.fromDto(dto));
        }else{
            UserProfile profile = userProfile.get();
            profile.setPhone(dto.getPhone());
            profile.setName(dto.getName());
            profile.setPhoto(dto.getPhoto());
            profile.setEmail(dto.getEmail());
            profile.setState(dto.getState());
            userProfileRepository.save(profile);
        }
    }


    public Iterable<UserProfile> findAll() {
        return  userProfileRepository.findAll();
    }

    public UserProfile findById(String id) {
        return userProfileRepository.findById(id).orElse(null);
    }

    /***
     *
     * @param page -> Hangi sayfayı görmek istediğinizi belirtir.
     * @param size -> bir sayfada kaç kayıt görmek istediğinizi belirtir.
     * @param sortParameter -> hangi parametreye göre sıralama yapmak istediğinizi belirtir. (ad, id, userName)
     * @param sortDirection -> sıralama yönünü belirtir. (ASC, DESC)
     * @return
     */
    public Page<UserProfile> findAll(int page, int size, String sortParameter, String sortDirection){
        Pageable pageable;
        if(sortParameter !=null && !sortParameter.isEmpty()){
            /**
             * Sıralama için bir nesne yaratmak,
             * Sort nesnesi gerekli,
             * Sort.Direction -> ASC(a...z, 0...9), DESC(z...a, 9...0)
             */
            Sort sort =  Sort.by(Sort.Direction.fromString(sortDirection == null ? "ASC" : sortDirection), sortParameter);
            pageable = PageRequest.of(page, size, sort);

        }else
            pageable = Pageable.ofSize(size).withPage(page);

        return userProfileRepository.findAll(pageable);
    }

    public Optional<UserProfile> findByAuthId(Long authId) {
        return userProfileRepository.findOptionalByAuthId(authId);
    }
}
