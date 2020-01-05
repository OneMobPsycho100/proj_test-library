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
public class Book  implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "bookid" ,type = IdType.AUTO)
    private Integer bookid;

    /**
     * 书名
     */
    @TableField("bookname")
    private String bookname;

    /**
     * 作者
     */
    @TableField("author")
    private String author;

    /**
     * 出版社
     */
    @TableField("press")
    private String press;

    /**
     * 描述
     */
    @TableField("bookdesc")
    private String bookdesc;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 0已借，1未借
     */
    @TableField("status")
    private String status;

    @TableField("image")
    private String image;

    /**
     * 书号
     */
    @TableField("num")
    private String num;

    @TableField("createdate")
    private String createdate;

    @TableField("updatedate")
    private String updatedate;

    /**
     * 借阅时间
     */
    @TableField(exist = false)
    private String brodate;

    /**
     * 归还时间
     */
    @TableField(exist = false)
    private String retdate;

    /**
     * 是否归还0未，1已
     */
    @TableField(exist = false)
    private String isbro;

    @TableField(exist = false)
    private  String username;

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getBookdesc() {
        return bookdesc;
    }

    public void setBookdesc(String bookdesc) {
        this.bookdesc = bookdesc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        return "Book{" +
        "bookid=" + bookid +
        ", bookname=" + bookname +
        ", author=" + author +
        ", press=" + press +
        ", bookdesc=" + bookdesc +
        ", type=" + type +
        ", status=" + status +
        ", num=" + num +
        ", createdate=" + createdate +
        ", updatedate=" + updatedate +
        "}";
    }
}
