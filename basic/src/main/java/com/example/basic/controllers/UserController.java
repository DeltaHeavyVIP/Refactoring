package com.example.basic.controllers;

import com.example.basic.services.UserService;
import com.example.objects.basic.request.UserRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/")
    public ResponseEntity<?> updateUser(@RequestBody UserRequestDto userDto) {
        userService.updateUser(userDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/buy/film/{id}")
    public ResponseEntity<?> buyFilm(@PathVariable Integer id) {
        return new ResponseEntity<>(userService.addFilmToUser(id), HttpStatus.OK);
    }

}
