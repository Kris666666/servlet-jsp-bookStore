package cn.itcast.bookStore.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookStore.exception.userException;
import cn.itcast.bookStore.service.UserService;

public class ActiveServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//激活操作传值
		String activeCode = request.getParameter("activeCode");
		UserService userService = new UserService();
			try {
				userService.activeUser(activeCode);
			} catch (userException | SQLException e) {
				//向用户提示失败信息
				response.getWriter().write(e.getMessage());
				e.printStackTrace();
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
