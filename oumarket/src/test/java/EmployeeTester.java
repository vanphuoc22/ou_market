import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pojo.Employee;
import services.EmployeeService;
import services.JdbcUtils;

class EmployeeTester {

    private EmployeeService employeeService = new EmployeeService();
    private static Connection conn ;
    @BeforeEach
    public void beforeEach() throws Exception {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @AfterEach
    public static void afterEach() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeTester.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Test
    @DisplayName("Test getEmployees method")
    public void testGetEmployees() throws SQLException {
        List<Employee> employees = employeeService.getEmployees("");
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    @DisplayName("Test deleteEmployee method")
    public void testDeleteEmployee() throws SQLException {
        // Delete an existing employee
        assertTrue(employeeService.deleteEmployee(1));

        // Delete a non-existing employee
        assertFalse(employeeService.deleteEmployee(100));
    }

    @Test
    @DisplayName("Test insert method")
    public void testInsert() throws SQLException {
        // Insert a new employee
        Employee emp = new Employee(0, "New Employee");
        employeeService.insert(emp);

        // Check if the employee was inserted successfully
        List<Employee> employees = employeeService.getEmployees("");
        assertTrue(employees.stream().anyMatch(e -> e.getName().equals("New Employee")));
    }

    @Test
    @DisplayName("Test update method")
    public void testUpdate() throws SQLException {
        // Update an existing employee
        Employee emp = new Employee(1, "Updated Employee");
        assertTrue(employeeService.update(emp));

        // Update a non-existing employee
        emp.setId(100);
        assertFalse(employeeService.update(emp));
    }

}
