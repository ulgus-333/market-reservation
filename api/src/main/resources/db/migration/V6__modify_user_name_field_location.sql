-- 모든 유형의 유저 이름 필드 변경
-- 각각의 entity -> 통합 entity로 이동 unique 처리

ALTER TABLE user.user_identify DROP COLUMN name;
ALTER TABLE user.admin_identify DROP COLUMN name;
ALTER TABLE user.console_identify DROP COLUMN name;

ALTER TABLE user.user ADD COLUMN name varchar(50) NOT NULL COMMENT '이름(양방향 암호화)' AFTER password;
CREATE INDEX user_name_index ON user.user(name);
