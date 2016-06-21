package com.itheima.dao;

import java.sql.SQLException;

import com.itheima.domain.User;

public interface UserDao {

    void add(User user) throws SQLException;

    User getByCode(String code) throws SQLException;

    void update(User user) throws SQLException;

    User getUserByUsernameAndPwd(String username, String password) throws SQLException;

}
