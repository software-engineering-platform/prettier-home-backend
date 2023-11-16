package com.ph.service;

import com.ph.exception.customs.EmailSendingException;
import com.ph.utils.MessageUtil;
import freemarker.template.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final Configuration freemarkerConfig;
    private final MessageUtil messageUtil;

    @Value("${email.account.forgot.template}")
    private String emailTemplate;

    @Value("${email.account.forgot.subject}")
    private String emailSubject;

    @Value("${email.account.forgot.text}")
    private String emailText;

    @Value("${email.account.forgot.email-img}")
    private String mailImage;


    public void sendResetPasswordEmail(String recipientEmail, String resetCode) {
        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            Template template = freemarkerConfig.getTemplate(emailTemplate);

            StringWriter writer = new StringWriter();
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("code", resetCode);
            dataModel.put("imgSource", mailImage);
            template.process(dataModel, writer);
            String emailContent = emailText + writer.toString();

            helper.setTo(recipientEmail);
            helper.setSubject(emailSubject);
            helper.setText(emailContent, true);

            mailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            throw new EmailSendingException(String.format(messageUtil.getMessage("error.mail.reset-password"), e.getMessage()));
        }
    }

}
