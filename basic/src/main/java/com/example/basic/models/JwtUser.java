package com.example.basic.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "jwt_user")
public class JwtUser implements UserDetails {

    @Id
    @NotBlank
    @Column(name = "username")
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "refresh_token")
    private String refreshToken;

    //OneToOne relationship start
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user", referencedColumnName = "id")
    private User user;
    //OneToOne relationship end

    //ManyToMany relationship start
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "jwt_user_jwt_role",
            joinColumns = @JoinColumn(name = "jwt_user_id", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "jwt_role_id", referencedColumnName = "id"))
    private Set<JwtRole> jwtRoles;
    //ManyToMany relationship end

    //implements methods UserDetails start
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return jwtRoles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    //implements methods UserDetails end
}
