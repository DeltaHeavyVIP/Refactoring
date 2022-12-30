package com.example.basic.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "film")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "describe")
    private String describe;

    @Min(0)
    @Column(name = "cost")
    private Integer cost;

    @NotBlank
    @Column(name = "release_year")
    private LocalDate releaseYear;

    @NotBlank
    @Column(name = "producer")
    private String producer;

    @NotBlank
    @Column(name = "film_token")
    private String filmToken;

    //ManyToMany relationship start
//    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private Set<Genre> genres;

    @ManyToMany(mappedBy = "films", fetch = FetchType.LAZY)
    private Set<User> users;
    //ManyToMany relationship end
}
