package com.example.basic.controllers;

import com.example.basic.configuration.security.JwtProvider;
import com.example.basic.models.JwtUser;
import com.example.basic.services.JwtUserService;
import com.example.objects.basic.request.RefreshRequestDto;
import com.example.objects.basic.response.AuthorizationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping(value = "/api/refresh")
public class RefreshController extends AbstractController {

    private final JwtProvider jwtProvider;
    private final JwtUserService jwtUserService;

    public RefreshController(JwtProvider jwtProvider, JwtUserService jwtUserService) {
        this.jwtProvider = jwtProvider;
        this.jwtUserService = jwtUserService;
    }

    @PostMapping("/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshRequestDto refreshDto) {
        String username = new String(Base64.getDecoder().decode(refreshDto.getRefreshToken())).split("&")[1];
        JwtUser jwtUser = jwtUserService.findJwtUserByUserName(username);

        if (jwtUser == null) throw new UsernameNotFoundException("User with username " + username + " not found");

        String token = null;
        if (jwtUser.getRefreshToken().equals(refreshDto.getRefreshToken())) {
            jwtUser = jwtUserService.updateRefreshToken(jwtUser);//
            token = jwtProvider.createToken(username);
            Authentication authentication = jwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new BadCredentialsException("Invalid refresh token");
        }

        return new ResponseEntity<>(new AuthorizationResponseDto(username, token, jwtUser.getRefreshToken()), HttpStatus.OK);
    }

}
