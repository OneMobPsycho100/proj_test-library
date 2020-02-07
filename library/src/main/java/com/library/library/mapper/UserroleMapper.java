package com.library.library.mapper;

import com.library.library.entity.Userrole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenmingzhe
 * @since 2020-02-07
 */
public interface UserroleMapper extends  BaseMapper<Userrole> {

    /**
     * 根据用户id获取用户的权限
     * @param userId
     * @return
     */
    @Select("SELECT r.`name`,ur.userid FROM `role` r,userrole ur WHERE r.id = ur.rid and ur.userid=#{userId}")
    List<Userrole> listUserRoleByUserId(int userId);
}
