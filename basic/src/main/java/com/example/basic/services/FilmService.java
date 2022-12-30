package com.example.basic.services;

import com.example.basic.exception.HackingException;
import com.example.basic.exception.InternalServerException;
import com.example.basic.exception.ResourceNotFoundException;
import com.example.basic.models.Film;
import com.example.basic.repositories.FilmRepo;
import com.example.basic.utils.ContextWorker;
import com.example.basic.utils.FilmToken;
import com.example.objects.basic.request.FilmRequestDto;
import com.example.objects.basic.response.FilmResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService {


    private FilmRepo filmRepo;

    private ContextWorker contextWorker;

    private ConversionService conversionService;

    @Autowired
    public FilmService(FilmRepo filmRepo, ConversionService conversionService, ContextWorker contextWorker) {
        this.filmRepo = filmRepo;
        this.conversionService = conversionService;
        this.contextWorker = contextWorker;
    }

    public FilmResponseDto getFilmById(Integer id) {
        Film film = filmRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Film with ID %d not found", id)));
        Set<Film> userFilms = contextWorker.getUserFromContext().getFilms();
        if (contextWorker.isAdmin()) {
            return conversionService.convert(film, FilmResponseDto.class);
        }
        for (Film userFilm : userFilms) {
            if (userFilm.getId().equals(film.getId())) {
                return conversionService.convert(film, FilmResponseDto.class);
            }
        }
        throw new HackingException("Attempt to get film data");
    }

    public FilmResponseDto createFilm(FilmRequestDto filmDto) {
        Film newFilm = conversionService.convert(filmDto, Film.class);
        if (newFilm == null) {
            throw new InternalServerException("Problem with convert film data");
        }
        newFilm.setFilmToken(FilmToken.generateFilmToken(newFilm.getName(), newFilm.getProducer()));
        return conversionService.convert(filmRepo.save(newFilm), FilmResponseDto.class);
    }

    public FilmResponseDto updateFilmById(Integer id, FilmRequestDto filmDto) {
        Film oldFilm = filmRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Film with ID %d not found", id)));
        Film newFilm = conversionService.convert(filmDto, Film.class);
        if (newFilm == null) {
            throw new InternalServerException("Problem with convert film data");
        }
        newFilm.setId(oldFilm.getId());
        newFilm.setFilmToken(FilmToken.generateFilmToken(newFilm.getName(), newFilm.getProducer()));
        return conversionService.convert(filmRepo.save(newFilm), FilmResponseDto.class);
    }

    public void deleteFilmById(Integer id) {
        try {
            filmRepo.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(String.format("Film with ID %d not found", id));
        }
    }
}
