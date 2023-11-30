package com.muhammet.graphql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserProfileInput {
    Long authId;
    String name;
    String userName;
    String email;
    String userId;
    String phone;
    String photo;
}
