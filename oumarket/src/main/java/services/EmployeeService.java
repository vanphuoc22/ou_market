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
import java.util.logging.Level;
import java.util.logging.Logger;


import pojo.Employee;

/**
 * 
 * @author ASUS
 */
public class EmployeeService {

    public List<Employee> getEmployees(String kw) throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();

            ResultSet rs = stm.executeQuery("SELECT * FROM employee");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                employeeList.add(new Employee(id, name));
            }
        }
        return employeeList;
    }
    
    public boolean deleteEmployee(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "DELETE FROM employee WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);
            
            return stm.executeUpdate() > 0;
        }
    }
   
    public void insert(Employee std){
        
        Connection con = null;
        PreparedStatement statement = null;
        try {
            con = JdbcUtils.getConn();
            
            String sql = "INSERT INTO employee(name) VALUES(?)";
            statement = con.prepareCall(sql);
            
            statement.setString(1, std.getName());
            
            statement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public boolean update(Employee p) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            String sql = "UPDATE employee SET name =? WHERE id=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getName());
            stm.setInt(2, p.getId());
            return stm.executeUpdate() > 0;
        }
    }
}
