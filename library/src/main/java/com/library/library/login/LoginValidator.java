package com.library.library.login;

import com.library.library.domain.LoginValidateResult;
import com.library.library.entity.User;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/9 15:06
 */
public interface LoginValidator {

    /**
     * 执行顺序
     * @return
     */
    default int order(){
        return 0;
    }

    /**
     * 登录验证
     * @param user
     * @return
     */
    LoginValidateResult validate(User user);
}
