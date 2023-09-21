/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author VanAn
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AccountSevrice {

//    
    public List<String> getUserRoles(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> roles = new ArrayList<>();

        try {
            // Tạo kết nối đến database
            conn = JdbcUtils.getConn();

            // Tạo câu lệnh truy vấn
            String sql = "SELECT role FROM account WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            // Thực thi truy vấn và lấy kết quả trả về
            rs = stmt.executeQuery();

            // Lặp qua tất cả các bản ghi và thêm vai trò vào danh sách
            while (rs.next()) {
                roles.add(rs.getString("role"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Đóng tất cả các đối tượng ResultSet, PreparedStatement và Connection
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return roles;
    }

    public boolean authenticateUser(String username, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Lấy kết nối cơ sở dữ liệu
            connection = JdbcUtils.getConn();

            // Tạo câu lệnh truy vấn để kiểm tra thông tin đăng nhập
            String query = "SELECT * FROM account WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            // Thực thi truy vấn
            resultSet = statement.executeQuery();

            // Xác thực thông tin đăng nhập
            return resultSet.next();

        } catch (SQLException ex) {
            Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                if (resultSet
                        != null) {
                    resultSet.close();
                }

                if (statement
                        != null) {
                    statement.close();
                }

                if (connection
                        != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

   

    public boolean registerUser(String username, String password, String branch) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Lấy kết nối cơ sở dữ liệu
            connection = JdbcUtils.getConn();

            // Kiểm tra xem tài khoản đã tồn tại chưa
            String query = "SELECT COUNT(*) as count FROM account WHERE username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            rs.next();
            int count = rs.getInt("count");
            if (count > 0) {
                // Tài khoản đã tồn tại
                return false;
            }

            // Thêm tài khoản vào cơ sở dữ liệu
            String insertQuery = "INSERT INTO account(username, password, branch_address) VALUES(?, ?, ?)";
            statement = connection.prepareStatement(insertQuery);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, branch);
         
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted <= 0) {
                // Không thể thêm tài khoản
                return false;
            }

            // Đăng ký thành công
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccountSevrice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    public void redirectToMainScreen() {
        // Đoạn code hiện tại của hàm redirectToMainScreen
    }

// chuyển hướng đến màn hình chính
    

}


