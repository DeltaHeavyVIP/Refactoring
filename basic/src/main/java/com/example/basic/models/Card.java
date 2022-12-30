package com.example.basic.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @NotBlank
    @Pattern(regexp = "(^$|[0-9]{16})")
    @Column(name = "card_number")
    private String cardNumber;

    @NotNull
    @Column(name = "card_date_end")
    private String cardDateEnd;

    @Min(100)
    @Max(999)
    @Pattern(regexp = "(^$|[0-9]{3})")
    @NotNull
    @Column(name = "card_CVC")
    private Integer cardCVC;

    @NotNull
    @Column(name = "money")
    private Integer money;

    //ManyToOne or OneToMany relationship start
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user", referencedColumnName = "id")
    private User user;
    //ManyToOne or OneToMany relationship end
}
