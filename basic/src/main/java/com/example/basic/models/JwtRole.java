package com.example.basic.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "jwt_role")
public class JwtRole implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank
    @Column(name = "name")
    private String name;

    //ManyToMany relationship start
    @ManyToMany(mappedBy = "jwtRoles", fetch = FetchType.LAZY)
    private Set<JwtUser> jwtUsers;
    //ManyToMany relationship end

    //implements methods GrantedAuthority start
    @Override
    public String getAuthority() {
        return name;
    }
    //implements methods GrantedAuthority start
}
