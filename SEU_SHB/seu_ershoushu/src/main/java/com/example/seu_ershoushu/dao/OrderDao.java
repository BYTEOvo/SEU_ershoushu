package com.example.seu_ershoushu.dao;

import com.example.seu_ershoushu.pojo.Book;
import com.example.seu_ershoushu.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderDao extends JpaRepository<Order,Integer> {
    List<Order> findAllByBuyer(String buyer);
    List<Order> findAllBySeller(String seller);
    List<Order> findAllByBook(Book book);
    List<Order> findAllByBuyerOrSeller(String keywords1,String keyword2);
    List<Order> findAllByBeginAfterAndEndBefore(Date begin, Date end);
    List<Order> findAllByState(String keyword);
}
