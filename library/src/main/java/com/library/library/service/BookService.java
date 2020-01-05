package com.library.library.service;

import com.library.library.entity.Book;
import com.library.library.mapper.BookMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenmingzhe
 * @since 2019-12-23
 */
@Service
public class BookService extends ServiceImpl<BookMapper, Book> {

    /**
     * 添加图书
     * @param book
     */
    public void addBook(Book book) {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        book.setCreatedate(date);
        book.setUpdatedate(date);
        // 未借出
        book.setStatus("1");
        this.save(book);
    }

    /**
     * 修改图书
     * @param book
     */
    public void updateBook(Book book) {
        book.setUpdatedate(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        this.save(book);
    }

    /**
     * 查询未借出的图书
     * @return
     */
    public List<Book> listBookByStatus() {
        return this.lambdaQuery().eq(Book::getStatus, 1).list();
    }

    /**
     * 多条件查询图书
     * @param book
     * @return
     */
    public List<Book> listBookByCon(String content) {
        // 去空格
        content = content.trim();
        return this.lambdaQuery().like(Book::getBookname, content)
                .or().eq(Book::getAuthor, content)
                .or().eq(Book::getPress, content)
                .list();
    }

    /**
     * 查询人气图书
     * @return
     */
    public List<Book> listBookPopular() {
        return this.baseMapper.listBookPop();
    }

    /**
     * 推荐图书
     * @return
     */
    public List<Book> listRecommendBook() {
        return this.baseMapper.listRecommendBook();
    }
}
