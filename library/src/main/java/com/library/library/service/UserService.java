package com.library.library.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.library.entity.User;
import com.library.library.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
     * 修改用户信息
     * @param user
     */
    public void updateUser(User user) {
        // 更新修改时间
//        user.setUpdatedate(LocalDateTime.now()
//                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
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
