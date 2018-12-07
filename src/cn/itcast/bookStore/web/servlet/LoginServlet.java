package cn.itcast.bookStore.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookStore.domain.User;
import cn.itcast.bookStore.exception.userException;
import cn.itcast.bookStore.service.UserService;

public class LoginServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
