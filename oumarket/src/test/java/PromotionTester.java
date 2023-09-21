
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
import pojo.Category;
import pojo.Promotion;
import services.PromotionService;
import services.JdbcUtils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Khoa Tran
 */
public class PromotionTester {
    private static Connection conn;
    private static PromotionService proService;
    
    @BeforeAll
    public static void beforeAll() {
    }
    
    @AfterAll
    public static  void afterAll() {
    }
    
//    @Test
//    public void testNotNull() throws ParseException {
//        proService = new PromotionService();
//        try {
//            List<Promotion> pros =  proService.getPromotions(null);
//            
//            long t = pros.stream().filter(c -> c.getName() == null).count();
//            Assertions.assertTrue(t == 0);
//        } catch (SQLException ex) {
//            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    @Test
//    public void testUniqueName() throws ParseException {
//        proService = new PromotionService();
//        try {
//            List<Promotion> pros =  proService.getPromotions(null);
//            
//            List<String> names = pros.stream().flatMap(c -> Stream.of(c.getName())).collect(Collectors.toList());
//            Set<String> testNames = new HashSet<>(names);
//            Assertions.assertEquals(names.size(), testNames.size());
//        } catch (SQLException ex) {
//            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    @Test
//    public void testAddPromotion() throws SQLException, ParseException {
//        Date start = new Date(2023, 04, 15);
//        Date end = new Date(2023, 04, 20);
//        try {
//            proService = new PromotionService();
//            Promotion pro = new Promotion("Khuyen mai moi", 10, start, end);
//            boolean actual = proService.addPromotion(pro);
//            Assertions.assertTrue(actual);
//
//            String sql = "SELECT * FROM promotion where name = ?";
//            PreparedStatement stm = JdbcUtils.getConn().prepareCall(sql);
//            stm.setString(1, pro.getName());
//
//            ResultSet rs = stm.executeQuery();
//            Assertions.assertNotNull(rs.next());
//            Assertions.assertEquals(pro.getName(), rs.getString("name")); //kiem tra da them vao hay chua
//        }
//        catch (SQLException ex) {
//            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//        @Test
//    public void testAddPromotion() throws SQLException, ParseException {
//        Date end = new Date(2023, 04, 15);
//        Date start = new Date(2023, 04, 20);
//        try {
//            proService = new PromotionService();
//            Promotion pro = new Promotion("Khuyen mai moi", 10, start, end);
//            boolean actual = proService.addPromotion(pro);
//            Assertions.assertTrue(actual);
//
//            String sql = "SELECT * FROM promotion where name = ?";
//            PreparedStatement stm = JdbcUtils.getConn().prepareCall(sql);
//            stm.setString(1, pro.getName());
//
//            ResultSet rs = stm.executeQuery();
//            Assertions.assertNotNull(rs.next());
//            Assertions.assertEquals(pro.getName(), rs.getString("name")); //kiem tra da them vao hay chua
//        }
//        catch (SQLException ex) {
//            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    @Test
//    public void testEditPromotion() throws SQLException {
//        String nameUpdate = "khoa ma khuyen mai";
//        int id = 4;
//        Date start = new Date(2023, 04, 15);
//        Date end = new Date(2023, 04, 20);
//        
//        proService = new PromotionService();
//        Promotion pro = new Promotion(id,nameUpdate, 10, start, end);
//        boolean actual = proService.editPromotion(pro);
//        Assertions.assertTrue(actual);
//        if (actual == true) {
//            Assertions.assertEquals(pro.getName(), nameUpdate);
//        }
//    }
    
//    @Test
//    public void testEditCategory() throws SQLException {
//        String nameUpdate = "khoa ma khuyen mai";
//        int id = 4;
//        Date start = new Date(2023, 04, 15);
//        Date end = new Date(2023, 04, 20);
//        
//        proService = new PromotionService();
//        Promotion pro = new Promotion(id,nameUpdate, 10, start, end);
//        boolean actual = proService.editPromotion(pro);
//        Assertions.assertTrue(actual);
//        if (actual == true) {
//            Assertions.assertEquals(pro.getName(), nameUpdate);
//        }
//    }
    
//    @Test
//    public void testDeleteCategory() {
//        int id = 4;
//        proService = new PromotionService();
//        try {
//             boolean actual = proService.deletePromotion(id);
//             Assertions.assertTrue(actual);
//        }
//        catch (SQLException ex) {
//            Logger.getLogger(PromotionTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
