package com.example.basic.service;

import com.example.objects.common.SpamMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String email;

    public void sendEmail(SpamMessage spamMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(spamMessage.getEmail());
        message.setSubject("Incredible offer");
        message.setText(spamMessage.getText());
        javaMailSender.send(message);
    }
}
