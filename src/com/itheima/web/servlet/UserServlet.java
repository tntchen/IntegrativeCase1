package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.itheima.constant.Constant;
import com.itheima.domain.User;
import com.itheima.myconverter.MyConverter;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.MD5Utils;
import com.itheima.utils.UUIDUtils;

/**
 * 和用户相关的方法
 */
public class UserServlet extends BaseServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 跳转到注册页面
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String registUI(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return "/jsp/register.jsp";
    }

    /**
     * 用户注册
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1.封装数据
        User user = new User();

        // 注册自定义转化器
        ConvertUtils.register(new MyConverter(), Date.class);
        BeanUtils.populate(user, request.getParameterMap());

        // 1.1设置用户id
        user.setUid(UUIDUtils.getId());
        // 1.2设置激活码
        user.setCode(UUIDUtils.getCode());
        // 1.3加密密码
        user.setPassword(MD5Utils.md5(user.getPassword()));

        // 2.调用service完成注册
        UserService s=(UserService) BeanFactory.getBean("UserService");
        s.regist(user);

        // 3.页面请求转发
        request.setAttribute("info", "用户注册已成功,请进入邮箱激活!");

        return "/jsp/info.jsp";
    }

    /**
     * 用户激活
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1.获取激活码
        String code = request.getParameter("code");

        // 2.调用seervice完成激活
        UserService s=(UserService) BeanFactory.getBean("UserService");
        User user = s.active(code);
        if (user == null) {
            // 通过激活码未找到用户
            request.setAttribute("info", "请重新激活");
        } else {
            request.setAttribute("info", "用户激活成功");
        }

        // 3.页面跳转
        return "/jsp/info.jsp";
    }

    /**
     * 跳转到登录页面
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String loginUI(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return "/jsp/login.jsp";
    }

    /**
     * 登录
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1.获取用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        password = MD5Utils.md5(password);

        // 2.调用service完成登录返回user
        UserService s=(UserService) BeanFactory.getBean("UserService");
        User user = s.login(username, password);

        // 3.判断用户
        if (user == null) {
            request.setAttribute("info", "用户名密码不匹配");
            return "/jsp/login.jsp";
        } else {
            // 继续判断用户状态是否激活
            if (Constant.USER_IS_ACTIVE != user.getState()) {
                request.setAttribute("info", "用户名未激活");
                return "/jsp/login.jsp";
            }
        }

        // 4.将user放入session中重定向
        request.getSession().setAttribute("user", user);
        response.sendRedirect(request.getContextPath() + "/");
        return null;
    }

    /**
     * 退出
     * 
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String loginOut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 清除session
        request.getSession().invalidate();
        // 重定向
        response.sendRedirect(request.getContextPath());
        // 处理自动登录
        return null;
    }
}
