package com.library.library.component;

import com.library.library.constant.LibraryConstants;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 *请求地址角色与用户角色匹配
 * @Author: chenmingzhe
 * @Date: 2020/2/7 21:18
 */
public class RolePathAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute c : collection) {
            String attribute = c.getAttribute();
            if (LibraryConstants.ROLE_LOGIN.equals(attribute)) {
                if (authentication instanceof AnonymousAuthenticationToken) {
//                   throw new RuntimeException("请先登录！");
//                } else {
                    return;
                }
            }
            for (GrantedAuthority a : authorities) {
                if (a.getAuthority().equals(attribute)) {
                    return;
                }
            }
        }

        throw new RuntimeException("用户权限不足！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
