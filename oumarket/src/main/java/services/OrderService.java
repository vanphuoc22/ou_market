///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pojo.Order;

/**
 *
 * @author Khoa Tran
 */
public class OrderService {

    public static Order order;

    public boolean addOrder(Order o) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO orders(id, total, orderDate, tienKhachDua, tienTraLai) VALUES(?,?,?, ?, ?)"; // SQL injection
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, o.getId());
            stm.setDouble(2, o.getTotal());
            stm.setString(3, o.getOrderDate());
            stm.setDouble(4, o.getTienKhachDua());
            stm.setDouble(5, o.getTienTraKhach());

            stm.executeUpdate();
            try {
                conn.commit();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
    }

    public Order findOrder(String id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM orders WHERE id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Order o = new Order(rs.getString("id"), rs.getString("orderDate"), rs.getDouble("total"), rs.getDouble("tienKhachDua"), rs.getDouble("tienTraLai"));
                return o;
            }
        }
        return null;
    }

    public List<Order> getOrders() throws SQLException {
        List<Order> ordersList = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM orders";
            PreparedStatement stm = conn.prepareCall(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String time = rs.getString("orderDate");
                double total = rs.getDouble("total");
                ordersList.add(new Order(id, time, total));
            }
        }
        return ordersList;
    }
}
