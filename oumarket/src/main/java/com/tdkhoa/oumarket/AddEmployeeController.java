/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pojo.Employee;
import services.EmployeeService;
import utils.MessageBox;

/**
 *
 * @author ASUS
 */
public class AddEmployeeController {
    static EmployeeService eS = new EmployeeService();
    
    @FXML private TextField nameEmployee;
    @FXML private TextField id;
    @FXML private TextField phoneNumber;
    @FXML private TextField startDay;
    @FXML private TextField Position;
    @FXML private TextField homeTown;
    @FXML private Button btnConfirm;
    @FXML private Button btnCancel;
    @FXML private VBox sceneVBox;
     Stage stageOut;
    public void addEmployee (ActionEvent evt) throws SQLException {
        Employee e = new Employee();
        e.setName(nameEmployee.getText());
        eS.insert(e);
        Alert a = MessageBox.getBox("Nhân viên", "Bạn có chắc muốn thêm nhân viên không? ", Alert.AlertType.CONFIRMATION);
            a.showAndWait().ifPresent(res -> {
                if (res == ButtonType.OK) {
                    stageOut = (Stage) sceneVBox.getScene().getWindow();
                    stageOut.close();
                }
            });
    }
    
    public void closeView(ActionEvent evt) {
        stageOut = (Stage) sceneVBox.getScene().getWindow();
        stageOut.close();
    }
}
