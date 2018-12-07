package cn.itcast.bookStore.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.bookStore.domain.Product;
import cn.itcast.bookStore.service.ProductService;

public class ChangeNumServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String num = request.getParameter("num");
		//注：只能重写id的hashcode
		Product b = new Product();
		b.setId(id);
		//重写session中的id值
		HttpSession session = request.getSession();
		Map<Product, String> cart = (Map<Product, String>) session.getAttribute("cart");
		//if商品数据为0，就删除此商品
		if ("0".equals(num)) {
			cart.remove(b);
		}
		//如果购物车找到相同商品的值时,num+1
		if (cart.containsKey(b)) {
			cart.put(b, num);
		}
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
