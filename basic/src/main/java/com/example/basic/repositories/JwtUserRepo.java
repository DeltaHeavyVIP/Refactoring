package com.example.basic.repositories;

import com.example.basic.models.JwtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtUserRepo extends JpaRepository<JwtUser, Integer> {

    JwtUser findByUsername(String username);

}
