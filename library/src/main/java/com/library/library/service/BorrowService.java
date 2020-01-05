package com.library.library.service;

import com.library.library.entity.Book;
import com.library.library.entity.Borrow;
import com.library.library.mapper.BorrowMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class BorrowService extends ServiceImpl<BorrowMapper, Borrow> {

    @Autowired
    private BookService bookService;

    /**
     * 查询用户借阅的图书
     * @param id
     * @return
     */
    public List<Book> userBorBookList(int id, String isbor) {
           return this.baseMapper.listBorBook(id, isbor);
    }

    /**
     * 借书
     * @param id
     * @param bookId
     */
    public void userBorrowBook(int id, int bookId) {
        String status = bookService.getById(bookId).getStatus();
        if("0".equals(status)) {
            throw new RuntimeException("该图书已被借阅");
        }
        Borrow borrow = new Borrow();
        borrow.setBookid(bookId);
        borrow.setUserid(id);
        //未归还
        borrow.setIsbro("0");
        borrow.setBrodate(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        // 修改图书为已借出状态
        bookService.lambdaUpdate()
                .set(Book::getStatus, "0")
                .eq(Book::getBookid,bookId)
                .update();
        this.save(borrow);
    }

    /**
     * 还书
     * @param id
     * @param num
     */
    public void userRetrunBook(int id, String num) {
        // 获取图书id 根据编号
        int bookId = bookService.lambdaQuery()
                .eq(Book::getNum,num.trim()).one()
                .getBookid();
        // 修改状态为已归还
        this.lambdaUpdate()
                .set(Borrow::getRetdate,LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .set(Borrow::getIsbro, "1")
                .eq(Borrow::getUserid, id)
                .eq(Borrow::getBookid,bookId)
                .update();
        /* 修改图书为未借出状态 */
        bookService.lambdaUpdate()
                .set(Book::getStatus, "1")
                .eq(Book::getBookid,bookId)
                .update();
    }

    /**
     * 查询用户借阅记录
     * @return
     */
    public List<Book> listUserBorBook() {
        return this.baseMapper.listUsersBorBook();
    }
}
