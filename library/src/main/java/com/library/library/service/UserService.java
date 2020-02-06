package com.library.library.service;

import com.alibaba.druid.sql.visitor.functions.If;
import com.library.library.constant.LibraryConstants;
import com.library.library.domain.UserCtmDetails;
import com.library.library.entity.User;
import com.library.library.mapper.UserMapper;
import com.library.library.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenmingzhe
 * @since 2019-12-23
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     * @param user
     */
    public void saveUser(User user) {
        // 普通用户
        user.setUsertype("1");
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedate(date);
        user.setUpdatedate(date);
        this.save(user);
    }

    /**
     * 验证用户合法性
     * @param user
     * @return
     */
    public String login(User user) {
        if (user == null) {
          throw new RuntimeException("账号或密码错误！");
        }
        UserDetails userDetails = this.loadUserByUsernameAndType(user.getUsername(), user.getUsertype());
        if(passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            String token = jwtUtil
                    .createJWT(userDetails.getUsername(), userDetails.getUsername(), LibraryConstants.ROLE_ADMIN);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return token;
        }else {
            throw new RuntimeException("账号或密码错误！");
        }
    }

    public UserDetails loadUserByUsernameAndType(String userName, String userType) {
        User userLogin = this.lambdaQuery().eq(User::getUsername, userName)
                .eq(User::getUsertype, userType)
                .one();
        if (userLogin == null) {
            throw new RuntimeException("账号或密码错误！");
        }
        return new UserCtmDetails(userLogin);
    }

    /**
     * 修改用户信息
     * @param user
     */
    public void updateUser(User user) {
        // 更新修改时间
        user.setUpdatedate(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        this.saveOrUpdate(user);
    }

    /**
     * 删除用户
     * @param userid
     */
    public void deleteUser(int userid) {
        this.removeById(userid);
    }
}
