package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JsonUtil;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {

	/**
	 * 查询所有的分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  //1.调用categoryservice 查询所有的分类返回list
	    CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
        List<Category> cList = null;
        try {
            cList = cs.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //2.将返回值转为json格式,放入页面
        String json = JsonUtil.list2json(cList);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(json);
        
        //去数据库中查询最新商品和热门商品,将他们放入request域中,请求转发
        return null;
	}

}
