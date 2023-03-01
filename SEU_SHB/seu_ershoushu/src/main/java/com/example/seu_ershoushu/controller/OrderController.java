package com.example.seu_ershoushu.controller;

import com.example.seu_ershoushu.pojo.Book;
import com.example.seu_ershoushu.pojo.Order;
import com.example.seu_ershoushu.pojo.User;
import com.example.seu_ershoushu.result.Result;
import com.example.seu_ershoushu.result.ResultFactory;
import com.example.seu_ershoushu.service.BookService;
import com.example.seu_ershoushu.service.OrderService;
import com.example.seu_ershoushu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @PostMapping("/api/orders")
    @CrossOrigin
    public Result addOrUpdate(@RequestBody Order order) throws Exception {
        int result = orderService.addOrder(order);
        if(result==1){
            String message="不能购买自己的书籍";
            return ResultFactory.buildFailResult(message);
        }

        if(result==2){
            String message="余额不足";
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult(order);
    }

    @GetMapping("/api/allorder")
    @CrossOrigin
    public List<Order> listAllOrder(){
        return orderService.list();
    }

    @CrossOrigin
    @PostMapping("/api/order/search")
    public List<Order> searchResult(@RequestParam("keywords") String keywords) throws Exception
    {
        return orderService.searchResult(keywords);
    }
}
