-- 유저 테이블간의 종속관계 설정
ALTER TABLE user.user_identify
ADD CONSTRAINT fk_user_identify_user
FOREIGN KEY (user_idx) REFERENCES user.user(idx)
ON DELETE CASCADE;

ALTER TABLE user.admin_identify
ADD CONSTRAINT fk_admin_identify_user
FOREIGN KEY (user_idx) REFERENCES user.user(idx)
ON DELETE CASCADE;

ALTER TABLE user.console_identify
ADD CONSTRAINT fk_console_identify_user
FOREIGN KEY (user_idx) REFERENCES user.user(idx)
ON DELETE CASCADE;