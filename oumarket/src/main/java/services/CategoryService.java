/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import pojo.Category;
import utils.MessageBox;
/**
 *
 * @author Khoa Tran
 */
public class CategoryService {
    public List<Category> getCategories(String kw) throws SQLException {
        List<Category> cates = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("SELECT * FROM categories");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                cates.add(new Category(id, name));
            }
        }
        return cates;
    }
    
    public boolean deleteCategory(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            
            String sql1 = "SELECT * FROM products WHERE category_id = ?";
            PreparedStatement stmGetProducts = conn.prepareCall(sql1);
            stmGetProducts.setInt(1, id);
            ResultSet rs = stmGetProducts.executeQuery();
            while(rs.next()){
                String sql2 = "DELETE FROM products WHERE category_id = ?";
                PreparedStatement stmDeleteProduct = conn.prepareCall(sql2);
                stmDeleteProduct.setInt(1, id);
                stmDeleteProduct.executeUpdate();
            }
            String sql3 = "DELETE FROM categories WHERE id=?";
            PreparedStatement stmDeleteCategory = conn.prepareCall(sql3);
            stmDeleteCategory.setInt(1, id);
            stmDeleteCategory.executeUpdate();
            return true;
        }
    }
    
    public boolean addCategory(Category c) throws SQLException {
        try ( Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            String sql = "SELECT * FROM categories WHERE name = ?";
            PreparedStatement stmCheckUnique = conn.prepareCall(sql);
            stmCheckUnique.setString(1, c.getName());
            ResultSet resultSet = stmCheckUnique.executeQuery();
            if(resultSet.next()) {
                return false;
            }
            sql = "INSERT INTO categories(name) VALUES (?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, c.getName());
            stm.executeUpdate();
            try {
                conn.commit();
                return true;
            }catch(SQLException ex) {
                return false;
            }
        }
    }
    
    public boolean editCategory(Category c) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            
            String sql = "SELECT * FROM categories WHERE name = ?";
            PreparedStatement stmCheckUnique = conn.prepareCall(sql);
            stmCheckUnique.setString(1, c.getName());
            ResultSet resultSet = stmCheckUnique.executeQuery();
            if (resultSet.next()) {
                return false;
            }
            sql = "UPDATE categories SET name =? WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, c.getName());
            stm.setInt(2, c.getId());
            stm.executeUpdate();
            return true;
        }
    }
}
