package com.walletwise.main.config.application;

import com.walletwise.infrastructure.adapters.EmailAdapter;
import com.walletwise.infrastructure.gateways.helpers.JasperReportHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Value("${app.email.sender}")
    private String appMailSender;

    @Value("${app.email.password}")
    private String password;

    @Bean
    public EmailAdapter emailAdapter(JavaMailSender mailSender) {
        return new EmailAdapter(mailSender, appMailSender);
    }


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(appMailSender);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public JasperReportHelper jasperReportHelper(DataSource dataSource) {
        return new JasperReportHelper(dataSource);
    }
}
