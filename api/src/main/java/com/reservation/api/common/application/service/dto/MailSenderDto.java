package com.reservation.api.common.application.service.dto;

import java.util.Map;

public record MailSenderDto(
        String targetEmail,
        MailViewType mailViewType,
        Map<String, Object> contentVariables
) {
    public static MailSenderDto idSearch(String targetEmail, String verificationCode) {
        return new MailSenderDto(
                targetEmail,
                MailViewType.SEARCH_ID,
                Map.of("verificationCode", verificationCode)
        );
    }
}
