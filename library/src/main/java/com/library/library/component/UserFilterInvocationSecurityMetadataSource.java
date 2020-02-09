package com.library.library.component;

import com.library.library.constant.LibraryConstants;
import com.library.library.entity.Menu;
import com.library.library.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 拦截请求 解析请求地址需要的用户角色
 * @Author: chenmingzhe
 * @Date: 2020/2/7 20:41
 */
public class UserFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuService menuService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        String url = "";
        if (requestUrl.contains("?")) {
             url = requestUrl.substring(0, requestUrl.indexOf("?"));
        } else {url = requestUrl;}
        List<Menu> menus = menuService.listAllMenu();
        List<ConfigAttribute> collect = new ArrayList<>();
        for (Menu m : menus) {
            if (antPathMatcher.match(url, m.getPath().trim())) {
                SecurityConfig securityConfig = new SecurityConfig(m.getName().trim());
                collect.add(securityConfig);
            }
        }
        return collect.size()>0 ? collect : SecurityConfig
                .createList(LibraryConstants.ROLE_LOGIN);
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
