package com.example.basic.converter;

import com.example.basic.models.Film;
import com.example.basic.models.Genre;
import com.example.objects.basic.response.FilmResponseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class FilmToFilmResponseDto implements Converter<Film, FilmResponseDto> {
    @Override
    public FilmResponseDto convert(Film film) {
        FilmResponseDto filmResponseDto = new FilmResponseDto();
        filmResponseDto.setId(film.getId());
        filmResponseDto.setName(film.getName());
        filmResponseDto.setDescribe(film.getDescribe());
        filmResponseDto.setReleaseYear(film.getReleaseYear());
        filmResponseDto.setCost(film.getCost());
        filmResponseDto.setProducer(film.getProducer());
        filmResponseDto.setFilmToken(film.getFilmToken());
        ArrayList<String> list = new ArrayList<>();
        for (Genre genre : film.getGenres()) {
            list.add(genre.getGenre());
        }
        filmResponseDto.setGenres(list);
        return filmResponseDto;
    }
}