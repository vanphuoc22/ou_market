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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.Order;
import pojo.OrderDetails;
import pojo.Product;
import services.JdbcUtils;
import services.OrderDetailsService;
import services.OrderService;

/**
 *
 * @author Khoa Tran
 */
public class OrderDetailsTester {

    private static Connection conn;
    private static OrderDetailsService ordDetailsService;
    private OrderService ordService;

    @BeforeAll
    public static void beforeAll() {
    }

    @AfterAll
    public static void afterAll() {
    }

    @Test
    public void saveOrderDetailsTest() {
        try {
            conn = JdbcUtils.getConn();
            ordDetailsService = new OrderDetailsService();
            OrderService ordService = new OrderService();
            ObservableList<OrderDetails> orderDetailsList = FXCollections.observableArrayList();
            boolean actual = false;

            String idOrder = "d8b958d8-cdae-456b-88d3-fd02261ae9fe";
            Order o = ordService.findOrder(idOrder);
            Assertions.assertEquals(o.getId(), idOrder);
            Product p1 = new Product("Keo", 1, 80000, 100, "Bịch");

            OrderDetails oD1 = new OrderDetails(p1, 5);

            orderDetailsList.add(oD1);

            for (OrderDetails x : orderDetailsList) {
                actual = ordDetailsService.saveOderDetails(x, o);
                Assertions.assertTrue(actual);
                String sql = "SELECT * FROM orderdetails WHERE id = ?";
                PreparedStatement stm = JdbcUtils.getConn().prepareCall(sql);
                stm.setString(1, idOrder);

                ResultSet rs = stm.executeQuery();
                if(rs.next()) {
                    Assertions.assertEquals(rs.getString("order_id"), o.getId()); //kiem tra da them vao hay chua
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailsTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void updateQuantityInStockTest() {
        ordDetailsService = new OrderDetailsService();
        ordService = new OrderService();
        ObservableList<OrderDetails> orderDetailsList = FXCollections.observableArrayList();
        try {

            String idOrder = "d8b958d8-cdae-456b-88d3-fd02261ae9fe";
            Order o = ordService.findOrder(idOrder);
            Assertions.assertEquals(o.getId(), idOrder);
            Product p1 = new Product("Keo", 1, 80000, 100, "Cái");

            OrderDetails oD1 = new OrderDetails(p1, 5);

            orderDetailsList.add(oD1);

            for (OrderDetails x : orderDetailsList) {
                ordDetailsService.saveOderDetails(x, o);
            }
            Assertions.assertTrue(ordDetailsService.updateQuantityInStock(orderDetailsList));
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailsTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void viewDetailTest() {
        try {
            ordDetailsService = new OrderDetailsService();
            String idOrder = "995d9a14-070c-40b2-9481-ec20b4927f45";
            List<Product> listProducts = ordDetailsService.viewDetail(idOrder);
            Assertions.assertEquals(10, listProducts.size());
        } catch (SQLException ex) {
            Logger.getLogger(OrderDetailsTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
