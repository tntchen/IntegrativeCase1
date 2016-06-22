package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.CookieUtils;

/**
 * 商品servlet
 */
/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {

    /**
     * 通过id查询商品详情
     */
    public String getById(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 1.获取商品id
        String pid = request.getParameter("pid");

        // 1.获取指定的cookie
        Cookie cookie = CookieUtils.getCookieByName("ids", request.getCookies());
        String ids = "";

        // 判断cookie是否为空
        if (cookie == null) {
            // 若为空 将当前商品id放入ids
            ids = pid;
        } else {
            // 不为空 继续判断ids中已经包含id
            // 获取值
            ids = cookie.getValue();
            String[] idsplit = ids.split("-");
            // 将数组转为集合,此list长度不可变
            List<String> asList = Arrays.asList(idsplit);
            // 将aslist放入新的list
            LinkedList<String> list = new LinkedList<>(asList);

            if (list.contains(pid)) {
                // 若包含id,将id移除,放置最前
                list.remove(pid);
                list.addFirst(pid);
            } else {
                // 若不包含 判断长度是否大于2
                if (list.size() > 2) {
                    // 长度大于等于3,移除最后一个,当前id放在最前
                    list.removeLast();
                    list.addFirst(pid);
                } else {
                    list.addFirst(pid);
                }
            }
            ids = "";
            // 将list转回字符串
            for (String s : list) {
                ids += (s + "-");
            }
            ids = ids.substring(0, ids.length() - 1);
        }

        // 将ids写回浏览器
        cookie = new Cookie("ids", ids);
        // 设置访问路径
        cookie.setPath(request.getContextPath() + "/");
        // 设置存活时间
        cookie.setMaxAge(3600);
        // 写回浏览器
        response.addCookie(cookie);

        // 2.调用service
        ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
        Product p = null;
        try {
            p = ps.getById(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3.将结果放入request中请求转发
        request.setAttribute("bean", p);
        return "/jsp/product_info.jsp";
    }

    /**
     * 分页查询数据
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String findByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1.获取类别和当前页 设置一个pageSize
        String cid = request.getParameter("cid");
        int currPage = Integer.parseInt(request.getParameter("currPage"));
        int pageSize = 6;

        // 2.调用service 返回pageBean
        ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
        PageBean<Product> bean = ps.findByPage(currPage, pageSize, cid);

        // 3.将结果放入request中请求转发
        request.setAttribute("pb", bean);
        return "/jsp/product_list.jsp";
    }
}
