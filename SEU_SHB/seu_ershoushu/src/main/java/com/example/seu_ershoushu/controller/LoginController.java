package com.example.seu_ershoushu.controller;

import com.example.seu_ershoushu.pojo.Book;
import com.example.seu_ershoushu.result.ResultFactory;
import com.example.seu_ershoushu.result.Result;
import com.example.seu_ershoushu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import org.apache.shiro.subject.Subject;
import com.example.seu_ershoushu.pojo.User;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser) {
        // 对 html 标签进行转义，防止 XSS 攻击
        String username = requestUser.getUsername();
        username=HtmlUtils.htmlEscape(username);
        Subject subject= SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,requestUser.getPassword());
        try{
            subject.login(usernamePasswordToken);
            User user=userService.findByUsername(username);
            return ResultFactory.buildSuccessResult(username);
        }catch(AuthenticationException e)
        {
            String message="账号密码错误";
            return ResultFactory.buildFailResult(message);
        }
        /*if (!Objects.equals("admin", username) || !Objects.equals("123456", requestUser.getPassword())) {
            String message = "账号密码错误";
            System.out.println("test");
            return ResultFactory.buildFailResult(message);
        }System.out.println("success");
        return ResultFactory.buildSuccessResult(username);*/
    }

    @CrossOrigin
    @PostMapping(value="api/register")
    @ResponseBody
    public Result register(@RequestBody User user){
        int result=userService.register(user);

        if(result == 0){
            String message="用户名或者密码不能为空";
            return ResultFactory.buildFailResult(message);
        }
        else if(result == 2){
            String message="用户名已被注册";
            return ResultFactory.buildFailResult(message);
        }
        else if(result == 3){
            String message="手机号不得小于10位";
            return ResultFactory.buildFailResult(message);
        }
        else if(result == 4){
            String message="密码不得少于6位";
            return ResultFactory.buildFailResult(message);
        }

        return ResultFactory.buildSuccessResult(user);
    }

    @ResponseBody
    @GetMapping("api/logout")
    public Result logout()
    {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        String message = "成功登出";
        return ResultFactory.buildSuccessResult(message);
    }

    @ResponseBody
    @CrossOrigin
    @GetMapping("/api/user/{username}/role")
    public User listByUserName(@PathVariable("username") String username) throws Exception {
        return userService.findByUsername(username);
    }
}

