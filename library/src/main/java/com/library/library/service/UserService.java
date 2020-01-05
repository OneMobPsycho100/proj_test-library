package com.library.library.service;

import com.alibaba.druid.sql.visitor.functions.If;
import com.library.library.entity.User;
import com.library.library.mapper.UserMapper;
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

    /**
     * 用户注册
     * @param user
     */
    public void saveUser(User user) {
        // 普通用户
        user.setUsertype("1");
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        user.setCreatedate(date);
        user.setUpdatedate(date);
        this.save(user);
    }

    /**
     * 验证用户合法性
     * @param user
     * @return
     */
    public User login(User user) {
        if (user == null) {
          throw new RuntimeException("账号或密码错误！");
        }
        User userLogin = this.lambdaQuery().eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword())
                .eq(User::getUsertype, user.getUsertype())
                .one();
        if (userLogin == null) {
            throw new RuntimeException("账号或密码错误！");
        }
        return userLogin;
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
