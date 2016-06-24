package com.itheima.dao.impl;

import org.apache.commons.dbutils.QueryRunner;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {

    /**
     * 添加一条订单
     */
    @Override
    public void addOrder(Order order) throws Exception {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
        qr.update(DataSourceUtils.getConnection(), sql, order.getOid(), order.getOrdertime(), order.getTotal(),
                order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid());
    }

    /**
     * 添加一条订单项
     */
    @Override
    public void addItem(OrderItem oi) throws Exception {
        QueryRunner qr = new QueryRunner();
        String sql = "insert into orderitem values(?,?,?,?,?)";
        qr.update(DataSourceUtils.getConnection(), sql, oi.getItemid(), oi.getCount(), oi.getSubtotal(),
                oi.getProduct().getPid(), oi.getOrder().getOid());
    }

}
