package com.library.library.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.library.entity.Book;
import com.library.library.entity.User;
import com.library.library.sys.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: chenmingzhe
 * @Date: 2020/4/12 16:57
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, User> {
}
