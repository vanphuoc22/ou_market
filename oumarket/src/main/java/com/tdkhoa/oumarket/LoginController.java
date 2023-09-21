/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.AccountSevrice;

/**
 *
 * @author ASUS
 */
public class LoginController {

    static AccountSevrice aS = new AccountSevrice();

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button btnSigup;
    @FXML
    private Button btnLogin;
    @FXML
    private Text actiontarget;
    @FXML
    private AnchorPane AnchorPane;
    public String savedUsername;

    public void handleLoginButtonAction(ActionEvent eve) {
        this.btnLogin.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() && password.isEmpty()) {
                actiontarget.setFill(Color.RED);
                actiontarget.setText("VUI LÒNG NHẬP TÊN ĐĂNG NHẬP VÀ MẬT KHẨU");
            } else {
                if (username.isEmpty()) {
                    actiontarget.setFill(Color.RED);
                    actiontarget.setText("VUI LÒNG NHẬP TÊN ĐĂNG NHẬP");
                } else {
                    if (password.isEmpty()) {
                        actiontarget.setFill(Color.RED);
                        actiontarget.setText("VUI LÒNG NHẬP MẬT KHẨU");
                    } else {
                        if (aS.authenticateUser(username, password)) {
                            savedUsername = username;
                            // ẩn form login
                            btnLogin.getScene().getWindow().hide();
                            // chuyển hướng đến ứng dụng
                            this.viewApp(eve);
                        } else {

                            // hiển thị thông báo lỗi
                            actiontarget.setFill(Color.RED);
                            actiontarget.setText("KHÔNG ĐÚNG TÊN ĐĂNG NHẬP HOẶC MẬT KHẨU.");
                        }
                    }
                }

            }

        });
    }


    public void handleSigupButtonAction(ActionEvent eve) {
        this.btnSigup.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() && password.isEmpty()) {
                actiontarget.setFill(Color.RED);
                actiontarget.setText("VUI LÒNG NHẬP TÊN ĐĂNG NHẬP VÀ MẬT KHẨU");
            } else {
                if (username.isEmpty()) {
                    actiontarget.setFill(Color.RED);
                    actiontarget.setText("VUI LÒNG NHẬP TÊN ĐĂNG NHẬP");
                } else {
                    if (password.isEmpty()) {
                        actiontarget.setFill(Color.RED);
                        actiontarget.setText("VUI LÒNG NHẬP MẬT KHẨU");
                    } else {
                        if (aS.authenticateUser(username, password)) {

                            List<String> userRoles = aS.getUserRoles(username);

                            // kiểm tra vài trò của người dùng
                            if (userRoles.contains("admin")) {
                                // chuyển hướng đén 
                                viewSigup(eve);
                            } else {
                                // hiển thị thông báo lỗi
                                actiontarget.setFill(Color.RED);
                                actiontarget.setText("NGƯỜI DÙNG KHÔNG PHẢI ADMIN.");
                            }
                        } else {
                            // hiển thị thông báo lỗi
                            actiontarget.setFill(Color.RED);
                            actiontarget.setText("KHÔNG ĐÚNG TÀI KHOẢN HOẶC MẬT KHẨU.");
                        }
                    }
                }

            }
            
        });
    }

    public void viewApp(ActionEvent evt) {

        try {
            Stage stage = new Stage();

            Parent root = FXMLLoader.load(getClass().getResource("/com/tdkhoa/oumarket/primary.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(" ");
//                
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void viewSigup(ActionEvent evt) {

        try {
            Stage stage = new Stage();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Sigup.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(" ");
//                stage.setOnHidden(e -> {  
//                    try {
//                        loadProductsData(null);
//                    } catch (SQLException ex) {
//                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                  });
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
