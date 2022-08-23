package com.cryptonita.app.core.services.impl;

import com.cryptonita.app.core.services.IEmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@AllArgsConstructor
public class EmailService implements IEmailService {

    private JavaMailSender mailSender;

    @Override
    public void send(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(content, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom(to);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("failed to send email", e);
        }
    }
}
