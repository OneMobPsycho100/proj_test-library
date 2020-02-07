package com.library.library.service;

import com.library.library.entity.Userrole;
import com.library.library.mapper.UserroleMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenmingzhe
 * @since 2020-02-07
 */
@Service
public class UserroleService extends  ServiceImpl<UserroleMapper, Userrole> {

    public List<Userrole> listUserRoleByUserId(int userId) {
        return this.baseMapper.listUserRoleByUserId(userId);
    }

}
