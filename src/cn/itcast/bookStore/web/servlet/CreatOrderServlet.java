package cn.itcast.bookStore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.itcast.bookStore.domain.Order;
import cn.itcast.bookStore.domain.OrderItem;
import cn.itcast.bookStore.domain.Product;
import cn.itcast.bookStore.domain.User;
import cn.itcast.bookStore.service.OrderService;

public class CreatOrderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.封装order对象
		Order order = new Order();
		try {
			BeanUtils.populate(order, request.getParameterMap());
			order.setId(UUID.randomUUID().toString());//给order手动赋一个id
			order.setUser((User) request.getSession().getAttribute("user"));//此处user没有获取到
			//把session对象中的user信息保存到order对象中
		} catch (Exception e) {
			e.printStackTrace();
		}
		//获取session中的购物车数据
		Map<Product, String> cart = (Map<Product, String>) request.getSession().getAttribute("cart");//这里曾经打成catr导致null报错
		//循环遍历封装数据
		List<OrderItem> list = new ArrayList<OrderItem>();
		for (Product p : cart.keySet()) {  //循环添加数据，查一次封装到orderItem中一次，查一次product就封装一次p
			OrderItem oi = new OrderItem(); //
			oi.setOrder(order);//把order对象添加到orderItem中
			oi.setP(p);//把购物车的商品对象添加到orderItem中
			oi.setBuynum(Integer.parseInt(cart.get(p)));//购物车中的商品数量，数量从cart的map中获取，商品数量的value
			list.add(oi);//把每个点单项添加到集合中
		}
		//把集合放入到order对象中
		order.setOrderItems(list);
		//调用业务逻辑
		OrderService os = new OrderService();
		try {
			os.addOrder(order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher("/pay.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
