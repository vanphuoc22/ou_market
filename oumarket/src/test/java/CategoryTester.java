/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pojo.Category;
import services.CategoryService;
import services.JdbcUtils;
/**
 *
 * @author Khoa Tran
 */
public class CategoryTester {
    private static Connection conn;
    private static CategoryService cateService;
    
    @BeforeAll
    public static void beforeAll() {
    }
    
    @AfterAll
    public static  void afterAll() {
    }
    
//    @Test
//    public void testNotNull() {
//        CategoryService s = new CategoryService();
//        try {
//            List<Category> cates =  s.getCategories(null);
//            
//            long t = cates.stream().filter(c -> c.getName() == null).count();
//            Assertions.assertTrue(t == 0);
//        } catch (SQLException ex) {
//            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    @Test
//    public void testUniqueName() {
//        try {
//            List<Category> cates =  cateService.getCategories(null);
//            
//            List<String> names = cates.stream().flatMap(c -> Stream.of(c.getName())).collect(Collectors.toList());
//            Set<String> testNames = new HashSet<>(names);
//            Assertions.assertEquals(names.size(), testNames.size());
//        } catch (SQLException ex) {
//            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    @Test
    public void testAddCategory() throws SQLException {
        try {
            cateService = new CategoryService();
            Category cate = new Category("Ve sinh nha cua");
            boolean actual = cateService.addCategory(cate);
            Assertions.assertTrue(actual);

            String sql = "SELECT * FROM categories where name = ?";
            PreparedStatement stm = JdbcUtils.getConn().prepareCall(sql);
            stm.setString(1, cate.getName());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals(cate.getName(), rs.getString("name")); //kiem tra da them vao hay chua
        }
        catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    @Test
//    public void testAddCategory() throws SQLException {
//        try {
//            cateService = new CategoryService();
//            Category cate = new Category("Ve sinh nha cua 4");
//            boolean actual = cateService.addCategory(cate);
//            Assertions.assertTrue(actual);
//
//            String sql = "SELECT * FROM categories where name = ?";
//            PreparedStatement stm = JdbcUtils.getConn().prepareCall(sql);
//            stm.setString(1, cate.getName());
//
//            ResultSet rs = stm.executeQuery();
//            Assertions.assertNotNull(rs.next());
//            Assertions.assertEquals(cate.getName(), rs.getString("name")); //kiem tra da them vao hay chua
//        }
//        catch (SQLException ex) {
//            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
//    @Test
//    public void testEditCategory() {
//        String nameUpdate = "khoa danh muc 1";
//        int id = 28;
//        cateService = new CategoryService();
//        try {
//            Category c = new Category(id,nameUpdate);
//
//            boolean actual = cateService.editCategory(c);
//            Assertions.assertTrue(actual);
//            if (actual == true) {
//                Assertions.assertEquals(c.getName(), nameUpdate);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    @Test
    public void testEditCategory() {
        String nameUpdate = "Trang tri noi that";
        int id = 48;
        cateService = new CategoryService();
        try {
            Category c = new Category(id,nameUpdate);

            boolean actual = cateService.editCategory(c);
            Assertions.assertTrue(actual);
            if (actual == true) {
                Assertions.assertEquals(c.getName(), nameUpdate);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Test
    public void testDeleteCategory() {
        int id = 41;
        cateService = new CategoryService();
        try {
             boolean actual = cateService.deleteCategory(id);
             Assertions.assertTrue(actual);
        }
        catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
