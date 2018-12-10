package cn.itcast.bookStore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.bookStore.domain.Order;
import cn.itcast.bookStore.domain.OrderItem;
import cn.itcast.bookStore.domain.Product;
import cn.itcast.bookStore.utils.C3P0Util;
import cn.itcast.bookStore.utils.ManagerThreadLocal;

public class OrderDao {

	//添加订单
	public void addOrder(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(ManagerThreadLocal.getConnection(),sql, order.getId(),order.getMoney(), order.getReceiverAddress(), order.getReceiverName(), order.getReceiverPhone(), order.getPaystate(), order.getOrdertime(), order.getUser().getId());
	}

	//根据用户id查询所有订单
	public List<Order> findOrders(int id) throws SQLException{
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		String sql = "select * from orders where user_id=?";
		return qr.query(sql, new BeanListHandler<Order>(Order.class),id);
	}

	public Order findOrderItemsByOrderId(String orderid) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		//根据id查order表，得到一个订单
		Order order = qr.query("select *from orders where id=?", new BeanHandler<Order>(Order.class),orderid);
		//根据订单id两表联查product和orderitem,因为是多个订单项，要查好好几个订单，所以要用list
		List<OrderItem> orderItems = qr.query("select * from products p,orderitem o where p.id=o.product_id and order_id=?", new ResultSetHandler<List<OrderItem>>(){
			
			public List<OrderItem> handle(ResultSet rs) throws SQLException {//匿名内部类
				List<OrderItem> orderItems = new ArrayList<OrderItem>();
				while(rs.next()){//while循环取值
					//封装orderitem对象
					OrderItem oi = new OrderItem();
					oi.setBuynum(rs.getInt("buynum"));
					//封装product对象
					Product p = new Product();
					p.setName(rs.getString("name"));
					p.setPrice(rs.getDouble("price"));
					//把每个producr的数据封装到orderitem中
					oi.setP(p);
					//把每个orderitem对象封装到orderirtem集合中，最后将集合数据封装到order中去
					orderItems.add(oi);
				}
				
				return orderItems;
			}
			
		},orderid);
		//把所有的订单项orderitems添加到主单对象order中
		order.setOrderItems(orderItems);
		
		return order;
	}

}
