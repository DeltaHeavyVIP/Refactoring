package com.example.basic.services;

import com.example.basic.exception.HackingException;
import com.example.basic.exception.InternalServerException;
import com.example.basic.exception.InvalidInputDataException;
import com.example.basic.exception.ResourceNotFoundException;
import com.example.basic.models.Card;
import com.example.basic.repositories.CardRepo;
import com.example.basic.utils.ContextWorker;
import com.example.objects.basic.request.CardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private CardRepo cardRepo;

    private ContextWorker contextWorker;
    private ConversionService conversionService;


    @Autowired
    public CardService(CardRepo cardRepo, ConversionService conversionService, ContextWorker contextWorker) {
        this.cardRepo = cardRepo;
        this.conversionService = conversionService;
        this.contextWorker = contextWorker;
    }

    public Card getCardById(Integer id) {
        Card card = cardRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Card with ID %d not found", id)));
        if(!contextWorker.isAdmin()) {
            if (!card.getUser().getId().equals(contextWorker.getUserFromContext().getId())) {
                throw new HackingException("Attempt to get someone else's card data");
            }
        }
        return card;
    }

    public Card createCard(CardRequestDto cardDto) {
        Card existCard = cardRepo.findCardByCardNumberAndCardDateEndAndCardCVC(cardDto.getCardNumber(), cardDto.getCardDateEnd(), cardDto.getCardCVC());
        if (existCard != null) {
            throw new InvalidInputDataException("This card is already present in the system");
        }

        Card newCard = conversionService.convert(cardDto, Card.class);
        if (newCard == null) {
            throw new InternalServerException("Problem with convert card data");
        }
        newCard.setUser(contextWorker.getUserFromContext());
        return cardRepo.save(newCard);
    }

    public Card updateCard(Integer id, CardRequestDto cardDto) {
        Card oldCard = cardRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Card with ID %d not found", id)));
        Card newCard = conversionService.convert(cardDto, Card.class);
        if (newCard == null) {
            throw new InternalServerException("Problem with convert card data");
        }
        newCard.setId(oldCard.getId());
        return cardRepo.save(newCard);
    }

    public void deleteCardById(Integer id) {
        try {
            cardRepo.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(String.format("Card with ID %d not found", id));
        }
    }
}
