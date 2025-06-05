package com.reservation.authentication.domain.principal;

import com.reservation.authentication.domain.type.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface SecurityPrincipal extends UserDetails {
    Authority getAuthority();

    default Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(getAuthority().getRole());
    }

    default String getUsername() {
        return "";
    }

    default String getPassword() {
        return "";
    }

    default boolean isAccountNonExpired() {
        return false;
    }

    default boolean isAccountNonLocked() {
        return false;
    }

    default boolean isCredentialsNonExpired() {
        return false;
    }

    default boolean isEnabled() {
        return false;
    }
}
