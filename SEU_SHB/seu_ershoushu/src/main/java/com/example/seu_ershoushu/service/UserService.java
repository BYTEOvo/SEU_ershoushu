package com.example.seu_ershoushu.service;

import com.example.seu_ershoushu.dao.UserDAO;
import com.example.seu_ershoushu.pojo.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String username)
    {
        User user=getByName(username);
        return null!=user;
    }

    public User getByName(String username)
    {

        return userDAO.findByUsername(username);
    }

    public User get(String username, String password)
    {

        return userDAO.getByUsernameAndPassword(username, password);
    }

    public void add(User user)
    {
        userDAO.save(user);
    }

    public User findByUsername(String userName)
    {
        return userDAO.findByUsername(userName);
    }

    public int register(User user){
        String username=user.getUsername();
        String password=user.getPassword();
        String phoneNumber=user.getPhonenumber();
        username= HtmlUtils.htmlEscape(username);
        user.setUsername(username);

        if (username.equals("") || password.equals("")) {
            return 0;
        }

        if(password.length()<6)
        {
            return 4;
        }

        if(phoneNumber.length()<10)
        {
            return 3;
        }

        boolean exist = isExist(username);

        if (exist) {
            return 2;
        }

        // 默认生成 16 位盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", password, salt, times).toString();

        user.setSalt(salt);
        user.setPassword(encodedPassword);
        user.setRole("user");
        userDAO.save(user);
        return 1;
    }

    public int updateInfo(User user){
        String name=user.getRole();
        String phoneNumber= user.getPhonenumber();
        if(phoneNumber.length()<10)
        {
            return 0;
        }
        int userId = userDAO.findByUsername(name).getId();
        User oldUser = userDAO.findById(userId);
        oldUser.setAddress(user.getAddress());
        oldUser.setPhonenumber(user.getPhonenumber());
        userDAO.save(oldUser);
        return 1;
    }

    public int updatePwd(User user){
        String username=user.getUsername();
        String password=user.getPassword();
        String name=user.getRole();
        username= HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        if (password.equals("")||username.equals("")) {
            return 0;
        }
        if(password.length()<6)
        {
            return 3;
        }
        int userId = userDAO.findByUsername(name).getId();
        User oldUser = userDAO.findById(userId);
        if(isExist(username)&&!(username.equals(oldUser.getUsername())))
            return 2;
        // 默认生成 16 位盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;
        String encodedPassword = new SimpleHash("md5", user.getPassword(), salt, times).toString();
        oldUser.setSalt(salt);
        oldUser.setPassword(encodedPassword);
        oldUser.setUsername(username);
        userDAO.save(oldUser);
        return 1;
    }

    public List<User> list(){
        return userDAO.findUserByRole("user");
    }

    public int getUserNumber() {
        return userDAO.findUserByRole("user").size();
    }
}
