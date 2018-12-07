package cn.itcast.bookStore.service;

import java.sql.SQLException;

import cn.itcast.bookStore.dao.OrderDao;
import cn.itcast.bookStore.dao.OrderItemDao;
import cn.itcast.bookStore.dao.ProductDao;
import cn.itcast.bookStore.domain.Order;
import cn.itcast.bookStore.utils.ManagerThreadLocal;

public class OrderService {

	OrderDao orderDao = new OrderDao();
	OrderItemDao orderItemDao = new OrderItemDao();
	ProductDao productDao = new ProductDao();
	
	public void addOrder(Order order) throws SQLException{
		try {
			ManagerThreadLocal.startTransacation();
			orderDao.addOrder(order);
			orderItemDao.addOrderItem(order);
			productDao.updateProductNum(order);
			ManagerThreadLocal.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ManagerThreadLocal.rollback();
		}
	}
}
