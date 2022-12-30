package com.example.basic.converter;

import com.example.basic.models.Card;
import com.example.objects.basic.request.CardRequestDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CardRequestDtoToCard implements Converter<CardRequestDto, Card> {
    @Override
    public Card convert(CardRequestDto cardDto) {
        Card card = new Card();
        card.setCardNumber(cardDto.getCardNumber());
        card.setCardDateEnd(cardDto.getCardDateEnd());
        card.setCardCVC(cardDto.getCardCVC());
        card.setMoney(cardDto.getMoney());
        return card;
    }
}