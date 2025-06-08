package com.reservation.api.user.enitty;

import com.reservation.api.user.enitty.type.AuthorityType;
import com.reservation.api.utils.DatabaseUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(catalog = DatabaseUtils.USER_SCHEMA, name = "role")
@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private AuthorityType authority;

    public RoleEntity(Long idx, AuthorityType authority) {
        this.idx = idx;
        this.authority = authority;
    }
}
