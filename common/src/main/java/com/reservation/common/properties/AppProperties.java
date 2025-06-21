package com.reservation.common.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppProperties {
    @Value("${application.properties.crypto.secret-key}")
    public String cryptoSecretKey;

}
