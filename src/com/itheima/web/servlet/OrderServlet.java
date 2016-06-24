package com.itheima.web.servlet;

import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @throws Exception
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    public String addOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 判断用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("info", "请先登录");
            return "/jsp/info.jsp";
        }

        // 1.封装数据
        Order order = new Order();
        // 2.订单id,时间,总金额,所有订单项,用户
        order.setOid(UUIDUtils.getId());

        order.setOrdertime(new Date());

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        order.setTotal(cart.getTotal());

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            // 设置id,购买数量,小计,product,order
            orderItem.setItemid(UUIDUtils.getId());
            orderItem.setCount(cartItem.getCount());
            orderItem.setSubtotal(cartItem.getSubtotal());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);

            // 添加到list中
            order.getItems().add(orderItem);
        }

        order.setUser(user);

        // 调用service添加订单
        OrderService os = (OrderService) BeanFactory.getBean("OrderService");
        os.addOrder(order);

        // 3.将order放入request中请求转发
        request.setAttribute("bean", order);
        
        // 4.清空购物车
        request.getSession().removeAttribute("cart");
        return "/jsp/order_info.jsp";
    }

}
