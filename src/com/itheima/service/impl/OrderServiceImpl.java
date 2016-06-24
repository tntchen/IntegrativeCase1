package com.itheima.service.impl;

import java.sql.SQLException;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {
    
    /**
     * 添加订单
     */
    @Override
    public void addOrder(Order order) throws Exception {
        //1.开启事务
        try {
            DataSourceUtils.startTransaction();
            
            //2.向orders表中添加一条数据
            OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
            od.addOrder(order);
            
            //3.向orderitem中添加n条数据
            for (OrderItem oi : order.getItems()) {
                od.addItem(oi);
            }
            
            //4.事务处理
            DataSourceUtils.commitAndClose();
        } catch (SQLException e) {
            e.printStackTrace();
            DataSourceUtils.rollbackAndClose();
            throw e;
        }
        
    }

}
