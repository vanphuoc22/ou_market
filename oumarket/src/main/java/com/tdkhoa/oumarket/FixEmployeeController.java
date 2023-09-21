/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.tdkhoa.oumarket;


import static com.tdkhoa.oumarket.PrimaryController.eRow;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pojo.Employee;
import services.EmployeeService;


/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FixEmployeeController implements Initializable {
    @FXML private TextField txtName;
    @FXML private TextField txtId;
    @FXML private AnchorPane AnchorPane;
    @FXML private Text txt;
    @FXML TableView<Employee> tbEmployees;
    Stage stageOut;
    static EmployeeService eS = new EmployeeService();
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
    }
    public void handleUpdateEmployee(ActionEvent event) throws SQLException {
        eRow.setName(this.txtName.getText());
        if(!txtName.getText().isEmpty()){
            eS.update(eRow);
            if(eS.update(eRow)){
                
                this.close(event);
            } else
                txt.setText("Lỗi");
        }else
            txt.setText("Vui lòng nhập thông tin");
        
    }

   public void close(ActionEvent evt) {
        stageOut = (Stage) AnchorPane.getScene().getWindow();
        stageOut.close();
    }
   
}

