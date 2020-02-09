package com.library.library.login.custom;

import com.library.library.domain.LibraryException;
import com.library.library.entity.User;
import com.library.library.login.impl.AbstractLoginServiceImpl;
import com.library.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 登录方式扩展
 * @Author: chenmingzhe
 * @Date: 2020/2/9 15:38
 */
public class CustomLoginService extends AbstractLoginServiceImpl {

    @Autowired
    private UserService userService;

    /**
     * 邮箱或手机号登录
     * @param user
     * @return
     */
    @Override
    public String login(User user) {
        if (user == null) {
            throw new LibraryException("账号和密码不能为空!");
        }
        String account = user.getUsername();
        User userLogin = userService.lambdaQuery()
                .eq(User::getMobile,account)
                .or(u->u.eq(User::getEmail,account))
                .one();
        if (userLogin != null) {
            user.setUsername(userLogin.getUsername());
        }
        return super.login(user);
    }
}
