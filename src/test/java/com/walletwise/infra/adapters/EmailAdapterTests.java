package com.walletwise.infra.adapters;

import com.walletwise.domain.adapters.IEmailAdapter;
import com.walletwise.mocks.Mocks;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class EmailAdapterTests {
    private final String emailSender = Mocks.faker.internet().emailAddress();
    private IEmailAdapter emailAdapter;
    @MockBean
    private JavaMailSender mailSender;

    @BeforeEach
    void setup() {
        this.emailAdapter = new EmailAdapter(mailSender, emailSender);
    }

    @Test
    @DisplayName("Should throw RuntimeException if send email throws")
    void shouldThrowUnexpectedExceptionIfSendEmailThrows() {
        String receptor = Mocks.faker.internet().emailAddress();
        String message = Mocks.faker.lorem().paragraph();
        String subject = Mocks.faker.lorem().word();


        SimpleMailMessage messageParam = new SimpleMailMessage();
        messageParam.setFrom(this.emailSender);
        messageParam.setTo(receptor);
        messageParam.setSubject(subject);
        messageParam.setText(message);

        Mockito.doThrow(RuntimeException.class).when(this.mailSender).send(messageParam);

        Throwable exception = Assertions.catchThrowable(() -> this.emailAdapter.sendEmail(receptor, message, subject));

        Assertions.assertThat(exception).isInstanceOf(RuntimeException.class);
        Mockito.verify(this.mailSender, Mockito.times(1)).send(messageParam);
    }
}
