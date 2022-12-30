package com.example.basic.repositories;

import com.example.basic.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepo extends JpaRepository<Card, Integer> {

    List<Card> findCardsByUser_Id(Integer id);

    Card findCardByCardNumberAndCardDateEndAndCardCVC(String cardNumber, String cardDateEnd, Integer cardCVC);

}
