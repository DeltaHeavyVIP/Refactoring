package com.example.basic.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Email
    @Column(name = "email")
    private String email;

    //OneToOne relationship start
    //OneToOne relationship end

    //ManyToMany relationship start
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "app_user_film", joinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"))
    private Set<Film> films;
    //ManyToMany relationship end
}
