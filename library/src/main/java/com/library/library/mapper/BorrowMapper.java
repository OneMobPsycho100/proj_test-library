package com.library.library.mapper;

import com.library.library.entity.Book;
import com.library.library.entity.Borrow;
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
public interface BorrowMapper extends  BaseMapper<Borrow> {

    /**
     *查询用户借阅图书列表
     * @param id userid
     * @param isbor 0未归还，1已归还
     */
    @Select("<script> select bk.* ,u.username,bo.retdate,bo.brodate,bo.isbro from borrow bo" +
            "        left join book bk on bo.bookid = bk.bookid" +
            "        left join user u on u.id = bo.userid where" +
            "        bo.userid = #{id}" +
            "        <if test='isbor!=null'>" +
            "         and bo.isbor = #{isbor}" +
            "        </if> order by bk.bookid asc </script>")
    List<Book> listBorBook(int id, String isbor);


    /**&
     * 查询用户借阅记录
     * @return
     */
    @Select("<script> select bk.* ,u.username,bo.retdate,bo.brodate,bo.isbro from borrow bo" +
            "        left join book bk on bo.bookid = bk.bookid" +
            "        left join user u on u.id = bo.userid" +
            "        order by bo.brodate desc </script>")
    List<Book> listUsersBorBook();
}
