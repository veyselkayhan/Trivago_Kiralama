package com.muhammet.config;

import com.muhammet.repository.entity.UserProfile;
import com.muhammet.service.UserProfileService;
import com.muhammet.service.YetkiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserDetails implements UserDetailsService {
    private final UserProfileService userProfileService;
    private final YetkiService yetkiService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    /**
     * 1- Sistemde kayıtlı kullanıcıların listesinden kullanıcının bilgilerini droğrulanır
     * 2- Bu kullanıcıya ait yetkiler var ise bu yetkiler çekilir.
     * 3- Spring için UserDetails nesnesi oluşturulur.
     * @param authId
     * @return
     */
    public UserDetails findByAuthId(Long authId){
        Optional<UserProfile> userProfile = userProfileService.findByAuthId(authId);
        if(userProfile.isEmpty()) throw new RuntimeException("Yetkisiz Kullanıcı ");
        List<GrantedAuthority> yetkileri = new ArrayList<>();
        yetkiService.findAllByUserId(userProfile.get().getUserId()).forEach(y->{
            yetkileri.add(new SimpleGrantedAuthority(y.getYetkiAdi()));
        });
        return User.builder()
                .username(userProfile.get().getUserName())
                .authorities(yetkileri)
                .password("")
                .accountExpired(false)
                .accountLocked(false)
                .build();
    }
}
