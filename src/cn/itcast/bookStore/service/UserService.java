package cn.itcast.bookStore.service;

import java.sql.SQLException;

import cn.itcast.bookStore.dao.UserDao;
import cn.itcast.bookStore.domain.User;
import cn.itcast.bookStore.exception.userException;
import cn.itcast.bookStore.utils.SendJMail;

public class UserService {

	UserDao userDao = new UserDao();
	public void regist(User user) throws userException {
		try {
			userDao.addUser(user);//用户注册
			String emailMsg = "test,test.注册成功，请访问<a href='http://www.product.com/activeServlet?"
								+ "activeCode="+user.getActiveCode()+"'>激活</a>后登录";
			SendJMail.sendMail(user.getEmail(), emailMsg);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new userException("注册失败");
		}
	}
	public void activeUser(String activeCode) throws SQLException, userException {
		//根据激活码查找用户
		try {
			User user = userDao.findUserByActiveCode(activeCode);
			if (user!=null) {
				//做一个判断不为空时激活用户时将state状态改为1；
				userDao.activeCode(activeCode);
				return;
			}
			throw new userException("激活失败");//没查到抛出一个
		} catch (SQLException e) {
			e.printStackTrace();
			throw new userException("激活失败");//异常抛出一个
		}
	}
	public User login(String username, String password) throws userException {
		User user = null;
		try {
			user = userDao.findUserByUserNameAndPassword(username,password);
			if (user==null) {  //判断为空的情况
				throw new userException("用户名或密码错误！");
			}
			if (user.getState()==0) {//状态为0时用户未激活
				throw new userException("用户未激活，请先激活！");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	//根据用户id查值
	public User findUserById(String id) throws userException {
		try {
			return userDao.findUserById(id);
		} catch (SQLException e) {
			throw new userException("用户查找失败！");
		}
	}
	//修改用户信息
	public void modifyUser(User user) throws userException {
		try {
			userDao.modifyUser(user);
		} catch (SQLException e) {
			throw new userException("修改用户信息失败！");
		}
	}
	
	
}
