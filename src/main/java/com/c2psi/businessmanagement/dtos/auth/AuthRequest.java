package com.c2psi.businessmanagement.dtos.auth;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthRequest {
    @Setter
    @Getter
    @NotNull(message = "The login or cni number or even email is compulsory to be connected")
    @NotBlank(message = "The login or cni number or even email is compulsory to be connected")
    @NotEmpty(message = "The login or cni number or even email is compulsory to be connected")
    String userToken;
    @Setter
    @Getter
    @NotNull(message = "The password is compulsory to be connected")
    @NotBlank(message = "The password is compulsory to be connected")
    @NotEmpty(message = "The password is compulsory to be connected")
    String password;
}
