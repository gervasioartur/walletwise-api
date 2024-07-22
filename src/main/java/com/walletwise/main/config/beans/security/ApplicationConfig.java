package com.walletwise.main.config.beans.security;

import com.walletwise.infra.adapters.AuthAdapter;
import com.walletwise.infra.adapters.CryptoAdapter;
import com.walletwise.infra.adapters.EmailAdapter;
import com.walletwise.infra.adapters.LoadUserAdapter;
import com.walletwise.infra.gateways.mappers.UserEntityMapper;
import com.walletwise.infra.gateways.mappers.ValidationTokenEntityMapper;
import com.walletwise.infra.gateways.security.SignKey;
import com.walletwise.infra.gateways.token.*;
import com.walletwise.infra.persistence.repositories.IUserRepository;
import com.walletwise.infra.persistence.repositories.IValidationTokenEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    @Value("${app.email.sender}")
    private String appMailSender;

    @Value("${app.email.password}")
    private String password;

    @Bean
    public EmailAdapter emailAdapter (JavaMailSender mailSender){
        return new EmailAdapter(mailSender,appMailSender);
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

}
