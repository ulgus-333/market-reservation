-- 모든 유형의 유저 이메일 필드 변경
-- 각각의 entity -> 통합 entity로 이동 unique 처리

ALTER TABLE user.user_identify DROP COLUMN email;
ALTER TABLE user.admin_identify DROP COLUMN email;
ALTER TABLE user.console_identify DROP COLUMN email;

ALTER TABLE user.user ADD COLUMN email varchar(50) NOT NULL COMMENT '이메일' AFTER password;
CREATE UNIQUE INDEX user_email_unique_index ON user.user(email);
