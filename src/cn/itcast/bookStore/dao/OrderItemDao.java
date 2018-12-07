package cn.itcast.bookStore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.bookStore.domain.Order;
import cn.itcast.bookStore.domain.OrderItem;
import cn.itcast.bookStore.utils.C3P0Util;
import cn.itcast.bookStore.utils.ManagerThreadLocal;

public class OrderItemDao {

	//添加订单项
	public void addOrderItem(Order order) throws SQLException{
		List<OrderItem> orderItems = order.getOrderItems();//得到所有订单项的集合
		QueryRunner  qr = new QueryRunner();
		Object[][] params = new Object[orderItems.size()][];
		//创建局部变量，有多少个子单，循环多少次[循环次数][],get（i）就是第一个item
		for (int i = 0; i < params.length; i++) {//给数组赋值
			//数组中的第一个参数代表主单id，第二个参数商品id，第三个商品购买数量
			params[i] = new Object[]{order.getId(),orderItems.get(i).getP().getId(),orderItems.get(i).getBuynum()};
		}
		qr.batch(ManagerThreadLocal.getConnection(),"insert into orderitem values(?,?,?)", params);
		//开启事务保证是同一个connection
	}
}
