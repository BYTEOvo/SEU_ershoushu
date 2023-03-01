package com.example.seu_ershoushu.controller;

import com.example.seu_ershoushu.pojo.Book;
import com.example.seu_ershoushu.pojo.Complaint;
import com.example.seu_ershoushu.pojo.Order;
import com.example.seu_ershoushu.pojo.User;
import com.example.seu_ershoushu.result.Result;
import com.example.seu_ershoushu.result.ResultFactory;
import com.example.seu_ershoushu.service.*;
import com.example.seu_ershoushu.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class PersonalController {
    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BookService bookService;

    @Autowired
    OrderService orderService;

    @Autowired
    ComplaintService complaintService;

    @CrossOrigin
    @GetMapping("/api/user/{username}/books")
    public List<Book> listByUserName(@PathVariable("username") String username) throws Exception {
        return bookService.listByUserName(username);
    }

    @PostMapping("/api/delete")
    @CrossOrigin
    public void delete(@RequestBody Book book) throws Exception {
        bookService.deleteById(book.getId());
    }

    @PostMapping("/api/books")
    @CrossOrigin
    public Book addOrUpdate(@RequestBody Book book) throws Exception {
        bookService.addOrUpdate(book);
        return book;
    }

    @CrossOrigin
    @PostMapping("api/covers")
    public String coversUpload(MultipartFile file) throws Exception {
        String folder = "/usr/local/img";
        File imageFolder = new File(folder);
        if (!imageFolder.exists()){
            imageFolder.mkdirs();
        }
        File f = new File(folder+"/", file.getOriginalFilename());
        if(!f.exists()) {
            f.createNewFile();
        }
        try {
            file.transferTo(f);
            String imgURL = "http://114.116.226.92:8443/api/file/" + f.getName();
            return imgURL;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    @PostMapping("/api/user/info")
    @CrossOrigin
    public Result UpdateInfo(@RequestBody User user) throws Exception {
        int result=userService.updateInfo(user);
        if(result==0)
        {
            String message="手机号不得小于10位";
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(user);
    }

    @PostMapping("/api/user/pwd")
    @CrossOrigin
    public Result UpdatePwd(@RequestBody User user) throws Exception {
        int result=userService.updatePwd(user);
        if(result == 0){
            String message="账号或者密码不能为空";
            return ResultFactory.buildFailResult(message);
        }
        else if(result == 2){
            String message="用户名已被注册";
            return ResultFactory.buildFailResult(message);
        }
        else if(result==3){
            String message="密码不得少于6位";
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(user);
    }

    @CrossOrigin
    @GetMapping("/api/user/{username}/info")
    public User findByUserName(@PathVariable("username") String username) throws Exception {
        return userService.findByUsername(username);
    }

    @CrossOrigin
    @GetMapping("/api/user/{username}/orders/sell")
    public List<Order> listBySeller(@PathVariable("username") String username) throws Exception {
        return orderService.findBySeller(username);
    }

    @CrossOrigin
    @GetMapping("/api/user/{username}/orders/buy")
    public List<Order> listByBuyer(@PathVariable("username") String username) throws Exception {
        return orderService.findByBuyer(username);
    }

    @CrossOrigin
    @GetMapping("/api/{username}/search")
    public List<Book> userSearchResult(@PathVariable String username ,@RequestParam("keywords") String keywords) throws Exception
    {
        if("".equals(keywords)){
            return bookService.listByUserName(username);
        }
        return bookService.UserSearch(username,keywords);
    }

    @CrossOrigin
    @PostMapping("/api/send")
    public Order sendBook(@RequestBody Order order){
        return orderService.sendBook(order);
    }

    @CrossOrigin
    @PostMapping("/api/receive")
    public Order receiveBook(@RequestBody Order order){
        return orderService.receiveBook(order);
    }

    @PostMapping("/api/complaint/create")
    @CrossOrigin
    public Complaint createComplaint(@RequestBody Complaint complaint){
        if(complaint.getOrder().getState().trim().equals("未寄出")){
            complaint.setState("已通过");
            complaintService.add(complaint);
            return complaintService.completeComplaint(complaint);
        }
        else
            return complaintService.add(complaint);
    }

    @CrossOrigin
    @GetMapping("/api/{username}/complaint")
    public List<Complaint> userComplaint(@PathVariable String username) throws Exception
    {
        return complaintService.listByUsername(username);
    }

    @PostMapping("/api/resend")
    @CrossOrigin
    public Complaint sendBookBack(@RequestBody Order order){
        return complaintService.resend(order);
    }
}
