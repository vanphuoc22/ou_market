/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import pojo.OrderDetails;
import pojo.Order;
import pojo.Product;

/**
 *
 * @author Khoa Tran
 */
public class OrderDetailsService {
    
    
    public boolean saveOderDetails(OrderDetails oD, Order o) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO orderdetails(productName, price, quantity, total, order_id) VALUES(?, ?, ?, ?, ?)"; // SQL injection
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, oD.getProduct().getName());
            stm.setDouble(2, oD.getPrice());
            stm.setDouble(3, oD.getQuantity());
            stm.setDouble(4, oD.getTotal());
            stm.setString(5, o.getId());

            stm.executeUpdate();

            try {
                conn.commit();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
    }
    
    public boolean updateQuantityInStock(ObservableList<OrderDetails> orderDetailsList) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE products SET quantity = quantity-? WHERE name =?";
            PreparedStatement prepare = conn.prepareStatement(sql);
            for (OrderDetails oD : orderDetailsList) {
                prepare.setDouble(1, oD.getQuantity());
                prepare.setString(2, oD.getProduct().getName());
                prepare.executeUpdate();
            }
            return true;
        }
    }
    
    public List<Product> viewDetail(String id) throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "SELECT * FROM orderdetails WHERE order_id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                Product p = new Product(rs.getString("id"), rs.getString("productName"), rs.getDouble("price"), rs.getInt("quantity"));
                products.add(p);
            }
            return products;
        }
    }
}
