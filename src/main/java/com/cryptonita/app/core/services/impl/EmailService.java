package com.cryptonita.app.core.services.impl;

import com.cryptonita.app.core.services.IEmailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService implements IEmailService {

    private JavaMailSender mailSender;
    
    @Override
    public void send(String to, String subject, String content) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(to);
        email.setSubject(subject);
        email.setText(content);
        mailSender.send(email);
    }
}
