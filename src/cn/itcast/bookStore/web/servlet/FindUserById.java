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

public class FindUserById extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
