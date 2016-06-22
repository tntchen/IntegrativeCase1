package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.BeanFactory;

/**
 * 和首页相关的servlet
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //去数据库里查询热门商品和最新商品
	    ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
	    
	    List<Product> newList = null;
	    List<Product> hotList = null;
        try {
            newList = ps.findNew();
            hotList = ps.findHot();
        } catch (Exception e) {
            e.printStackTrace();
        }
	    
	    request.setAttribute("nList", newList);
	    request.setAttribute("hList", hotList);
	    
	    //去数据库中查询最新商品和热门商品,将他们放入request域中,请求转发
	    return "/jsp/index.jsp";
	    
	}

}
