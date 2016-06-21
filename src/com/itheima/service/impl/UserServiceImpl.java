package com.itheima.service.impl;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.MailUtils;

public class UserServiceImpl implements UserService {

    /**
     * 用户注册
     * 
     * @throws SQLException
     * @throws MessagingException
     * @throws AddressException
     */
    @Override
    public void regist(User user) throws SQLException, AddressException, MessagingException {
        UserDao dao = new UserDaoImpl();
        dao.add(user);

        // 发送邮件
        String emailMsg = "欢迎您注册成我们的一员,<a href='http://localhost:8080/store/user?method=active&code=" + user.getCode()
                + "'>点此激活</a>";
        MailUtils.sendMail(user.getEmail(), emailMsg);
    }

    /**
     * 用户激活
     * 
     * @throws SQLException
     */
    @Override
    public User active(String code) throws SQLException {
        UserDao dao = new UserDaoImpl();
        // 1.通过code获取一个用户
        User user = dao.getByCode(code);

        // 2.判断用户是否为空
        if (user == null) {
            return null;
        }

        // 3.修改用户状态
        user.setState(1);
        dao.update(user);

        return user;
    }

    /**
     * 注册
     * 
     * @throws SQLException
     */
    @Override
    public User login(String username, String password) throws SQLException {
        UserDao dao = new UserDaoImpl();
        return dao.getUserByUsernameAndPwd(username, password);
    }

}
