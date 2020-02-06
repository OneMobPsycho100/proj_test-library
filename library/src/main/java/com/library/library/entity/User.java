package com.library.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
/**
 * <p>
 * 
 * </p>
 *
 * @author chenmingzhe
 * @since 2019-12-23
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户类型0管理员，1普通用户
     */
    @TableField("usertype")
    private String usertype;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 备注
     */
    @TableField("base")
    private String base;

    /**
     * 头像
     */
    @TableField("headfile")
    private String headfile;

    /**
     * 创建日期
     */
    @TableField("createdate")
    private String createdate;

    /**
     * 修改日期
     */
    @TableField("updatedate")
    private String updatedate;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getHeadfile() {
        return headfile;
    }

    public void setHeadfile(String headfile) {
        this.headfile = headfile;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", usertype=" + usertype +
        ", mobile=" + mobile +
        ", base=" + base +
        ", headfile=" + headfile +
        ", createdate=" + createdate +
        ", updatedate=" + updatedate +
        "}";
    }
}
