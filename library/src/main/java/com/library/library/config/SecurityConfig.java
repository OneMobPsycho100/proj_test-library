package com.library.library.config;

import com.library.library.component.*;
import com.library.library.properties.ExcludeUrlsProperties;
import com.library.library.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/3 16:16
 */
@Configuration
@EnableWebSecurity
@Order(-1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        excludeUrls().getExclude().forEach(url ->{
            registry.antMatchers(url).permitAll();
        });
        //允许跨域请求的OPTIONS请求
        registry.antMatchers(HttpMethod.OPTIONS)
                .permitAll();

        registry.and()
                .authorizeRequests()
               // .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(rolePathAccessDecisionManager());
                        o.setSecurityMetadataSource(userFilterInvocationSecurityMetadataSource());
                        return o;
                    }
                })
                .and()
                .csrf()
                .disable()
                .formLogin()
                .loginPage("/user/login")
                .and()
                .logout()
                .logoutUrl("/user/logout")
                // 成功退出登录
                .logoutSuccessHandler(restLogoutSuccessHandler())
                .and()
                // 自定义权限拒绝处理类
                .exceptionHandling()
                // 无权限
                .accessDeniedHandler(restfulAccessDeniedHandler())
                // 登录失效
                .authenticationEntryPoint(restAuthenticationEntryPoint());

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public ExcludeUrlsProperties excludeUrls(){
        return new ExcludeUrlsProperties();
    }

    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() { return new RestfulAccessDeniedHandler();}

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {return new RestAuthenticationEntryPoint();}

    @Bean
    public RestLogoutSuccessHandler restLogoutSuccessHandler() {return  new RestLogoutSuccessHandler();}

    @Bean
    public UserFilterInvocationSecurityMetadataSource userFilterInvocationSecurityMetadataSource() {return new UserFilterInvocationSecurityMetadataSource();}

    @Bean
    public RolePathAccessDecisionManager rolePathAccessDecisionManager() {return new RolePathAccessDecisionManager();}

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();}

    @Bean
    public JwtUtil jwtUtil() { return new JwtUtil();}

}
