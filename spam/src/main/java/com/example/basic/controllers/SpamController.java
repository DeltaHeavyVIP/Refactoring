package com.example.basic.controllers;

import com.example.objects.common.SpamMessage;
import com.example.basic.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/spam")
public class SpamController {

    private final EmailService emailService;

    public SpamController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/ping")
    public ResponseEntity<?> isAlive() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> sendSpamMessage(SpamMessage spamMessage) {
        emailService.sendEmail(spamMessage);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
