package com.example.basic.services;

import com.example.basic.models.JwtRole;
import com.example.basic.models.JwtUser;
import com.example.basic.repositories.JwtRoleRepo;
import com.example.basic.repositories.JwtUserRepo;
import com.example.objects.basic.request.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JwtUserService {

    private JwtUserRepo jwtUserRepo;

    private JwtRoleRepo jwtRoleRepo;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public JwtUserService(JwtUserRepo jwtUserRepo, JwtRoleRepo jwtRoleRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtUserRepo = jwtUserRepo;
        this.jwtRoleRepo = jwtRoleRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public JwtUser findJwtUserByUserName(String username) {
        return jwtUserRepo.findByUsername(username);
    }

    public JwtUser register(RegisterRequestDto registerDto) {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setUsername(registerDto.getUsername());
        jwtUser.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
        jwtUser.setRefreshToken(Base64.getEncoder().encodeToString((UUID.randomUUID() + "&" + registerDto.getUsername()).getBytes()));

        Set<JwtRole> rolesJwtUser = new HashSet<>();
        List<JwtRole> rolesDB = jwtRoleRepo.findAll();
        for (String role : registerDto.getRoles()) {
            for (JwtRole roleDB : rolesDB) {
                if (roleDB.getName().equals(role)) {
                    rolesJwtUser.add(roleDB);
                }
            }
        }
        jwtUser.setJwtRoles(rolesJwtUser);
        return jwtUserRepo.save(jwtUser);
    }


    public JwtUser updateRefreshToken(JwtUser jwtUser) {
        jwtUser.setRefreshToken(Base64.getEncoder().encodeToString((UUID.randomUUID() + "&" + jwtUser.getUsername()).getBytes()));
        return jwtUserRepo.save(jwtUser);
    }
}
