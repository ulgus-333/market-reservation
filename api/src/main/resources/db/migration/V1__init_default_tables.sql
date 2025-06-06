-- create user
CREATe TABLE user.role (
    idx int(11) unsigned auto_increment COMMENT '식별번호' primary key,
    authority enum('USER', 'ADMIN', 'CONSOLE') not null COMMENT '권한'
);

CREATE TABLE user.user (
    idx int(11) unsigned auto_increment COMMNET '식별번호' primary key,
    role_idx int(11) unsigned not null COMMENT '권한 식별번호',
    id varchar(50) not null COMMENT '로그인 아이디',
    password varchar(100) not null COMMENT '비밀번호 (단방향 암호화)',
    reg_datetime datetime not null default current_time COMMENT '등록일시',
    mod_datetime datetime null COMMENT '수정일시'
)

CREATE TABLE user.user_identify (
    user_idx int(11) unsigned not null COMMENT '유저 식별번호' primary key,
    name varchar(50) not null COMMENT '이름(양방향 암호화)',
    phone varchar(50) not null COMMENT '연락처(양방향 암호화)',
    email varchar(50) null COMMENT '이메일',
    nickname varchar(50) not null COMMENT '닉네임',
    gender tinyint(1) not null COMMENT '성별 1: 남, 2: 여',
    birth varchar(10) not null COMMENT '생년월일(YYYYMMDD)',
    ci varchar(255) not null COMMENT 'CI(Connection Information) 연계정보',
    di varchar(255) not null COMMENT 'DI(Duplication Information) 중복 가입 확인 정보',
    register_type tinyint(1) not null COMMENT '가입방식 1: native 가입 / 2: social 가입',
)