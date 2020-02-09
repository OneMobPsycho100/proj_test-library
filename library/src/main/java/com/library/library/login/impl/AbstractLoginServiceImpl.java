package com.library.library.login.impl;

import com.library.library.constant.LibraryConstants;
import com.library.library.domain.LibraryException;
import com.library.library.domain.LoginValidateResult;
import com.library.library.domain.UserCtmDetails;
import com.library.library.entity.User;
import com.library.library.entity.Userrole;
import com.library.library.login.LoginService;
import com.library.library.login.LoginValidator;
import com.library.library.service.UserService;
import com.library.library.service.UserroleService;
import com.library.library.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/9 15:21
 */
public class AbstractLoginServiceImpl implements LoginService, SmartLifecycle {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserroleService userroleService;
    @Autowired
    private ApplicationContext applicationContext;

    private List<LoginValidator> loginValidators = new ArrayList<>();

    @Override
    public void start() {
        // 给按设定好的order给Validator排序
        String[] beanNames = applicationContext.getBeanNamesForType(LoginValidator.class);
        for (String beanName : beanNames) {
            loginValidators.add(applicationContext.getBean(beanName,LoginValidator.class));
        }
        loginValidators = loginValidators.stream()
                .sorted(Comparator.comparing(LoginValidator::order)).collect(Collectors.toList());
    }

    @Override
    public String login(User user) {
        // 开始登录校验
        Iterator<LoginValidator> iterator = loginValidators.iterator();
        LoginValidateResult result;
        while (iterator.hasNext()) {
            result = iterator.next().validate(user);
            if (!result.isOk()) {
                throw new LibraryException(result.getMsg());
            }
        }
        UserDetails userDetails = this.loadUserByUsername(user.getUsername());
        String token = jwtUtil
                .createJWT(userDetails.getUsername(), userDetails.getUsername(), LibraryConstants.ROLE_ADMIN);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return token;
    }

    private UserDetails loadUserByUsername(String userName) {
        User userLogin = userService.lambdaQuery()
                .eq(User::getUsername, userName)
                .one();
        List<Userrole> userRoleList = userroleService.listUserRoleByUserId(userLogin.getId());
        List<SimpleGrantedAuthority> roleNames = userRoleList.stream()
                .map(ur -> new SimpleGrantedAuthority(ur.getName()))
                .collect(Collectors.toList());
        return new UserCtmDetails(userLogin,roleNames);
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
