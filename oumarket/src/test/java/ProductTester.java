
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import services.ProductService;
import org.junit.jupiter.api.*;
import services.JdbcUtils;
import pojo.Product;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Khoa Tran
 */
public class ProductTester {

    private static Connection conn;
    private static ProductService pS;

    @BeforeAll
    public static void beforeAll() {
    }

    @AfterAll
    public static void afterAll() {
    }

    @Test
    public void testNotNull() {
        pS = new ProductService();
        try {
            List<Product> pros = pS.getProducts(null);

            long t = pros.stream().filter(c -> c.getName() == null).count();
            Assertions.assertTrue(t == 0);
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testUniqueName() {
        try {
            pS = new ProductService();
            List<Product> pros = pS.getProducts(null);

            List<String> names = pros.stream().flatMap(c -> Stream.of(c.getName())).collect(Collectors.toList());
            Set<String> testNames = new HashSet<>(names);
            Assertions.assertEquals(names.size(), testNames.size());
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void searchNameProductSuccessfull() throws SQLException {
        pS = new ProductService();
        List<Product> pros = pS.getProducts(null);
        Assertions.assertEquals(5, pros.size());
    }

    @Test
    public void searchNameProductFail() throws SQLException {
        pS = new ProductService();
        List<Product> pros = pS.getProducts("Sting");
        Assertions.assertEquals(1, pros.size());
    }

    @Test
    public void testAddProduct() throws SQLException {
        try {
            pS = new ProductService();
            Product p = new Product("Keo", 1, 80000, 100, "bịch");
            boolean actual = pS.addProduct(p);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM products where name = ?";
            PreparedStatement stm = JdbcUtils.getConn().prepareCall(sql);
            stm.setString(1, p.getName());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals(p.getName(), rs.getString("name")); //kiem tra da them vao hay chua
        }
        catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    @Test
//    public void testEditProduct() throws SQLException { //Test chinh sua thanh cong
//        String id = "2a904e57-dd90-44fc-a2f6-4c332b4285a3";
//
//        pS = new ProductService();
//        Product p = new Product(id, "Bò test 1", 41, 15000,100, "Kg", 5);
//        boolean actual = pS.editProduct(p);
//        Assertions.assertTrue(actual);
//        if (actual == true) {
//            Assertions.assertEquals(p.getName(), "Bò test 1");
//        }
//    }
//    @Test
//    public void testEditProduct() throws SQLException { //Test chinh sua ten cua san pham trung voi ten cua san pham khac
//        String id = "2a904e57-dd90-44fc-a2f6-4c332b4285a3";
//
//        pS = new ProductService();
//        Product p = new Product(id, "Gạo", 41, 15000,100, "Kg", 5);
//        boolean actual = pS.editProduct(p);
//        Assertions.assertTrue(actual);
//        if (actual == true) {
//            Assertions.assertEquals(p.getName(), "Gạo");
//        }
//    }
//    @Test
//    public void testEditPromotion() throws SQLException {
//        int idPromotion = 4;
//        String id = "31c7d7e0-b90c-48e3-bf29-8b7f4ca392cb";
//
//        pS = new ProductService();
//        Product p = new Product(id, "Gạo", 41, 15000,100, "Kg", idPromotion);
//        boolean actual = pS.updatePromotion(p);
//        Assertions.assertEquals(p.getPromotion_id(), idPromotion);
//    }
    @Test
    public void testDelete() {
        String id = "ded8ff95-70a1-4a1e-9596-d4669072a1a5";
        boolean actual;
        try {
            pS = new ProductService();
            conn = JdbcUtils.getConn();
            actual = pS.deleteProduct(id);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM products WHERE id=?";   //Lay ra san pham co ID do, neu khong co thi tra ve false. Mong muon false
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            Assertions.assertFalse(rs.next());

        } catch (SQLException ex) {
            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    @Test
//    public void testDelete() {
//        String id = "28765db2-8326-4bdb-a8be-61bc489e84c1"; //ID nay khong ton tai
//        boolean actual;
//        try {
//            pS = new ProductService();
//            conn = JdbcUtils.getConn();
//            actual = pS.deleteProduct(id);
//            Assertions.assertTrue(actual); //Mong muon xoa dc se tra ve TRUE
//            
//            String sql = "SELECT * FROM products WHERE id=?";   //Lay ra san pham co ID do, neu khong co thi tra ve false. Mong muon false
//            PreparedStatement stm = conn.prepareCall(sql);
//            stm.setString(1, id);
//            ResultSet rs = stm.executeQuery();
//            Assertions.assertFalse(rs.next());
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(ProductTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
