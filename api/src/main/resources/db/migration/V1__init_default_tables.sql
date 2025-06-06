-- create schema
CREATE SCHEMA user;
CREATE SCHEMA market;
CREATE SCHEMA reservation;
CREATE SCHEMA common;

-- create user
CREATE TABLE user.role (
    idx int(11) unsigned auto_increment COMMENT '식별번호' primary key,
    authority enum('USER', 'ADMIN', 'CONSOLE') not null COMMENT '권한'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='유저 종류';

CREATE TABLE user.user (
    idx int(11) unsigned auto_increment COMMNET '식별번호' primary key,
    role_idx int(11) unsigned not null COMMENT '권한 식별번호',
    id varchar(50) not null COMMENT '로그인 아이디',
    password varchar(100) not null COMMENT '비밀번호 (단방향 암호화)',
    is_active tinyint(1) not null default 1 COMMENT '유저 활성화 여부',
    reg_datetime datetime not null default current_time COMMENT '등록일시',
    mod_datetime datetime null COMMENT '수정일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='유저 공통 정보';

CREATE UNIQUE INDEX user_id_unique ON user.user(id);
CREATE INDEX user_reg_datetime_index ON user.user(reg_datetime);

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
    reg_datetime datetime not null default current_time COMMENT '등록일시',
    mod_datetime datetime null COMMENT '수정일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='일반 유저 부가정보';

CREATE TABLE user.admin_identify (
    user_idx int(11) unsigned not null COMMENT '유저 식별번호' primary key,
    market_idx int(11) unsigned not null COMMENT '상점 식벌번호',
    department_idx int(11) usgined null COMMENT '부서 식별번호',
    position varchar(20) null COMMENT '직급 명칭',
    identification_number varchar(50) null COMMENT '사번',
    name varchar(50) not null COMMENT '이름(양방향 암호화)',
    email varchar(50) not null COMMENT '이메일',
    reg_datetime datetime not null default current_time COMMENT '등록일시',
    mod_datetime datetime null COMMENT '수정일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='상점 관리자 부가정보';

CREATE TABLE user.console_identify (
    user_idx int(11) unsigned not null COMMENT '유저 식별번호' primary key,
    department_idx int(11) not null COMMENT '부서 식별번호',
    position varchar(20) unsigned not null COMMENT '직급 명칭',
    name varchar(50) not null COMMENT '이름(양방향 암호화)',
    email varchar(50) not null COMMENT '이메일',
    reg_datetime datetime not null default current_time COMMENT '등록일시',
    mod_datetime datetime null COMMENT '수정일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='백오피스 관리자 부가정보';

CREATE TABLE user.department (
    idx int(11) unsigned auto_increment COMMENT '부서 식별번호' primary key,
    market_idx int(11) unsgiend not null COMMENT '관리 상점 인덱스 번호 / 0: 백오피스',
    name varchar(20) not null COMMENt '부서명',
    menu_authority_idx int(11) unsgiend not null COMMENT '메뉴권한 식별번호',
    reg_datetime datetime not null default current_time COMMENT '등록일시',
    reg_idx int(11) unsigned not null default 0 COMMENT '등록자 식별번호 / 0: 시스템',
    mod_datetime datetime null default COMMENT '수정일시',
    mod_idx int(11) unsigned null COMMENT '등록자 식별번호 / 0: 시스템'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='부서정보';

CREATE TABLE common.menu (
    idx int(11) unsigned auto_increment COMMENT '메뉴 식별번호' primary key,
    name varchar(50) not null COMMENT '메뉴명',
    is_active tinyint(1) not null default 1 COMMENT '메뉴 활성 여부',
    reg_datetime datetime not null default current_time COMMENT '등록일시',
    mod_datetime datetime null COMMENT '수정일시'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='메뉴 정보';

CREATE TABLE user.menu_authority (
    idx int(11) unsgiend auto_increment COMMENT '메뉴권한 식별번호' primary key,
    market_idx int(11) unsigned not null COMMENT '관리 상점 식별번호 / 0: 백오피스',
    name varchar(50) not null COMMENT '권한 명칭',
    reg_datetime datetime not null default current_time COMMENT '등록일시',
    reg_idx int(11) not null default 0 COMMENT '등록자 식별번호 / 0: 시스템',
    mod_datetime datetime COMMENT '수정일시',
    mod_idx int(11) COMMENT '수정자 식별번호 / 0: 시스템'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='메뉴 권한 정보';

CREATE TABLE user.permitted_menu_authority (
    idx int(11) unsgiend auto_increment COMMENT '접근 가능 메뉴 식별번호' primary key,
    menu_authority_idx int(11) unsigned not null COMMENT '메뉴 권한 식별번호',
    menu_idx int(11) unsigned not null COMMENT '접근 가능 메뉴 식별번호',
    reg_datetime datetime not null default current_time COMMENT '등록일시',
    reg_idx int(11) unsigned not null default 0 COMMENT '등록자 식별번호 / 0: 시스템',
    mod_datetime datetime null COMMENT '수정일시',
    mod_idx int(11) unsigned COMMENT '수정자 식별번호 / 0: 시스템'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='권한별 접근 가능 메뉴 정보';

CREATE UNIQUE INDEX permitted_menu_authority_unique_index ON user.permitted_menu_authority(menu_authority_idx, menu_idx)

CREATE TABLE market.market (
    idx int(11) unsigned auto_increment COMMENT '상점 식별번호' primary key,
    name varchar(50) not null COMMENT '상점 명칭',
    identification_number varchar(20) not null COMMENT '사업자 번호',
    representative_name varchar(50) not null COMMENT '대표자 명',
    representative_phone varchar(20) not null COMMENT '대표번호',
    region_code char(2) not null COMMENT '광역/특별시 및 도 코드',
    city_district_code char(3) not null COMMENT '시/구/군 코드',
    town_code char(3) not null COMMENT '읍/면/동 코드',
    village_code char(2) not null COMMENT '리 코드',
    land_address varchar(255) not null COMMENT '지번 주소',
    road_address varchar(255) null COMMENT '도로명 주소',
    latitude decimal(10, 8) not null COMMENT '위도',
    longitude decimal (10, 8) not null COMMENT '경도',
    reg_datetime datetime not null default current_time COMMENT '등록일시',
    reg_idx int(11) not null COMMENT '등록자 식별번호',
    mod_datetime datetime COMMENT '수정일시',
    mod_idx int(11) COMMENT '수정자 식별번호'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='상점 기본정보';
