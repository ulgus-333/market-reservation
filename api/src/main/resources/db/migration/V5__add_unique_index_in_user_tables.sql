/*
유저 유니크 인덱스 정리
1. 유저 공통 테이블
    - 이름, 이메일 (이미 unique index 등록되어 있음)
2. 일반 유저 상세 테이블
    - 연락처, 닉네임
3. 어드민 유저 상세 테이블
    - 상점 idx + 사번
4. 콘솔 유저 상세 테이블 (X)
*/

CREATE UNIQUE INDEX user_phone_unique_index ON user.user_identify(phone);
CREATE UNIQUE INDEX user_nickname_unique_index ON user.user_identify(nickname);

CREATE UNIQUE INDEX admin_user_id_number_unique_index ON user.admin_identify(market_idx, identification_number);