package com.library.library.controller;

import com.library.library.entity.Book;
import com.library.library.domain.Result;
import com.library.library.entity.User;
import com.library.library.service.BorrowService;
import com.library.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenmingzhe
 * @since 2019-12-23
 */
@RestController
@CrossOrigin
@RequestMapping("/borrow")
public class BorrowController   {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 用户图书
     * @param isbor
     * @return
     */
    @GetMapping("/userBorBook")
    public Result<List<Book>>
        selectUserBorBook(@RequestParam(required=false) String isbor) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.getFailure("请先登录！");
        }
        return Result.getSuccess(borrowService.userBorBookList(user.getId(), isbor));
    }

    /**
     * 获取用户借阅记录
     * @return
     */
    @GetMapping("/userBorrowBook")
    public Result<List<Book>> userBorrowBook() {
        return Result.getSuccess(borrowService.listUserBorBook());
    }

    /**
     * 用户图书
     * @param name 用户名
     * @return
     */
    @GetMapping("/userBorBookByName")
    public Result<List<Book>>
            selectUserBorBookByName(@RequestParam String name) {
        User user = userService.lambdaQuery()
                .eq(User::getUsername, name.trim()).one();
        return Result.getSuccess(borrowService.userBorBookList(user.getId(), null));
    }

    /**
     * 借书
     * @param bookId
     * @return
     */
    @RequestMapping("/borrowBook")
    public Result<String> borrowBook(@RequestParam int bookId) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            return Result.getFailure("请先登录！");
        }
        borrowService.userBorrowBook(user.getId(),bookId);
        return Result.getSuccess("借阅成功！");
    }

    /**
     * 还书
     * @param bookId
     * @return
     */
    @RequestMapping("/returnBook")
    public Result<String> returnBook(@RequestParam String num, @RequestParam String username) {
        User user = userService.lambdaQuery()
                .eq(User::getUsername, username.trim()).one();
        borrowService.userRetrunBook(user.getId(), num);
        return Result.getSuccess("还书成功！");
    }

}

