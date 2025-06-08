package com.reservation.api.user.enitty;

import com.reservation.api.common.entity.MenuEntity;
import com.reservation.api.common.entity.meta.BaseExecutorEntity;
import com.reservation.api.utils.DatabaseUtils;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Table(catalog = DatabaseUtils.USER_SCHEMA, name = "department_menu_authority")
@Entity
public class DepartmentMenuAuthorityEntity extends BaseExecutorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "department_idx")
    private DepartmentEntity department;

    @ManyToOne
    @JoinColumn(name = "menu_idx")
    private MenuEntity menu;

    public static DepartmentMenuAuthorityEntity create(DepartmentEntity department, MenuEntity menu, LocalDateTime regDatetime, Long regIdx) {
        return new DepartmentMenuAuthorityEntity(department, menu, regDatetime, regIdx);
    }

    private DepartmentMenuAuthorityEntity(DepartmentEntity department, MenuEntity menu, LocalDateTime regDatetime, Long regIdx) {
        this(null, department, menu, regDatetime, regIdx);
    }

    private DepartmentMenuAuthorityEntity(Long idx, DepartmentEntity department, MenuEntity menu, LocalDateTime regDatetime, Long regIdx) {
        super(regDatetime, regIdx);
        this.idx = idx;
        this.department = department;
        this.menu = menu;
    }
}
