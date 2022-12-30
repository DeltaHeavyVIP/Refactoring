package com.example.basic.controllers;

import com.example.basic.services.FilmService;
import com.example.objects.basic.request.FilmRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated

@RestController
@RequestMapping("/api/film")
public class FilmController extends AbstractController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable Integer id) {
        return new ResponseEntity<>(filmService.getFilmById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createFilm(@Valid @RequestBody FilmRequestDto filmDto) {
        return new ResponseEntity<>(filmService.createFilm(filmDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFilmById(@PathVariable Integer id, @Valid @RequestBody FilmRequestDto filmDto) {
        return new ResponseEntity<>(filmService.updateFilmById(id, filmDto), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFilmById(@PathVariable Integer id) {
        filmService.deleteFilmById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
