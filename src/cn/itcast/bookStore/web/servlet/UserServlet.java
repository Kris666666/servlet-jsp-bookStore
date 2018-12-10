package cn.itcast.bookStore.web.servlet;

import java.io.IOException;
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

//优化servlet
public class UserServlet extends HttpServlet {//此处终极版是继承baseServlet用反射的思路来定位到方法名

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取请求参数
		String method = request.getParameter("method");
		if (!"".equals(method)) {
			if ("login".equals(method)) {
				login(request, response);
			}
			if ("register".equals(method)) {
				register(request, response);
			}
			if ("logout".equals(method)) {
				login(request, response);
			}
			if ("logout".equals(method)) {
				logout(request, response);
			}
			if ("findUserById".equals(method)) {
				findUserById(request, response);
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取表单数据
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				UserService userService = new UserService();
				try {
					String path = "/index.jsp";
					User user =  userService.login(username,password);
					if ("admin".equals(user.getRole())) {
						path = "admin/login/home.jsp";
					}
					request.getSession().setAttribute("user", user);
					request.getRequestDispatcher(path).forward(request, response);
				} catch (userException e) {
					e.printStackTrace();
					request.setAttribute("user_msg", e.getMessage());//进入异常抛出信息重定向login页面
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
	}
	
	protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();//销毁session
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}
	
	protected void findUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		UserService userService = new UserService();
		try {
			User user = userService.findUserById(id);//这里要返回一个user对象
			request.setAttribute("user",user);
			if (user==null) {
				request.setAttribute("findUserById_msg", "用户不存在！");
				request.getRequestDispatcher("/modifyuserinfo.jsp").forward(request, response);
			}
			request.getRequestDispatcher("/modifyuserinfo.jsp").forward(request, response);
		} catch (userException e) {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
		}
		
	}
}
