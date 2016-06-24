package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;

/**
 * 购物车servlet
 */
public class CartServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    public Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null) {
            // 创建一个cart
            cart = new Cart();
            // 添加到session中
            request.getSession().setAttribute("cart", cart);
        }
        return cart;
    }

    /**
     * 添加到购物车操作
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String addCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 先获取两个参数pid/count
        String pid = request.getParameter("pid");
        int count = Integer.parseInt(request.getParameter("count"));
        
        // 调用productservice,通过id获取商品
        ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
        Product product = ps.getById(pid);

        // 拼装cartitem(product/count/subtotal-可计算)
        CartItem cartItem = new CartItem(product, count);
        
        // 获取购物车调用add2cart
        Cart cart = getCart(request);
        cart.add2Cart(cartItem);

        // 重定向
        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
        return null;
    }
    
    /**
     * 从购物车中移除购物项
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String removeCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取商品pid
        String pid = request.getParameter("pid");
        
        //调用购物车remove方法
        getCart(request).removeFromCart(pid);
        
        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
        return null;      
    }
    
    
    public String clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取购物车并清空
        getCart(request).clearCart();
        
        response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
        return null;
    }    
}
