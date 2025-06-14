package com.reservation.api.common.application.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MailViewType {
    SEARCH_ID("search-id-view", "[임시사업자명] 아이디 찾기 인증 메일"),
    RESET_PW("reset-pw-view", "[임시사업자명] 비밀번호 초기화 인증 메일"),
    ;

    private final String view;
    private final String title;
}
