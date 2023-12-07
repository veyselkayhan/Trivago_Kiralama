package com.muhammet.mapper;

import com.muhammet.dto.request.UserProfileRequestDto;
import com.muhammet.repository.entity.UserProfile;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-07T14:07:14+0300",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.jar, environment: Java 17.0.9 (Azul Systems, Inc.)"
)
@Component
public class UserProfileMapperImpl implements UserProfileMapper {

    @Override
    public UserProfile fromDto(UserProfileRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserProfile.UserProfileBuilder userProfile = UserProfile.builder();

        userProfile.userId( dto.getId() );
        userProfile.id( dto.getId() );
        userProfile.authId( dto.getAuthId() );
        userProfile.userName( dto.getUserName() );
        userProfile.email( dto.getEmail() );
        userProfile.name( dto.getName() );
        userProfile.photo( dto.getPhoto() );
        userProfile.phone( dto.getPhone() );
        userProfile.state( dto.getState() );

        return userProfile.build();
    }
}
