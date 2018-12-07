package cn.itcast.bookStore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.itcast.bookStore.domain.User;
import cn.itcast.bookStore.exception.userException;
import cn.itcast.bookStore.service.UserService;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//验证验证码
		String ckcode = request.getParameter("ckcode");//从前端获取到输入的验证码
		String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");//获取正确的验证码
		if(!checkcode_session.equals(ckcode)){//如果两个验证码不一致，跳回注册面
			request.setAttribute("ckcode_msg", "验证码错误！");//set验证信息，供jsp页面获取
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return ;
		}
		//获取保单数据
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
			user.setActiveCode(UUID.randomUUID().toString());//手动设置激活码值
			//调用业务逻辑
			UserService us = new UserService();
			us.regist(user);
			//分发转向
			request.getSession().setAttribute("user", user);//把用户信息封装到sission对象中
			request.getRequestDispatcher("/registersuccess.jsp").forward(request, response);
		}catch (userException e){
			request.setAttribute("user_msg", e.getMessage());
			request.getRequestDispatcher("/register.jsp").forward(request, response);
			return;
			//自定义异常，将异常抛向客户端
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
