package cn.itcast.bookStore.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import cn.itcast.bookStore.domain.Order;
import cn.itcast.bookStore.utils.C3P0Util;
import cn.itcast.bookStore.utils.ManagerThreadLocal;

public class OrderDao {

	//添加订单
	public void addOrder(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(ManagerThreadLocal.getConnection(),sql, order.getId(),order.getMoney(), order.getReceiverAddress(), order.getReceiverName(), order.getReceiverPhone(), order.getPaystate(), order.getOrdertime(), order.getUser().getId());
	}

}
