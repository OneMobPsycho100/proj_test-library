package com.library.library.login;

import com.library.library.login.custom.CustomLoginService;
import com.library.library.login.validator.PassWordValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/10 13:01
 */
@Configuration
public class CustomLoginAutoConfigurer {

    /**
     * 密码验证
     * @return
     */
	@Bean
	public PassWordValidator passWordValidator(){
		return new PassWordValidator();
	}

    /**
     * 手机号与邮箱登录
     * @return
     */
    @Bean
    public LoginService customLoginService(){
        return new CustomLoginService();
    }
}
