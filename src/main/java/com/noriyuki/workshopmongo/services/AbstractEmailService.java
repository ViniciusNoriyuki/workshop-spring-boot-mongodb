package com.noriyuki.workshopmongo.services;

import com.noriyuki.workshopmongo.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService{

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(User obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromUser(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromUser(User obj) {
        SimpleMailMessage sm = new SimpleMailMessage();

        sm.setTo(obj.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Usuário criado com sucesso! Código: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());

        return sm;
    }
}
