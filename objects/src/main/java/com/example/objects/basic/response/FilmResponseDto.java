package com.example.objects.basic.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class FilmResponseDto {
    Integer id;
    private String name;
    private String describe;
    private Integer cost;
    private LocalDate releaseYear;
    private String producer;
    private String filmToken;
    private ArrayList<String> genres;
}
