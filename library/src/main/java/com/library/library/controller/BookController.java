package com.library.library.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.library.library.entity.Book;
import com.library.library.domain.Result;
import com.library.library.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.UUID;

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
@RequestMapping("/book")
public class BookController   {

    @Autowired
    private BookService bookService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 添加图书
     * @param book
     * @return
     */
    @PostMapping("/add")
    public Result<String> bookAdd(@RequestBody Book book) {
        bookService.addBook(book);
        return Result.getSuccess("添加成功！");
    }

    /**
     * 修改图书
     * @param book
     * @return
     */
    @PutMapping("/update")
    public Result<String> bookUpdate(@RequestBody Book book) {
        bookService.updateBook(book);
        return Result.getSuccess("修改成功！");
    }

    /**
     * 删除图书
     * @param bookId
     * @return
     */
    @DeleteMapping("/delete")
    public Result<String> bookDelete(@RequestParam int bookId) {
        bookService.removeById(bookId);
        return Result.getSuccess("删除成功！");
    }

    /**
     *图书详细信息
     * @param bookId
     * @return
     */
    @GetMapping("/detail")
    public Result<Book> bookDetail(@RequestParam int bookId) {
        return Result.getSuccess(bookService.getById(bookId));
    }

    /**
     * 查询图书列表
     * @return
     */
    @ApiOperation(value = "查询图书列表")
    @GetMapping("/list")
    public Result<List<Book>> bookList() {
        List<Book> bookList = (List<Book>) redisTemplate.opsForValue().get("bookList");
        if (bookList == null) {
            bookList = bookService.list();
            redisTemplate.opsForValue().set("bookList", bookList);
        }
        return Result.getSuccess(bookList);
    }

    /**
     * 查询未借的图书列表
     * @return
     */
    @GetMapping("/listByStatus")
    public Result<List<Book>> bookListByStatus() {
        return Result.getSuccess(bookService.listBookByStatus());
    }

    /**
     * 多条件查询
     * @param content
     * @return
     */
    @RequestMapping("/listByCon")
    public Result<List<Book>> bookListByCon(String content) {
        return Result.getSuccess(bookService.listBookByCon(content));
    }

    /**
     * 人气图书
     * @param
     * @return
     */
    @GetMapping("/popular")
    public Result<List<Book>> popularBookList() {
        return Result.getSuccess(bookService.listBookPopular());
    }

    /**
     * 推荐图书
     * @param
     * @return
     */
    @GetMapping("/recommend")
    public Result<List<Book>> recommendBookList() {
        return Result.getSuccess(bookService.listRecommendBook());
    }

    /**
     *图片上传 base64
     * @param image
     * @return
     */
    @RequestMapping("/upload")
    public Result<String> uploadImage(@RequestParam String image) throws IOException {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid + ".png";
        BASE64Decoder decoder = new BASE64Decoder();
            //Base64解码
            byte[] b = decoder.decodeBuffer(image);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //调整异常数据
                    b[i] += 256;
                }
            }
        //指定本地文件夹存储图片
        File file = new File("E://images//");
        //如果文件夹不存在
        if (!file.exists()) {
            //创建文件夹
            file.mkdir();
        }
        //新生成的图片
        String filePath = file.getPath() + "/";
        OutputStream out = new FileOutputStream( filePath + fileName);
        out.write(b);
        out.flush();
        out.close();
        // 获取服务器地址
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName()
                + ":" + request.getServerPort() + path;

        // 图片上传成功返回图片地址
        return Result.getSuccess(basePath + "/static/image/" + fileName);
    }

    /**
     * 保存图书，表单上传图片
     * @param book
     * @param img
     * @return
     * @throws IOException
     */
    @RequestMapping("/bookSave")
    public Result<String> bookSave(Book book,@RequestParam(required = false) MultipartFile img) throws IOException {
        if (img != null) {
            String name = img.getOriginalFilename();
            assert name != null;
            String suffix = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
            String fileName = UUID.randomUUID() + "." + suffix;
            //指定本地文件夹存储图片
            File dir = new File("E://images//");
            //如果文件夹不存在
            if (!dir.exists()) {
                //创建文件夹
                dir.mkdir();
            }
            String filePath = dir.getPath() + "/";
            File image = new File("E://images//" + fileName);
            img.transferTo(image);
            // 获取服务器地址
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort() + path;
            book.setImage(basePath + "/static/image/" + fileName);
        }
        bookService.saveOrUpdate(book);
        return Result.getSuccess();
    }

}