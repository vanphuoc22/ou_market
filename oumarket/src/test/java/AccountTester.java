import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import services.AccountSevrice;
import static org.junit.jupiter.api.Assertions.*;
import services.JdbcUtils;

public class AccountTester {

    private static AccountSevrice accountService = new AccountSevrice();;
    private static Connection conn ;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(AccountTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(AccountTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Test
    public void testGetUserRoles() {
        // Test case 1: Kiểm tra danh sách vai trò của một tài khoản có tồn tại
        List<String> roles = accountService.getUserRoles("admin");
        assertTrue(roles.contains("admin"));

        // Test case 2: Kiểm tra danh sách vai trò của một tài khoản không tồn tại
        roles = accountService.getUserRoles("user99");
        assertTrue(roles.isEmpty());
    }

    @Test
    public void testAuthenticateUser() {
        // Test case 1: Kiểm tra thông tin đăng nhập chính xác
        assertTrue(accountService.authenticateUser("an", "123"));

        // Test case 2: Kiểm tra thông tin đăng nhập không chính xác
        assertFalse(accountService.authenticateUser("user1", "225"));
    }

    @Test
    public void testRegisterUser() {
        // Test case 1: Đăng ký tài khoản thành công
        assertTrue(accountService.registerUser("newuser", "111", "Nguyễn Kiệm"));

        // Test case 2: Đăng ký tài khoản đã tồn tại
        assertFalse(accountService.registerUser("an", "123", "Nguyễn Kiệm"));
    }
}
