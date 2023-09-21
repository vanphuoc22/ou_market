/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import pojo.Product;
import pojo.Promotion;
import utils.MessageBox;

/**
 *
 * @author Khoa Tran
 */
public class PromotionService {

    public List<Promotion> getPromotions(String kw) throws SQLException, ParseException {
        List<Promotion> promos = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("SELECT * FROM promotion");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double value = rs.getInt("value");
                Date start = rs.getDate("start");
                Date end = rs.getDate("end");

                promos.add(new Promotion(id, name, value, start, end));
            }
        }
        return promos;
    }

    public boolean deletePromotion(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM promotion WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);
            stm.executeUpdate();
            return true;
        }
    }
    
    public boolean editPromotion(Promotion p) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            
            String sql = "SELECT * FROM promotion WHERE name = ?";
            PreparedStatement stmCheckUnique = conn.prepareCall(sql);
            stmCheckUnique.setString(1, p.getName());
            ResultSet resultSet = stmCheckUnique.executeQuery();
            if (resultSet.next()) {
                return false;
            }
            sql = "UPDATE promotion SET name =?, value = ?, start = ?, end = ? WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getName());
            stm.setDouble(2, p.getValue());
            stm.setDate(3, p.getTimeStart());
            stm.setDate(4, p.getTimeEnd());
            stm.setInt(5, p.getId());
            stm.executeUpdate();
            return true;
        }
    }

    public boolean addPromotion(Promotion p) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM promotion WHERE name = ?";
            PreparedStatement stmCheckUnique = conn.prepareCall(sql);
            stmCheckUnique.setString(1, p.getName());
            ResultSet resultSet = stmCheckUnique.executeQuery();
            if (resultSet.next()) {
                return false;
            }
            sql = "INSERT INTO promotion(name, value, start, end) VALUES(?, ?, ?, ?)"; // SQL injection
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getName());
            stm.setDouble(2, p.getValue());
            stm.setDate(3, p.getTimeStart());
            stm.setDate(4, p.getTimeEnd());
            stm.executeUpdate();
            try {
                conn.commit();
                return true;
            } catch (SQLException ex) {
                return false;
            }
        }
    }

    public static boolean isPromotionActive(Promotion promotion) {
//        LocalDate now = LocalDate.now();
//        return now.isAfter(promotion.getTimeStart()) && now.isBefore(promotion.getTimeEnd());
        return false;
    }

    public static double getDiscountedPrice(Product product) throws SQLException {
        double priceDiscount = 0;
        double price = product.getPrice();
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "Select value from promotion where id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, product.getPromotion_id());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                priceDiscount = rs.getDouble("value");
            }
        }
        price *= (1 - priceDiscount / 100);
//        if (product.getPromotion_id()!= null && isPromotionActive(product.getPromotion())) {
//            double discountPercentage = product.getPromotion().getValue();
//            price *= (1 - discountPercentage / 100);
//        }
        return price;
    }
}
