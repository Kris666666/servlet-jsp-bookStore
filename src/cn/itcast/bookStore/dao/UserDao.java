package cn.itcast.bookStore.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.bookStore.domain.User;
import cn.itcast.bookStore.utils.C3P0Util;


public class UserDao {
	//注册用户
	public void addUser(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "INSERT INTO user(username,PASSWORD,gender,email,telephone,introduce,activecode,state,registtime) "
				+ "VALUES(?,?,?,?,?,?,?,?,?)";
		qr.update(sql, user.getUsername(), user.getPassword(),
				user.getGender(), user.getEmail(),user.getTelephone(), user.getIntroduce(),
				user.getActiveCode(), user.getState(), user.getRegisTIme());
	}
	//根据activeCode查找用户
	public User findUserByActiveCode(String activeCode) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from user where activecode=?", new BeanHandler<User>(User.class),activeCode);
	}
	//修改用户激活状态
	public void activeCode(String activeCode) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("update user set state=1 where activecode=?",activeCode);
	}
	//用户登录功能，根据用户名和密码查找用户
	public User findUserByUserNameAndPassword(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from user where username=? and password=?", new BeanHandler<User>(User.class),username,password);
	}
	//根据id查找用户
	public User findUserById(String id) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr.query("select * from user where id=?", new BeanHandler<User>(User.class),id);
	}
	//修改用户信息
	public void modifyUser(User user) throws SQLException {//密码性别联系方式
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		qr.update("update user set password=? , gender=? , telephone=? where id=?",user.getPassword(),user.getGender(),user.getTelephone(),user.getId());
	}
}
