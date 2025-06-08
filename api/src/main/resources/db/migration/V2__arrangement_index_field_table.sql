-- 유저 권한 테이블 유니크 인덱스 추가
CREATE UNIQUE INDEX role_authority_unique_index ON user.role(authority);

-- 불필요한 테이블 및 연관 필드 제거 (권한 관련)
-- 권한 설정은 부서별로만 지정할 수 있도록 = 개별 권한은 아직 불필요
-- 개별권한이 구현되려고 하면 유저와 관련된 연관테이블이 필요하기 때문에 향후 고도화 할 때 고려
-- 부서별 권한, 개별 권한에 대한 세팅으로 상관관계 및 유효성 체크 필요
-- 현 시점은 부서별 권한만 지정할 수 있도록
DROP TABLE user.menu_authority;
ALTER TABLE user.department DROP COLUMN menu_authority_idx;
ALTER TABLE user.permitted_menu_authority CHANGE menu_authority_idx department_idx int unsigned NOT NULL COMMENT '부서 식별번호';
ALTER TABLe user.permitted_menu_authority RENAME user.department_menu_authority;