package com.library.library.service;

import com.library.library.entity.Menu;
import com.library.library.mapper.MenuMapper;
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
public class MenuService extends  ServiceImpl<MenuMapper, Menu> {

    public List<Menu> listAllMenu(){
        return this.baseMapper.selectAllMenus();
    }
}
