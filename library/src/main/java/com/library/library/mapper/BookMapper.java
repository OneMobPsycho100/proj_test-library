package com.library.library.mapper;

import com.library.library.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenmingzhe
 * @since 2019-12-23
 */
public interface BookMapper extends BaseMapper<Book> {
    /**
     * 查询借阅次数最多的图书 4条
     * @return
     */
    @Select("SELECT book.*, COUNT(*) as nu from borrow INNER JOIN book " +
            "WHERE book.bookid = borrow.bookid  GROUP BY bookid  order by nu desc LIMIT 4")
    List<Book> listBookPop();

    /**
     * 随机获取4条
     * @return
     */
    @Select("SELECT * FROM book ORDER BY RAND() LIMIT 4")
    List<Book> listRecommendBook();
}
