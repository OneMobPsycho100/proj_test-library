package com.library.library.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.library.library.annotation.Role;
import com.library.library.constant.LibraryConstants;
import com.library.library.domain.Result;
import com.library.library.entity.User;
import com.library.library.login.LoginService;
import com.library.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
@RequestMapping("/user")
public class UserController   {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result<String> userRegister(@RequestBody User user) {
        userService.saveUser(user);
        return Result.getSuccess("注册成功！");
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @RequestMapping("/login")
    public Result<String> userLogin(@RequestBody User user) {
        return Result.getSuccess(loginService.login(user));
    }

    /**
     *退出登陆
     * @return
     */
//    @RequestMapping("/logout")
//    public Result<String> userLogout() {
//        SecurityContextHolder.getContext().setAuthentication(null);
//        request.getSession().removeAttribute("user");
//        return Result.getSuccess("已退出登陆！");
//    }

    /**
     * 用户修改信息
     * @param user
     * @return
     */
    @PutMapping("/userUpdate")
    public Result<String> userUpdate(@RequestBody User user) {
        userService.updateUser(user);
        HttpSession session = request.getSession();
        // 修改之后更新session
        session.removeAttribute("user");
        session.setAttribute("user",userService.getById(user.getId()));
        return Result.getSuccess("修改成功！");
    }

    /**
     * 修改密码
     * @param oldPass
     * @param newPass
     * @return
     */
    @RequestMapping("/passwordUpdate")
    public Result<?> passwordUpdate(@RequestParam("oldPass") String oldPass,
                                    @RequestParam("newPass") String newPass) {
        User user = (User) request.getSession()
                .getAttribute("user");

        if(oldPass.equals(user.getPassword())) {
           user.setPassword(newPass);
           userService.saveOrUpdate(user);
        }

        return  Result.getSuccess("修改成功");
    }

    /**
     * 删除用户
     * @param userid
     * @return
     */
    @DeleteMapping("/userDelete")
    public Result<String> userUpdate(@RequestParam int userid) {
        userService.deleteUser(userid);
        return Result.getSuccess("删除成功！");
    }

    /**
     * 获取用户信息
     * @param
     * @return
     */
    @RequestMapping("/getUser")
    public Result<User> getUser() {
        User user = (User) request.getSession()
                .getAttribute("user");
        return Result.getSuccess(user);
    }

    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    @RequestMapping("/getUserByName")
    public Result<User> getUserByName(@RequestParam String name) {
        User user = userService.lambdaQuery()
                .eq(User::getUsername, name.trim()).one();
       return Result.getSuccess(user);
    }

    /**
     * 用户列表 分页
     * @param curPage
     * @param pageSize
     * @return
     */
    @Role(LibraryConstants.ROLE_ADMIN)
    @RequestMapping("/listUser")
    public Result<Page<User>> listAllUser(@RequestParam("curPage") int curPage,
                                          @RequestParam("pageSize") int pageSize) {
        return Result.getSuccess(userService.lambdaQuery().page(new Page<>(curPage,pageSize)));
    }
}

