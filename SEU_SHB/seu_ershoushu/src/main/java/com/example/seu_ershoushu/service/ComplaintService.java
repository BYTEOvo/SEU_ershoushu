package com.example.seu_ershoushu.service;

import com.example.seu_ershoushu.dao.ComplaintDAO;
import com.example.seu_ershoushu.dao.OrderDao;
import com.example.seu_ershoushu.dao.UserDAO;
import com.example.seu_ershoushu.pojo.Complaint;
import com.example.seu_ershoushu.pojo.Order;
import com.example.seu_ershoushu.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ComplaintService {
    @Autowired
    ComplaintDAO complaintDAO;

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDAO userDAO;

    public List<Complaint> list() {
        return complaintDAO.findAll();
    }

    public Complaint add(Complaint complaint) {
        Order order=complaint.getOrder();
        order.setState(order.getState()+",申诉中");
        orderDao.save(order);
        return complaintDAO.save(complaint);
    }

    public Complaint unPass(Complaint complaint){
        Order order=complaint.getOrder();
        order.setState("已完成");
        order.setEnd(new Date());
        orderDao.save(order);
        User user=userDAO.findByUsername(order.getSeller());
        user.setAccount(user.getAccount()+order.getBook().getPrice());
        userDAO.save(user);
        return complaintDAO.save(complaint);
    }

    public List<Complaint> listByUsername(String username) {
        return complaintDAO.findAllByUsername(username);
    }

    public Complaint handle(Complaint complaint) {
        Order order=complaint.getOrder();
        User user=userDAO.findByUsername(order.getBuyer());
        if(!order.getState().trim().equals("已寄出,申诉中")) {
            user.setAccount(user.getAccount() + order.getBook().getPrice());
            order.setState("已退款");
            order.setEnd(new Date());
            userDAO.save(user);
            orderDao.save(order);
            return complaintDAO.save(complaint);
        }
        order.setState("等待寄回");
        orderDao.save(order);
        return complaintDAO.save(complaint);
    }

    public Complaint completeComplaint(Complaint complaint){
        Order order=complaint.getOrder();
        User user=userDAO.findByUsername(order.getBuyer());
        user.setAccount(user.getAccount() + order.getBook().getPrice());
        order.setState("已退款");
        order.setEnd(new Date());
        userDAO.save(user);
        orderDao.save(order);
        return complaintDAO.save(complaint);
    }

    public Complaint resend(Order order) {
        order.setState("已寄回");
        orderDao.save(order);
        Complaint complaint=complaintDAO.findByOrder_Id(order.getId());
        complaint.setOrder(order);
        return complaintDAO.save(complaint);
    }

    public Complaint receiveResendBook(Complaint complaint) {
        Order order=complaint.getOrder();
        order.setState("已退款");
        order.setEnd(new Date());
        User user=userDAO.findByUsername(order.getBuyer());
        user.setAccount(user.getAccount() + order.getBook().getPrice());
        userDAO.save(user);
        orderDao.save(order);
        complaint.setOrder(order);
        return complaintDAO.save(complaint);
    }

    /*public List<Complaint> getTop10Complaint(){
        return complaintDAO.findByChecked(false);
    }

    public void addComplaintByOrderIdAndReason(Integer oid,String reason){
        Complaint complaint = new Complaint();
        Optional<Order> orderOptional = orderDao.findById(oid);
        if(orderOptional.isPresent()){
            complaint.setOrder(orderOptional.get());
            complaint.setReason(reason);
            complaintDAO.save(complaint);
        }
    }

    public void deleteComplaintByOrderId(Integer orderId){
        Optional<Complaint> optionalComplaint = complaintDAO.findById(orderId);
        if(optionalComplaint.isPresent()){
            Complaint complaint = optionalComplaint.get();
            Order order = complaint.getOrder();
            order.setOrderState(OrderState.ALL_COMPLETED);
            complaintDAO.delete(complaint);
            orderDao.save(order);
        }
    }*/


}
