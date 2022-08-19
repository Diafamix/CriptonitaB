package com.cryptonita.app.core.services;

public interface IEmailService {

    /**
     * Sends an email asynchronously
     * @param to the mail to send the mail to
     * @param subject the subject of this email
     * @param content the content of this email
     */
    void send(String to,String subject,String content);

}
