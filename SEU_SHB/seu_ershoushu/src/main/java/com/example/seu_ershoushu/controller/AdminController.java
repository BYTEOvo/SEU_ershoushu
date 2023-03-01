package com.example.seu_ershoushu.controller;

import com.example.seu_ershoushu.pojo.Book;
import com.example.seu_ershoushu.pojo.Category;
import com.example.seu_ershoushu.pojo.Complaint;
import com.example.seu_ershoushu.pojo.User;
import com.example.seu_ershoushu.result.Result;
import com.example.seu_ershoushu.result.ResultFactory;
import com.example.seu_ershoushu.service.*;
import com.example.seu_ershoushu.utils.CategoryUtils;
import com.example.seu_ershoushu.utils.OrderStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    BookService bookService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ComplaintService complaintService;

    @GetMapping("/api/users")
    @CrossOrigin
    public List<User> listAllUsers(){
        return userService.list();
    }

    @PostMapping("/api/category")
    @CrossOrigin
    public Result addOrUpdate(@RequestBody Category category) throws Exception {
        int result=categoryService.addCategory(category);
        if(result==2){
            String message="该书籍类别已存在！";
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(category);
    }

    @GetMapping("/api/complaint")
    @CrossOrigin
    public List<Complaint> listAllComplaint(){
        return complaintService.list();
    }

    @PostMapping("/api/complaint/pass")
    @CrossOrigin
    public Complaint handleComplaint(@RequestBody Complaint complaint){
        return complaintService.handle(complaint);
    }

    @PostMapping("/api/complaint/unpassable")
    @CrossOrigin
    public Complaint rejectedComplaint(@RequestBody Complaint complaint){
        return complaintService.unPass(complaint);
    }

    @PostMapping("/api/complaint/complete")
    @CrossOrigin
    public Complaint completeComplaint(@RequestBody Complaint complaint){
        return complaintService.completeComplaint(complaint);
    }

    @PostMapping("/api/complaint/receive")
    @CrossOrigin
    public Complaint receiveResendBook(@RequestBody Complaint complaint){
        return complaintService.receiveResendBook(complaint);
    }

    @GetMapping("/api/nums")
    @CrossOrigin
    public double[] loadNums(){
        double[] nums= new double[4];
        nums[0]=userService.getUserNumber();
        nums[1]=bookService.getBookNumber();
        nums[2]=orderService.getOrderNumber();
        nums[3]=orderService.getAllMoney();
        return nums;
    }

    @GetMapping("/api/order/weekly")
    @CrossOrigin
    public int[] getOrderNumberWeekly(){
        return orderService.getOrderNumberWeekly();
    }

    @GetMapping("/api/orderState")
    @CrossOrigin
    public OrderStateUtils[] getOrderState(){
        return orderService.getOrderState();
    }

    @GetMapping("/api/category/number")
    @CrossOrigin
    public CategoryUtils[] getCategoryNumber(){
        return bookService.getBookCategoryNumber();
    }

    @GetMapping("/api/categories/{cid}/books")
    @CrossOrigin
    public List<Book> listByCategory(@PathVariable("cid") int cid) throws Exception {
        if (0 != cid) {
            return bookService.listByCategory(cid);
        } else {
            return bookService.list();
        }
    }
}
