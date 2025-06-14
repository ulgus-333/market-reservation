package com.reservation.api.config.properties;

import jakarta.mail.internet.InternetAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;

@Configuration
public class SmtpProperties {
    private static final String ALIAS = "임시사업자명";

    @Value("${spring.mail.username}")
    private String senderMail;

    public InternetAddress senderMailWithAlias() throws UnsupportedEncodingException {
        return new InternetAddress(this.senderMail, ALIAS);
    }
}
