package com.example.basic.schedulers;

import com.example.basic.feign.SpamService;
import com.example.basic.repositories.CardRepo;
import com.example.basic.repositories.FilmRepo;
import com.example.basic.repositories.UserRepo;
import com.example.basic.services.RabbitMQSender;
import com.example.objects.common.SpamMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class Schedul {

    @Value("${application.spam.receiver}")
    private String receiver;

    private final FilmRepo filmRepo;
    private final CardRepo cardRepo;
    private final UserRepo userRepo;
    private final RabbitMQSender rabbitMQSender;

    private final SpamService spamService;

    @Autowired
    public Schedul(FilmRepo filmRepo, CardRepo cardRepo, UserRepo userRepo, RabbitMQSender rabbitMQSender, SpamService spamService) {
        this.filmRepo = filmRepo;
        this.cardRepo = cardRepo;
        this.userRepo = userRepo;
        this.rabbitMQSender = rabbitMQSender;
        this.spamService = spamService;
    }

    @Scheduled(fixedRate = 120)
    @Async
    public void informationAboutDB() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        log.info("Amount of users :" + userRepo.count());
        log.info("Amount of films :" + filmRepo.count());
        log.info("Amount of films :" + cardRepo.count());
        log.info("End collecting information! Time: " + formatter.format(new Date()));
    }


    @Scheduled(fixedRate = 120000)
    @Async
    public void sendSpamMessageRabbitMQ() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        log.info("Start send spam messages! Time: " + formatter.format(new Date()));
        rabbitMQSender.send(new SpamMessage(receiver, "Test RabbitMQ"));
        log.info("End send spam messages! Time: " + formatter.format(new Date()));
    }


    @Scheduled(fixedRate = 300000)
    @Async
    public void sendSpamMessageFeign() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        log.info("Start send spam messages! Time: " + formatter.format(new Date()));
        spamService.sendSpamMessage(new SpamMessage(receiver, "Test Feign"));
        log.info("End send spam messages! Time: " + formatter.format(new Date()));
    }

}
