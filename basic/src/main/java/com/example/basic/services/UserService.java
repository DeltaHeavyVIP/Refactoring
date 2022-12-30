package com.example.basic.services;

import com.example.basic.exception.InsufficientFundsException;
import com.example.basic.exception.ResourceNotFoundException;
import com.example.basic.models.Card;
import com.example.basic.models.Film;
import com.example.basic.models.JwtUser;
import com.example.basic.models.User;
import com.example.basic.repositories.CardRepo;
import com.example.basic.repositories.FilmRepo;
import com.example.basic.repositories.JwtUserRepo;
import com.example.basic.repositories.UserRepo;
import com.example.basic.utils.ContextWorker;
import com.example.objects.basic.request.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private FilmRepo filmRepo;

    private ContextWorker contextWorker;

    private CardRepo cardRepo;
    private final JwtUserRepo jwtUserRepo;
    private final UserRepo userRepo;

    @Autowired
    public UserService(FilmRepo filmRepo, JwtUserRepo jwtUserRepo, ContextWorker contextWorker, CardRepo cardRepo, UserRepo userRepo) {
        this.filmRepo = filmRepo;
        this.jwtUserRepo = jwtUserRepo;
        this.contextWorker = contextWorker;
        this.cardRepo = cardRepo;
        this.userRepo = userRepo;

    }

    public void updateUser(UserRequestDto userDto) {
        JwtUser jwtUser = jwtUserRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMiddleName(userDto.getMiddleName());
        user.setEmail(userDto.getEmail());
        jwtUser.setUser(user);
        jwtUserRepo.save(jwtUser);
    }


    @Transactional
    public Film addFilmToUser(Integer id) {
        Film film = filmRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Film with ID %d not found", id)));

        User user = contextWorker.getUserFromContext();
        Set<Film> userFilms = contextWorker.getUserFromContext().getFilms();
        for (Film userFilm : userFilms) {
            if (userFilm.getId().equals(film.getId())) {
                return film;
            }
        }

        List<Card> userCards = cardRepo.findCardsByUser_Id(user.getId());
        for (Card card : userCards) {
            if (card.getMoney() >= film.getCost()) {
                card.setMoney(card.getMoney() - film.getCost());
                cardRepo.save(card);
                userFilms.add(film);
                user.setFilms(userFilms);
                userRepo.save(user);
                return film;
            }
        }
        throw new InsufficientFundsException("Insufficient funds on the card");
    }
}
