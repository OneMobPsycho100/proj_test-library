package com.library.library.component;

import com.library.library.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 拦截请求 解析请求地址需要的用户角色
 * @Author: chenmingzhe
 * @Date: 2020/2/7 20:41
 */
public class UserFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return menuService.listAllMenu()
                .stream()
                .filter(m -> !antPathMatcher.match(m.getPath(), requestUrl))
                .map(m -> new SecurityConfig(m.getName().trim()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
