package com.example.objects.basic.request;

import lombok.Data;

@Data
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
}
