package com.example.basic.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotBlank
    @Column(name = "genre")
    private String genre;

    //ManyToMany relationship start
//    @JsonBackReference
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Film> films;
    //ManyToMany relationship end
}
