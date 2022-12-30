package com.example.basic.utils;

import com.example.basic.models.JwtRole;
import com.example.basic.models.JwtUser;
import com.example.basic.models.User;
import com.example.basic.repositories.JwtUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ContextWorker {
    private final JwtUserRepo jwtUserRepo;

    @Autowired
    public ContextWorker(JwtUserRepo jwtUserRepo) {
        this.jwtUserRepo = jwtUserRepo;
    }

    public User getUserFromContext() {
        JwtUser jwtUser = jwtUserRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return jwtUser.getUser();
    }

    public boolean isAdmin() {
        JwtUser jwtUser = jwtUserRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Set<JwtRole> jwtRoles = jwtUser.getJwtRoles();
        for (JwtRole jwtRole : jwtRoles) {
            if (jwtRole.getName().equals("Admin")) {
                return true;
            }
        }
        return false;
    }
}
