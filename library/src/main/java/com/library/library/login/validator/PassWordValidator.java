package com.library.library.login.validator;

import com.library.library.constant.LibraryConstants;
import com.library.library.domain.LoginValidateResult;
import com.library.library.entity.User;
import com.library.library.login.LoginValidator;
import com.library.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** 密码验证
 * @Author: chenmingzhe
 * @Date: 2020/2/9 16:22
 */
public class PassWordValidator implements LoginValidator {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int order() {
        return 2;
    }

    @Override
    public LoginValidateResult validate(User user) {
        User userLogin = userService.lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .one();
        // 第二天自动解锁
        if (!StringUtils.isEmpty(userLogin.getPwdlocktime())) {
            LocalDate pwdLockTime = LocalDate
                    .parse(userLogin.getPwdlocktime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            if (LocalDate.now().isAfter(pwdLockTime)) {
                userService.lambdaUpdate().set(User::getPwdlocktime,null)
                        .set(User::getPwderrtimes,0).update();
            }
        }
        if (LibraryConstants.PWD_ERROR_TIMES < userLogin.getPwderrtimes()) {
            return LoginValidateResult.getFailure("密码错误次数已达上限");
        }
        // 验证密码正确性
        if (passwordEncoder.matches(user.getPassword(), userLogin.getPassword())) {
                return LoginValidateResult.getSuccess();
        }else {
            // 密码错误 次数加一
            userService.lambdaUpdate()
                    .set(User::getPwderrtimes,userLogin.getPwderrtimes()+1).update();
            return LoginValidateResult.getFailure("账号或密码错误！");
        }
    }
}
