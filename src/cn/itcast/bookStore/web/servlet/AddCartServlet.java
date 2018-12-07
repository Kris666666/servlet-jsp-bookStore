package cn.itcast.bookStore.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cn.itcast.bookStore.domain.Product;
import cn.itcast.bookStore.service.ProductService;

public class AddCartServlet extends HttpServlet {


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		ProductService bs = new ProductService();
		Product b = bs.findBookById(id);
		//从session中的购物车获取出来
		HttpSession session = request.getSession();
		Map<Product, String> cart = (Map<Product, String>) session.getAttribute("cart");
		int num = 1;
		//如果是第一次访问没有购物车对象，创建一个
		if (cart==null) {
			cart = new HashMap<Product, String>();
		}
		//查看当前集合中是否存在b这本书,如果有就把数据取出来加1;
		if (cart.containsKey(b)) {
			num = Integer.parseInt(cart.get(b))+1;
		}
		cart.put(b,num+"");
		//把cart对象放回到session作用域中
		session.setAttribute("cart", cart);
		out.print("<a href='"+request.getContextPath()+"/pageServlet'>继续购物</a>，<a href='"+request.getContextPath()+"/cart.jsp'>查看购物车</a>");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
