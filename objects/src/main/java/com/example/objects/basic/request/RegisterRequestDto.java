package com.example.objects.basic.request;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RegisterRequestDto {
    private String username;
    private String password;
    private ArrayList<String> roles;
}
