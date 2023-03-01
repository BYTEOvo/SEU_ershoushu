package com.example.seu_ershoushu.config;

import com.example.seu_ershoushu.pojo.Order;
import com.example.seu_ershoushu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleTask {
    @Autowired
    OrderService orderService;

    @Scheduled(cron = "0 0 23 * * ?")
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        List<Order> orderList=orderService.list();
        for(int i=0;i<orderList.size();i++){
            Order order=orderList.get(i);
            Date begin = order.getBegin();
            Date current = new Date(System.currentTimeMillis());

            Calendar calendar_end = Calendar.getInstance();
            calendar_end.setTime(begin);

            Calendar calendar_current = Calendar.getInstance();
            calendar_current.setTime(current);

            //给订单结束时间加7天
            calendar_end.add(Calendar.DATE, 7);
            if (calendar_current.after(calendar_end)&&((order.getState().trim().equals("未寄出"))||(order.getState().trim().equals("已寄出")))) {
                orderService.receiveBook(order);
            }
        }
    }
}
