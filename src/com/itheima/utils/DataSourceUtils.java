package com.itheima.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 工具类
 * 增加事务的操作
 */
public class DataSourceUtils {
	private static ComboPooledDataSource ds = new ComboPooledDataSource(); 
	private static ThreadLocal<Connection> tl = new ThreadLocal<>();
	
	//获取数据源
	public static DataSource getDataSource() {
		return ds;
	}
	/**
	 * 从当前线程上获取连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = tl.get();
		if (conn == null) {
			//第一次获取连接为空 则创建一个连接和当前线程绑定
			conn = ds.getConnection();
			tl.set(conn);
		}
		return conn;
	}
	/** 
	 * 释放三个资源
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void closeResource(Connection conn, Statement st, ResultSet rs) {
		closeResource(st, rs);
		closeConn(conn);
	}
	/**
	 * 释放两个资源
	 * @param st
	 * @param rs
	 */
	public static void closeResource(Statement st, ResultSet rs) {
		closeResultset(rs);
		closeStatement(st);
	}
	/**
	 * 释放连接
	 * @param conn
	 */
	public static void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				//和当前线程解绑
				tl.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//设置为空,垃圾回收机制可将资源回收
			conn = null;
		}
	}
	/**
	 * 释放语句执行者
	 * @param st
	 */
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//设置为空,垃圾回收机制可将资源回收
			st = null;
		}
	}
	/**
	 * 释放结果集
	 * @param rs
	 */
	public static void closeResultset(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//设置为空,垃圾回收机制可将资源回收
			rs = null;
		}
	}
	/**
	 * 开启事务
	 * @throws SQLException 
	 */
	public static void startTransaction() throws SQLException {
		//获取连接,开启事务
		getConnection().setAutoCommit(false);	
	}
	/**
	 * 事务提交
	 */
	public static void commitAndClose() {
		//获取连接
		try {
			Connection conn = getConnection();
			//提交事务
			conn.commit();
			//释放资源
			conn.close();
			//解除绑定
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 事务回滚
	 */
	public static void rollbackAndClose() {
		//获取连接
		try {
			Connection conn = getConnection();
			//提交事务
			conn.rollback();
			//释放资源
			conn.close();
			//解除绑定
			tl.remove();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
