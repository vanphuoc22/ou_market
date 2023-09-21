/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.OrderDetails;
import pojo.Product;
import services.OrderDetailsService;
import services.OrderService;
import pojo.Data;
import pojo.Order;


/**
 *
 * @author Khoa Tran
 */
public class ViewOrderDetailsController implements Initializable {
    @FXML TextField txtName;
    @FXML TextField txtPrice;
    @FXML TextField txtTienGiam;
    @FXML TextField txtTotal;
    @FXML TextField txtTienNhan;
    @FXML TextField txtTienTra;
    @FXML DatePicker orderDate;
    @FXML TextField txtDate;
    @FXML TableView tbProducts;
    private double sum = 0;
    OrderDetailsService oDS = new OrderDetailsService();
    OrderService oD = new OrderService();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTableOrderDetailsColumn();
        try {
            this.loadDataProducts();
            Order o = oD.findOrder(Data.getId());
//            System.out.println(o.getOrderDate());
            txtTotal.setText(o.getTotal().toString());
            txtDate.setText(o.getOrderDate());
            txtTienNhan.setText(o.getTienTraKhach().toString());
            txtTienTra.setText(o.getTienKhachDua().toString());
            txtTienGiam.setText(Double.toString(sum - Double.parseDouble(txtTotal.getText())));
        } catch (SQLException ex) {
            Logger.getLogger(ViewOrderDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadTableOrderDetailsColumn() {
        TableColumn<Product, String> colName = new TableColumn<>("Tên");
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colName.setPrefWidth(170);
        
        TableColumn<Product, Double> colPrice = new TableColumn<>("Giá");
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        
        TableColumn<Product, String> colQuantity = new TableColumn<>("Số lượng");
        colQuantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        
        this.tbProducts.getColumns().addAll(colName, colPrice, colQuantity);
    }
    
    public void loadDataProducts() throws SQLException {
        List<Product> pros = oDS.viewDetail(Data.getId());
        
        for(Product x: pros) {
            sum += x.getPrice() * x.getQuantity();
        }
        System.out.println("Tong tien hang: ");
        System.out.println(sum);
        
        this.tbProducts.getItems().clear();
        this.tbProducts.setItems(FXCollections.observableList(pros));
    }
}
