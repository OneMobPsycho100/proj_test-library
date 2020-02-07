package com.library.library.mapper;

import com.library.library.entity.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 获取所有的菜单信息
     * @return
     */
    @Select("SELECT m.path,r.`name` FROM menu m LEFT JOIN menurole mr on mr.mid = m.id LEFT JOIN role r on r.id = mr.rid")
    List<Menu> selectAllMenus();
}
