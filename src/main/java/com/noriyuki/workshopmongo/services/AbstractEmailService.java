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

    @Override
    public void sendNewPasswordEmail(User user, String newPassword) {
        SimpleMailMessage sm = prepareNewPasswordEmail(user, newPassword);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(User user, String newPassword) {
        SimpleMailMessage sm = new SimpleMailMessage();

        sm.setTo(user.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPassword);

        return sm;
    }
}
