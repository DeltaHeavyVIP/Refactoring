package com.example.basic.configuration.security;

import com.example.basic.models.JwtUser;
import com.example.basic.repositories.JwtUserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUsersDetailService implements UserDetailsService {
    private final JwtUserRepo jwtUserRepo;

    public JwtUsersDetailService(JwtUserRepo jwtUserRepo) {
        this.jwtUserRepo = jwtUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUser jwtUser = jwtUserRepo.findByUsername(username);

        if (jwtUser == null) throw new UsernameNotFoundException("User with username " + username + " does not exists");

        return jwtUser;
    }

}
