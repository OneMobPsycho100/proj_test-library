package com.library.library.component;

import com.library.library.annotation.Role;
import com.library.library.constant.LibraryConstants;
import com.library.library.utils.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/6 16:10
 */
@Aspect
@Component
public class RoleAspect {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtUtil jwtUtil;

    @Pointcut("@annotation(com.library.library.annotation.Role)")
    public void roleIntercept() {}

    @Before("roleIntercept()")
    public void before(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                Role annotation = signature.getMethod().getAnnotation(Role.class);
                if (LibraryConstants.ROLE_ADMIN.equals(annotation.value())) {
                    String authToken = request.getHeader(LibraryConstants.TOKEN_HEADER_NAME)
                            .substring(LibraryConstants.TOKEN_HEAD.length());
                    String role = jwtUtil.getUserRoleFromToken(authToken);
                    if (!role.equals(annotation.value())) {
                        throw new RuntimeException("用户权限不足！");
            }
        }
    }
}
