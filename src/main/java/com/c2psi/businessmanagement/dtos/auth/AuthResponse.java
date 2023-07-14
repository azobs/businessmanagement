package com.c2psi.businessmanagement.dtos.auth;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthResponse {
    @Setter
    @Getter
    private String accessToken;
}
