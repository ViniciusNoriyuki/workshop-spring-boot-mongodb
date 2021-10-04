package com.noriyuki.workshopmongo.services;

import com.noriyuki.workshopmongo.domain.User;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(User obj);

    void sendEmail(SimpleMailMessage msg);
}
