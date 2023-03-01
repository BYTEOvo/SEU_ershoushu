package com.example.seu_ershoushu.dao;

import com.example.seu_ershoushu.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDAO extends JpaRepository<User,Integer>{
    User findByUsername(String username);
    User getByUsernameAndPassword(String username,String password);
    User findById(int id);
    List<User> findUserByRole(String role);
}
