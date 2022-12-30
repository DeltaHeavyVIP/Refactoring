package com.example.objects.basic.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class FilmRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String describe;
    @Min(0)
    private Integer cost;
    @NotBlank
    private LocalDate releaseYear;
    @NotBlank
    private String producer;
    @NotNull
    private ArrayList<String> genres;

}
