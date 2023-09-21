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
import pojo.Order;
import services.JdbcUtils;
import services.OrderService;

/**
 *
 * @author Khoa Tran
 */
public class OrderTester {

    private static Connection conn;
    private static OrderService ordService;

    @BeforeAll
    public static void beforeAll() {
    }

    @AfterAll
    public static void afterAll() {
    }

    @Test
    public void testNotNull() throws ParseException {
        ordService = new OrderService();
        try {
            List<Order> orders = ordService.getOrders();

            long t = orders.stream().filter(c -> c.getId() == null).count();
            Assertions.assertTrue(t == 0);
        } catch (SQLException ex) {
            Logger.getLogger(OrderTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testUniqueName() throws ParseException {
        ordService = new OrderService();
        try {
            List<Order> orders = ordService.getOrders();

            List<String> ids = orders.stream().flatMap(c -> Stream.of(c.getId())).collect(Collectors.toList());
            Set<String> testIds = new HashSet<>(ids);
            Assertions.assertEquals(ids.size(), testIds.size());
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Test
//    public void testAddOrderSuccessfull() throws SQLException, ParseException {
//        try {
//            double total = 108000;
//            double tienKhachDua = 110000;
//            double tienTraKhach = tienKhachDua - total;
//            ordService = new OrderService();
//            Order order = new Order("19/04/2023", 108000, 110000, tienTraKhach);
//            boolean actual = ordService.addOrder(order);
//            Assertions.assertTrue(actual);
//
//            String sql = "SELECT * FROM order where id = ?";
//            PreparedStatement stm = JdbcUtils.getConn().prepareCall(sql);
//            stm.setString(1, order.getId());
//
//            ResultSet rs = stm.executeQuery();
//            Assertions.assertNotNull(rs.next());
//            Assertions.assertEquals(order.getId(), rs.getString("id")); //kiem tra da them vao hay chua
//        }
//        catch (SQLException ex) {
//            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @Test
    public void findOrderSuccessfull() throws SQLException {
        try {
            String id = "d8b958d8-cdae-456b-88d3-fd02261ae9fe";
            ordService = new OrderService();
            Order o = ordService.findOrder(id);

            Assertions.assertEquals("19/04/2023", o.getOrderDate());
        } catch (SQLException ex) {
            Logger.getLogger(OrderTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
