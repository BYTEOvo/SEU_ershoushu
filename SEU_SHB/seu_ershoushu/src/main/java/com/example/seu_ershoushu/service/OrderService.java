package com.example.seu_ershoushu.service;

import com.example.seu_ershoushu.dao.BookDAO;
import com.example.seu_ershoushu.dao.OrderDao;
import com.example.seu_ershoushu.dao.UserDAO;
import com.example.seu_ershoushu.pojo.Book;
import com.example.seu_ershoushu.pojo.Order;
import com.example.seu_ershoushu.pojo.User;
import com.example.seu_ershoushu.result.Result;
import com.example.seu_ershoushu.result.ResultFactory;
import com.example.seu_ershoushu.utils.OrderStateUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDAO userDao;

    @Autowired
    BookDAO bookDAO;

    public List<Order> list(){
        return orderDao.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

    public List<Order> findBySeller(String seller) {
        return orderDao.findAllBySeller(seller);
    }

    public List<Order> findByBuyer(String buyer) {
        return orderDao.findAllByBuyer(buyer);
    }

    public List<Order> SearchByBook(Book book){return orderDao.findAllByBook(book);};

    public List<Order> Search(String keywords){return orderDao.findAllByBuyerOrSeller(keywords,keywords);};

    public int addOrder(Order order){
        String buyer=order.getBuyer();
        String seller=order.getSeller();
        if(buyer.trim().equals(seller.trim())){
            return 1;
        }
        Book book=order.getBook();
        User user=userDao.findByUsername(buyer);
        if(book.getPrice()>user.getAccount()){
            return 2;
        }
        book.setSold(1);
        bookDAO.save(book);
        user.setAccount(user.getAccount()-book.getPrice());
        userDao.save(user);
        order.setReceive(user.getAddress());
        order.setSend(userDao.findByUsername(seller).getAddress());
        order.setState("未寄出");
        orderDao.save(order);
        return 0;
    }

    public List<Order> SearchByTime(Date begin,Date end){return orderDao.findAllByBeginAfterAndEndBefore(begin,end);}

    public Order sendBook(Order order){
        order.setState("已寄出");
        return orderDao.save(order);
    }

    public Order receiveBook(Order order){
        User user=userDao.findByUsername(order.getSeller());
        user.setAccount(user.getAccount()+order.getBook().getPrice());
        userDao.save(user);
        order.setEnd(new Date());
        order.setState("已完成");
        return orderDao.save(order);
    }

    public Order findById(int oid) {
        return orderDao.getById(oid);
    }

    public int getOrderNumber() {
        return orderDao.findAllByState("已完成").size();
    }

    public double getAllMoney() {
        List<Order> orderList=orderDao.findAllByState("已完成");
        double money=0;
        for(int i=0;i<orderList.size();i++)
        {
            money+=orderList.get(i).getBook().getPrice();
        }
        return money;
    }

    public List<Order> searchResult(String keywords){
        if("".equals(keywords)){
            return list();
        }
        List<Book> bookList=bookDAO.findAllByTitleOrAuthor(keywords,keywords);
        List<Order> orderList = new ArrayList<>();
        for(int i=0;i<bookList.size();i++){
            orderList.addAll(SearchByBook(bookList.get(i)));
        }
        if(Search(keywords).size()>0)
            orderList.addAll(Search(keywords));
        return orderList;
    }

    public int[] getOrderNumberWeekly() {
        int[] nums=new int[7];
        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 24);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.MILLISECOND, 0);
        cal.setTimeInMillis(today.getTime().getTime()-3600*24*1000*7);
        List<Order> orderList=list();
        for(int i=0;i<orderList.size();i++){
            Order order=orderList.get(i);
            Calendar begin = Calendar.getInstance();
            begin.setTime(order.getBegin());
            if(begin.after(cal)) {
                for(int j=0;j<7;j++){
                    Calendar temp=Calendar.getInstance();
                    temp.setTimeInMillis(cal.getTime().getTime()+3600*24*1000*(j+1));
                    if(begin.before(temp)){
                        nums[j]++;
                        break;
                    }
                }
            }
        }
        return nums;
    }

    public OrderStateUtils[] getOrderState() {
        OrderStateUtils[] nums=new OrderStateUtils[6];
        for(int i=0;i<6;i++)
            nums[i]=new OrderStateUtils();
        nums[0].setName("未发货");
        nums[1].setName("已发货");
        nums[2].setName("已发货，申诉中");
        nums[3].setName("等待寄回");
        nums[4].setName("已完成");
        nums[5].setName("已退款");

        List<Order> orderList=list();
        for(int i=0;i<orderList.size();i++){
            Order order=orderList.get(i);
            if(order.getState().trim().equals("未发货"))
                nums[0].setValue(nums[0].getValue()+1);
            else if(order.getState().trim().equals("已发货"))
                nums[1].setValue(nums[1].getValue()+1);
            else if(order.getState().trim().equals("已发货，申诉中"))
                nums[2].setValue(nums[2].getValue()+1);
            else if(order.getState().trim().equals("等待寄回"))
                nums[3].setValue(nums[3].getValue()+1);
            else if(order.getState().trim().equals("已完成"))
                nums[4].setValue(nums[4].getValue()+1);
            else if(order.getState().trim().equals("已退款"))
                nums[5].setValue(nums[5].getValue()+1);
        }
        return nums;
    }
}
