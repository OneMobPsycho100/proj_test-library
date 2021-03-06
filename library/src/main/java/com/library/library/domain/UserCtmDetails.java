package com.library.library.domain;

import com.library.library.entity.Role;
import com.library.library.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/5 16:41
 */
public class UserCtmDetails implements UserDetails {

    private User user;

    private Collection<? extends GrantedAuthority> roleNames;

    public UserCtmDetails(User user, Collection<? extends GrantedAuthority> roleNames) {
        this.user = user;
        this.roleNames = roleNames;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleNames;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Collection<? extends GrantedAuthority> getRoleNames() {
        return roleNames;
    }
}
