package com.example.objects.basic.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CardRequestDto {
    @NotBlank
    private String cardNumber;
    @NotBlank
    private String cardDateEnd;
    @NotBlank
    private Integer cardCVC;
    @NotBlank
    private Integer money;
}
