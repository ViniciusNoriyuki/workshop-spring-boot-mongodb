package com.noriyuki.workshopmongo.services;

import com.noriyuki.workshopmongo.domain.User;
import com.noriyuki.workshopmongo.repository.UserRepository;
import com.noriyuki.workshopmongo.services.exception.ObjectNotFoundException;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPassword = newPassword();
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
        emailService.sendNewPasswordEmail(user, newPassword);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }

        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);

        if (opt == 0) {
            return (char) (random.nextInt(10) + 48);
        } else if (opt == 1) {
            return (char) (random.nextInt(26) + 65);
        } else {
            return (char) (random.nextInt(26) + 97);
        }
    }
}
