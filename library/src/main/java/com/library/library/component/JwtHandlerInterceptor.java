package com.library.library.component;

import com.library.library.constant.LibraryConstants;
import com.library.library.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: chenmingzhe
 * @Date: 2020/2/5 11:40
 */
@Component
public class JwtHandlerInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(JwtHandlerInterceptor.class);

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader(LibraryConstants.TOKEN_HEADER_NAME);
    if (authHeader != null && authHeader.startsWith(LibraryConstants.TOKEN_HEAD)) {
        String authToken = authHeader.substring(LibraryConstants.TOKEN_HEAD.length());
        String userName = jwtUtil.getUserNameFromToken(authToken);
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if (jwtUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        logger.info("拦截器生效了{}",userName);
    }
        return true;
    }

}
