package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IEmailAdapter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.rmi.UnexpectedException;

public class EmailAdapter implements IEmailAdapter {
    private final JavaMailSender mailSender;
    private  String sender;

    public EmailAdapter(JavaMailSender mailSender, String sender) {
        this.mailSender = mailSender;
        this.sender = sender;
    }

    @Override
    public void sendEmail(String receptor, String message,String subject) {
            SimpleMailMessage messageParams = new SimpleMailMessage();
            messageParams.setFrom(this.sender);
            messageParams.setTo(receptor);
            messageParams.setSubject(subject);
            messageParams.setText(message);
            mailSender.send(messageParams);
    }
}
