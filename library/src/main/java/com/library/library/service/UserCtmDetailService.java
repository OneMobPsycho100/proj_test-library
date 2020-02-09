package com.library.library.service;

import com.library.library.domain.UserCtmDetails;
import com.library.library.entity.User;
import com.library.library.entity.Userrole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/9 10:35
 */
public class UserCtmDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserroleService userroleService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User userLogin = userService.lambdaQuery().eq(User::getUsername, name)
                .one();
        if (userLogin == null) {
            throw new RuntimeException("账号或密码错误！");
        }
        List<Userrole> userRoleList = userroleService.listUserRoleByUserId(userLogin.getId());
        List<SimpleGrantedAuthority> roleNames = userRoleList.stream()
                .map(ur -> new SimpleGrantedAuthority(ur.getName()))
                .collect(Collectors.toList());
        return new UserCtmDetails(userLogin,roleNames);
    }
}
