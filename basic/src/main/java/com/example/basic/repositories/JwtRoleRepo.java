package com.example.basic.repositories;

import com.example.basic.models.JwtRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JwtRoleRepo extends JpaRepository<JwtRole, String> {
}
