/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.Customer;
import services.CustomerService;
import services.JdbcUtils;
/**
 *
 * @author Khoa Tran
 */
public class CustomerTester {
    private static Connection conn;
    private static CustomerService cusS;
    
    @BeforeAll
    public static void beforeAll() {
    }
    
    @AfterAll
    public static  void afterAll() {
    }
    
//    @Test
//    public void testNotNull() throws ParseException {
//        cusS = new CustomerService();
//        try {
//            List<Customer> cus =  cusS.getCustomers();
//            
//            long t = cus.stream().filter(c -> c.getName() == null).count();
//            Assertions.assertTrue(t == 0);
//        } catch (SQLException ex) {
//            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    @Test
//    public void testUniqueName() throws ParseException {
//        cusS = new CustomerService();
//        try {
//            List<Customer> cus =  cusS.getCustomers();
//            
//            List<String> names = cus.stream().flatMap(c -> Stream.of(c.getName())).collect(Collectors.toList());
//            Set<String> testNames = new HashSet<>(names);
//            Assertions.assertEquals(names.size(), testNames.size());
//        } catch (SQLException ex) {
//            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    @Test
    public void testSearchCustomerSuccessfull() {
        try {
            cusS = new CustomerService();
            List <Customer> customersList = cusS.getCustomers();
            Customer c = cusS.findCustomerByPhoneNumber(customersList, "0397825025");
            
            Assertions.assertEquals("0397825025", c.getPhone());
        }
        catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testSearchCustomerFail() {
        try {
            cusS = new CustomerService();
            List <Customer> customersList = cusS.getCustomers();
            Customer c = cusS.findCustomerByPhoneNumber(customersList, "0397825025");
            
            Assertions.assertEquals("1234567890", c.getPhone());
        }
        catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testAddCustomerSuccessfull() throws SQLException, ParseException {
        try {
            cusS = new CustomerService();
            Customer cus = new Customer("Nguyen Van Phuoc", "01/01/2002", "0905953329", 0);
            boolean actual = cusS.addCustomer(cus);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM customer where phone = ?";
            PreparedStatement stm = JdbcUtils.getConn().prepareCall(sql);
            stm.setString(1, cus.getPhone());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals(cus.getPhone(), rs.getString("phone")); //kiem tra da them vao hay chua
        }
        catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testAddCustomerFail() throws SQLException, ParseException {   //Lỗi tồn tại khách hàng
        try {
            cusS = new CustomerService();
            Customer cus = new Customer("Nguyen Van Phuoc", "01/01/2002", "0905953328", 0);
            boolean actual = cusS.addCustomer(cus);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM customer where phone = ?";
            PreparedStatement stm = JdbcUtils.getConn().prepareCall(sql);
            stm.setString(1, cus.getPhone());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals(cus.getPhone(), rs.getString("phone")); //kiem tra da them vao hay chua
        }
        catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testPlusPoint() {
        try {
            cusS = new CustomerService();
            List <Customer> customersList = cusS.getCustomers();
            Customer c = cusS.findCustomerByPhoneNumber(customersList, "0397825025");
            
            boolean actual = cusS.updatePoint(c, 1000000);
            Assertions.assertTrue(actual);
            Assertions.assertEquals("0397825025", c.getPhone());
        }
        catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
