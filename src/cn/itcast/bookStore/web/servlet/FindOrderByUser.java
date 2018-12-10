package cn.itcast.bookStore.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookStore.domain.Order;
import cn.itcast.bookStore.domain.User;
import cn.itcast.bookStore.service.OrderService;

public class FindOrderByUser extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user =  (User) request.getSession().getAttribute("user");
		OrderService os = new OrderService();
		List<Order> orders =  os.findOrderByUserId(user.getId());//要返回一个order集合，因为一个人可以购买好多订单
		request.setAttribute("count", orders.size());
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
