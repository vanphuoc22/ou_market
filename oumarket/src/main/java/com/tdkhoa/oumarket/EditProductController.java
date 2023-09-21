/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tdkhoa.oumarket;

import static com.tdkhoa.oumarket.AddProductController.pS;
import static com.tdkhoa.oumarket.PrimaryController.pRow;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pojo.Category;
import pojo.Promotion;
import services.CategoryService;
import services.ProductService;
import services.PromotionService;
import utils.MessageBox;

/**
 *
 * @author Khoa Tran
 */
public class EditProductController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtQuantity;
    @FXML
    private ComboBox<Promotion> cbPromotions;
    @FXML
    private VBox sceneVBox;
    static public String[] itemsUnit = {"Cái", "Gói", "Kg", "Chai", "Lon"};
    @FXML
    ComboBox<String> cbUnit;
    Stage stageOut;
    @FXML
    private ComboBox<Category> cbCategories;
    static ProductService pS = new ProductService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CategoryService s = new CategoryService();
        txtName.setText(pRow.getName());
        txtPrice.setText(Double.toString(pRow.getPrice()));
        txtQuantity.setText(Integer.toString(pRow.getQuantity()));
        cbUnit.setValue(pRow.getUnit());
        cbCategories.setPromptText(pRow.getCategoryName());
        this.cbUnit.setItems(FXCollections.observableArrayList(itemsUnit));
        try {
            List<Category> cates = s.getCategories(null);
            this.cbCategories.setItems(FXCollections.observableList(cates));
        } catch (SQLException ex) {
            Logger.getLogger(EditProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleUpdateProduct(ActionEvent event) throws SQLException {
        try {
            double p = Double.parseDouble(this.txtPrice.getText());
            double b = Double.parseDouble(this.txtQuantity.getText());
            if (p < 0 || b < 0) {
                MessageBox.getBox("Sản phẩm", "Dữ liệu không hợp lệ", Alert.AlertType.WARNING).show();
            } else {
                if (!this.txtPrice.getText().isEmpty() && !this.txtName.getText().isEmpty() && this.cbCategories.getValue() != null  && this.cbUnit.getValue() != null) {
                    pRow.setName(this.txtName.getText());
                    pRow.setCategoryId(this.cbCategories.getSelectionModel().getSelectedItem().getId());
                    pRow.setQuantity(Integer.parseInt(this.txtQuantity.getText()));
                    pRow.setPrice(Double.parseDouble(this.txtPrice.getText()));
                    pRow.setUnit(this.cbUnit.getValue());
                    try {
                        if (pS.editProduct(pRow)) {
                            Alert a = MessageBox.getBox("Sản phẩm", "Sửa sản phẩm thành công ", Alert.AlertType.INFORMATION);
                            a.showAndWait().ifPresent(res -> {
                                if (res == ButtonType.OK) {
                                    stageOut = (Stage) sceneVBox.getScene().getWindow();
                                    stageOut.close();
                                }
                            });
                        } else {
                            MessageBox.getBox("Sản phẩm", "Sản phẩm đã tồn tại", Alert.AlertType.WARNING).show();
                        }
                    } catch (SQLException ex) {
                        System.out.println("Catch");
                        MessageBox.getBox("Sản phẩm", "Sửa sản phẩm thất bại", Alert.AlertType.ERROR).show();
                        Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    MessageBox.getBox("Sản phẩm", "Vui lòng điền đủ cái trường thông tin", Alert.AlertType.WARNING).show();
                }
            }
        } catch (NumberFormatException ex) {
            MessageBox.getBox("Sản phẩm", "Dữ liệu không hợp lệ", Alert.AlertType.WARNING).show();
        }

    }
}
