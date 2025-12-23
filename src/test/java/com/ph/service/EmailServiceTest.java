package com.ph.service;

import com.ph.exception.customs.EmailSendingException;
import com.ph.utils.MessageUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private Configuration freemarkerConfig;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailService, "emailTemplate", "template.ftl");
        ReflectionTestUtils.setField(emailService, "emailSubject", "Subject");
        ReflectionTestUtils.setField(emailService, "mailImage", "img.png");
        ReflectionTestUtils.setField(emailService, "confirmEmailTemplate", "confirm.ftl");
        ReflectionTestUtils.setField(emailService, "confirmEmailSubject", "Confirm");
        ReflectionTestUtils.setField(emailService, "checkTourRequestTemplate", "check.ftl");
        ReflectionTestUtils.setField(emailService, "informationEmailSubject", "Info");
    }

    @Test
    void sendResetPasswordEmail_throwsOnTemplateError() throws Exception {
        when(messageUtil.getMessage("error.mail.reset-password")).thenReturn("mail error: %s");
        when(freemarkerConfig.getTemplate("template.ftl")).thenThrow(new java.io.IOException("boom"));
        when(mailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));

        assertThatThrownBy(() -> emailService.sendResetPasswordEmail("user@example.com", "code"))
                .isInstanceOf(EmailSendingException.class);
    }

    @Test
    void sendResetPasswordEmail_sendsMail() throws Exception {
        Template template = mock(Template.class);
        when(freemarkerConfig.getTemplate("template.ftl")).thenReturn(template);
        when(mailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));

        emailService.sendResetPasswordEmail("user@example.com", "code");

        verify(mailSender).send(any(MimeMessage.class));
    }
}
