package com.example.basic.controllers;

import com.example.basic.services.CardService;
import com.example.objects.basic.request.CardRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/api/card")
public class CardController extends AbstractController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/ping")
    public ResponseEntity<?> isAlive() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCardById(@PathVariable Integer id) {
        return new ResponseEntity<>(cardService.getCardById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createCard(@Valid @RequestBody CardRequestDto cardDto) {
        return new ResponseEntity<>(cardService.createCard(cardDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCardById(@PathVariable Integer id, @Valid @RequestBody CardRequestDto cardDto) {
        return new ResponseEntity<>(cardService.updateCard(id, cardDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCardById(@PathVariable Integer id) {
        cardService.deleteCardById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
