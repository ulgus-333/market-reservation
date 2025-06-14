package com.reservation.api.common.application.service;

import com.reservation.api.common.application.service.dto.MailSenderDto;
import com.reservation.api.config.properties.SmtpProperties;
import com.reservation.api.error.exception.BusinessException;
import com.reservation.api.error.type.InternalServerErrorType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Service
public class MailSenderService {
    private final JavaMailSender javaMailSender;
    private final SmtpProperties smtpProperties;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(MailSenderDto mailSenderDto) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(mailSenderDto.targetEmail());
            helper.setSubject(mailSenderDto.mailViewType().getTitle());
            helper.setText(generateContent(mailSenderDto), true);

            helper.setFrom(smtpProperties.senderMailWithAlias());

            javaMailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BusinessException(InternalServerErrorType.FAILED_TO_SEND_MAIL);
        }
    }

    private String generateContent(MailSenderDto mailSenderDto) {
        Context context = new Context();
        context.setVariables(mailSenderDto.contentVariables());

        return templateEngine.process(mailSenderDto.mailViewType().getView(), context);
    }
}
