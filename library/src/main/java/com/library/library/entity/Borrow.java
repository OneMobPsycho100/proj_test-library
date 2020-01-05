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
public class Borrow  implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "borrowid", type = IdType.AUTO)
    private Integer borrowid;

    @TableField("userid")
    private Integer userid;

    @TableField("bookid")
    private Integer bookid;

    /**
     * 借阅时间
     */
    @TableField("brodate")
    private String brodate;

    /**
     * 归还时间
     */
    @TableField("retdate")
    private String retdate;

    /**
     * 是否归还0未，1已
     */
    @TableField("isbro")
    private String isbro;

    @TableField(exist = false)
    private String bookname;

    @TableField(exist = false)
    private String image;

    public Integer getBorrowid() {
        return borrowid;
    }

    public void setBorrowid(Integer borrowid) {
        this.borrowid = borrowid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public String getBrodate() {
        return brodate;
    }

    public void setBrodate(String brodate) {
        this.brodate = brodate;
    }

    public String getRetdate() {
        return retdate;
    }

    public void setRetdate(String retdate) {
        this.retdate = retdate;
    }

    public String getIsbro() {
        return isbro;
    }

    public void setIsbro(String isbro) {
        this.isbro = isbro;
    }

    @Override
    public String toString() {
        return "Borrow{" +
        "borrowid=" + borrowid +
        ", userid=" + userid +
        ", bookid=" + bookid +
        ", brodate=" + brodate +
        ", retdate=" + retdate +
        ", isbro=" + isbro +
        "}";
    }
}
