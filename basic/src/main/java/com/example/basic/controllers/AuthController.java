package com.example.basic.controllers;

import com.example.basic.configuration.security.JwtProvider;
import com.example.basic.models.JwtUser;
import com.example.basic.services.JwtUserService;
import com.example.objects.basic.request.LoginRequestDto;
import com.example.objects.basic.request.RegisterRequestDto;
import com.example.objects.basic.response.AuthorizationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NonUniqueResultException;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends AbstractController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    private final JwtUserService jwtUserService;

    public AuthController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, JwtUserService jwtUserService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.jwtUserService = jwtUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerDto) {
        if (jwtUserService.findJwtUserByUserName(registerDto.getUsername()) != null) {
            throw new NonUniqueResultException("User with this username has been already registered");
        }
        JwtUser jwtUser = jwtUserService.register(registerDto);
        String token = jwtProvider.createToken(registerDto.getUsername());
        Authentication authentication = jwtProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>(new AuthorizationResponseDto(registerDto.getUsername(), token, jwtUser.getRefreshToken()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto) {
        JwtUser jwtUser = jwtUserService.findJwtUserByUserName(loginDto.getUsername());
        if (jwtUser == null)
            throw new UsernameNotFoundException("User with username " + loginDto.getUsername() + " not found");
        String token = jwtProvider.createToken(loginDto.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtUser.getUsername(), loginDto.getPassword()));

        return new ResponseEntity<>(new AuthorizationResponseDto(jwtUser.getUsername(), token, jwtUser.getRefreshToken()), HttpStatus.OK);
    }

}
