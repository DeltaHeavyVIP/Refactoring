package com.example.objects.basic.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthorizationResponseDto {
    private String username;
    private String token;
    private String refreshToken;
}
