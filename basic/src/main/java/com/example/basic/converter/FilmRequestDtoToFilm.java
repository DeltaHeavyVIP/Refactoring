package com.example.basic.converter;

import com.example.basic.exception.ResourceNotFoundException;
import com.example.basic.models.Film;
import com.example.basic.models.Genre;
import com.example.basic.repositories.GenreRepo;
import com.example.objects.basic.request.FilmRequestDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class FilmRequestDtoToFilm implements Converter<FilmRequestDto, Film> {
    private final GenreRepo genreRepo;

    public FilmRequestDtoToFilm(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    @Override
    public Film convert(FilmRequestDto filmDto) throws ResourceNotFoundException {
        Film film = new Film();
        film.setName(filmDto.getName());
        film.setDescribe(filmDto.getDescribe());
        film.setCost(filmDto.getCost());
        film.setReleaseYear(filmDto.getReleaseYear());
        film.setProducer(filmDto.getProducer());
        Set<Genre> genres = new HashSet<>();
        for (String genreName : filmDto.getGenres()) {
            genres.add(genreRepo.findByGenre(genreName).orElseThrow(() -> new ResourceNotFoundException("")));
        }
        film.setGenres(genres);
        return film;
    }
}