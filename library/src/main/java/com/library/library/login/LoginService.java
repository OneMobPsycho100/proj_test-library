package com.library.library.login;

import com.library.library.entity.User;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/9 15:21
 */
public interface LoginService {

    /**
     * 登录
     * @param user
     * @return
     */
    String login(User user);
}
