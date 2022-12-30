package com.example.basic.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class FilmToken {

    @Value("${jwt.secret.film}")
    private static String jwtSecretValueFilm;

    public static String generateFilmToken(String nameFilm, String nameProducer) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + 48 * 60 * 1000 * 60);
        return Jwts.builder()
                .setSubject(nameFilm + "&" + nameProducer)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS256, jwtSecretValueFilm)
                .compact();
    }

}
